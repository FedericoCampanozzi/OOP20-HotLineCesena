package hotlinecesena.controller.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

import hotlinecesena.controller.Updatable;
import hotlinecesena.model.dataccesslayer.DataAccessLayer;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.items.Projectile;
import hotlinecesena.model.entities.items.Projectile.ProjectileStatus;
import hotlinecesena.view.WorldView;
import hotlinecesena.view.entities.Sprite;
import hotlinecesena.view.entities.SpriteImpl;
import hotlinecesena.view.loader.ImageLoader;
import hotlinecesena.view.loader.ImageType;
import hotlinecesena.view.loader.ProxyImage;
import hotlinecesena.view.loader.SceneType;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Translate;

public final class ProjectileController implements Updatable {

    private static final Point2D DELTA_VECTOR = new Point2D(1, 1);
    private static final double SPRITE_SCALE = 0.2;
    private static final int DEFAULT_POOL_SIZE = 30;
    private static final Point2D OUT_OF_BOUNDS = new Point2D(500_000, 500_000);
    private final List<Sprite> spritePool = new ArrayList<>(DEFAULT_POOL_SIZE);
    private final Map<Projectile, Sprite> projectileMap = new HashMap<>();
    private final ImageLoader loader = new ProxyImage();
    private final WorldView worldView;

    public ProjectileController(final WorldView worldView) {
        this.worldView = worldView;
        this.initSpritePool();
    }

    @Override
    public Consumer<Double> getUpdateMethod() {
        return deltaTime -> {
            final List<Projectile> projectilesInModel = this.getDAL().getBullets().getProjectile();
            final Set<Projectile> removeSet = new HashSet<>();

            if (projectilesInModel.size() > spritePool.size()) {
                this.expandPool(projectilesInModel.size() - spritePool.size());
            }

            if (projectileMap.size() != projectilesInModel.size()) {
                final Projectile newProjectile = projectilesInModel.get(projectilesInModel.size() - 1);
                final Sprite newSprite = this.findUnusedSprite();
                newSprite.updateRotation(newProjectile.getAngle());
                projectileMap.put(newProjectile, newSprite);
            }

            final Iterator<Entry<Projectile, Sprite>> projIterator = projectileMap.entrySet().iterator();
            projIterator.forEachRemaining(currentEntry -> {
                final Projectile proj = currentEntry.getKey();
                final Sprite sprite = currentEntry.getValue();

                if (proj.getProjectileStatus() == ProjectileStatus.MOVING) {
                    proj.move(DELTA_VECTOR.multiply(deltaTime));
                    sprite.updatePosition(proj.getPosition());
                } else {
                    sprite.updatePosition(OUT_OF_BOUNDS);
                    removeSet.add(proj);
                }
            });
            removeSet.forEach(p -> {
                projectileMap.remove(p);
                projectilesInModel.remove(p);
            });
        };
    }

    private Sprite createSprite() {
        final ImageView top = new ImageView(loader.getImage(SceneType.GAME, ImageType.BULLET));
        top.getTransforms().add(new Translate());
        top.setFitWidth(SPRITE_SCALE);
        top.setFitHeight(SPRITE_SCALE);
        worldView.getGridPane().add(top, 0, 0);

        final Sprite projSprite = new SpriteImpl(top);
        projSprite.updatePosition(OUT_OF_BOUNDS);
        projSprite.updateRotation(0);
        return projSprite;
    }

    private void initSpritePool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            spritePool.add(this.createSprite());
        }
    }

    private void expandPool(final int extra) {
        for (int i = spritePool.size() - 1; i < spritePool.size() + extra - 1; i++) {
            spritePool.add(this.createSprite());
        }
    }

    private Sprite findUnusedSprite() {
        final Point2D offsetTranslate = OUT_OF_BOUNDS.multiply(0.5);
        return spritePool.stream()
                .filter(s -> s.getTranslatePosition().equals(offsetTranslate))
                .findFirst()
                .get();
    }

    private DataAccessLayer getDAL() {
        return JSONDataAccessLayer.getInstance();
    }
}