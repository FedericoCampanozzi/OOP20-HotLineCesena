package hotlinecesena.controller.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import hotlinecesena.model.dataccesslayer.DataAccessLayer;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.items.Projectile;
import hotlinecesena.model.entities.items.ProjectileImpl.ProjectileStatus;
import hotlinecesena.view.WorldView;
import hotlinecesena.view.entities.Sprite;
import hotlinecesena.view.entities.SpriteImpl;
import hotlinecesena.view.loader.ImageLoader;
import hotlinecesena.view.loader.ImageType;
import hotlinecesena.view.loader.ProxyImage;
import hotlinecesena.view.loader.SceneType;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * Update all the projectiles (status and position) in game.
 */
public final class ProjectileControllerImpl implements ProjectileController {

    private static final Point2D DELTA_VECTOR = new Point2D(1, 1);
    private static final double SPRITE_SCALE = 0.2;
    private static final int DEFAULT_POOL_SIZE = 30;
    private static final Point2D OUT_OF_BOUNDS = new Point2D(500_000, 500_000);
    
    private final Set<Sprite> spritePool = new HashSet<>(DEFAULT_POOL_SIZE);
    private final Map<Projectile, Sprite> projectileMap = new HashMap<>();
    private final ImageLoader loader = new ProxyImage();
    private final WorldView worldView;

    /**
     * Class constructor.
     * @param worldView
     */
    public ProjectileControllerImpl(final WorldView worldView) {
        this.worldView = worldView;
        this.expandPool(DEFAULT_POOL_SIZE);
    }

    /**
     * Takes projectiles from the pool.
     */
    @Override
    public Consumer<Double> getUpdateMethod() {
        return deltaTime -> {
            final List<Projectile> projectilesInModel = this.getDAL().getBullets().getProjectile();
            final Set<Projectile> removeSet = new HashSet<>();

            for (final Projectile current : projectilesInModel) {
                if (!projectileMap.containsKey(current)) {
                    Optional<Sprite> unusedSprite = this.findUnusedSprite();
                    while (unusedSprite.isEmpty()) {
                        this.expandPool(DEFAULT_POOL_SIZE);
                        unusedSprite = this.findUnusedSprite();
                    }
                    unusedSprite.get().updatePosition(current.getPosition());
                    unusedSprite.get().updateRotation(current.getAngle());
                    projectileMap.put(current, unusedSprite.get());
                }
            }
            projectileMap.forEach((proj, sprite) -> {
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

    /**
     * Create the projectile sprite.
     * @return
     */
    private Sprite createSprite() {
        final ImageView top = new ImageView(loader.getImage(SceneType.GAME, ImageType.BULLET));
        top.getTransforms().add(new Translate());
        top.getTransforms().add(new Rotate());
        top.setFitWidth(SPRITE_SCALE);
        top.setFitHeight(SPRITE_SCALE);
        worldView.getGridPane().add(top, 0, 0);

        final Sprite projSprite = new SpriteImpl(top);
        projSprite.updatePosition(OUT_OF_BOUNDS);
        projSprite.updateRotation(0);
        return projSprite;
    }

    /**
     * Increase pool size.
     * @param extra
     */
    private void expandPool(final int extra) {
        for (int i = 0; i < extra; i++) {
            spritePool.add(this.createSprite());
        }
    }

    /**
     * @return an unused sprite of a projectile.
     */
    private Optional<Sprite> findUnusedSprite() {
        final Point2D offsetTranslate = OUT_OF_BOUNDS.multiply(0.5);
        return spritePool.stream()
                .filter(s -> s.getTranslatePosition().equals(offsetTranslate))
                .findFirst();
    }

    /**
     * @return the DAL current instance.
     */
    private DataAccessLayer getDAL() {
        return JSONDataAccessLayer.getInstance();
    }
}