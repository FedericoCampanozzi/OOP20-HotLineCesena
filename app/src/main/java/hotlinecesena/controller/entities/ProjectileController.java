package hotlinecesena.controller.entities;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Translate;

public final class ProjectileController implements Updatable {

    private static final Image EMPTY_IMAGE = null;
    private static final Point2D DELTA_VECTOR = new Point2D(1, 1);
    private final Map<Projectile, Sprite> projectileMap = new ConcurrentHashMap<>();
    private final ImageLoader loader = new ProxyImage();
    private final WorldView worldView;

    public ProjectileController(final WorldView worldView) {
        this.worldView = worldView;
    }

    @Override
    public Consumer<Double> getUpdateMethod() {
        return deltaTime -> {
            final List<Projectile> projectilesInModel = this.getDAL().getBullets().getProjectile();
            if (projectileMap.size() != projectilesInModel.size()) {
                final Projectile newProjectile = projectilesInModel.get(projectilesInModel.size() - 1);
                projectileMap.put(newProjectile, this.createSprite(newProjectile));
            }
            final Iterator<Entry<Projectile, Sprite>> projIterator = projectileMap.entrySet().iterator();
            projIterator.forEachRemaining(currentEntry -> {
                final Projectile proj = currentEntry.getKey();
                final Sprite sprite = currentEntry.getValue();

                if (proj.getProjectileStatus() == ProjectileStatus.MOVING) {
                    proj.move(DELTA_VECTOR.multiply(deltaTime));
                    sprite.updatePosition(proj.getPosition());
                } else {
                    sprite.updateImage(EMPTY_IMAGE);
                    projectileMap.remove(proj);
                    projectilesInModel.remove(proj);
                }
            });
        };
    }

    private Sprite createSprite(final Projectile proj) {
        final double spriteScale = 0.2;
        final ImageView top = new ImageView(loader.getImage(SceneType.GAME, ImageType.BULLET));
        top.getTransforms().add(new Translate());
        top.setFitHeight(this.getDAL().getSettings().getTileSize() * spriteScale);
        top.setFitWidth(this.getDAL().getSettings().getTileSize() * spriteScale);
        worldView.getGridPane().add(top, 0, 0);

        final Sprite projSprite = new SpriteImpl(top);
        projSprite.updatePosition(proj.getPosition());
        projSprite.updateRotation(proj.getAngle());
        return projSprite;
    }

    private DataAccessLayer getDAL() {
        return JSONDataAccessLayer.getInstance();
    }
}