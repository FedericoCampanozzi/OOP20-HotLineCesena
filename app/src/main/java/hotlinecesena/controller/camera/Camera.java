package hotlinecesena.controller.camera;

import hotlinecesena.controller.Updatable;
import hotlinecesena.view.entities.Sprite;
import javafx.geometry.Point2D;

/**
 * Models a camera in the style of a top-down shooter game.
 */
public interface Camera extends Updatable {

    /**
     * Binds this camera to an entity's {@link Sprite}.
     * @param sprite the Sprite this camera will be bound to.
     */
    void bindToSprite(Sprite sprite);

    /**
     * Returns this camera's current position.
     * @return this camera's position.
     */
    Point2D getPosition();
}
