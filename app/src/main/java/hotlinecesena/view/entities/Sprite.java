package hotlinecesena.view.entities;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

/**
 *
 * View representation of a game {@link Entity}.
 *
 */
public interface Sprite {

    /**
     * Updates this sprite's position based on the entity's coordinates
     * in the Model.
     * @param entityPos the entity's coordinates in the Model.
     */
    void updatePosition(Point2D entityPos);

    /**
     * Updates this sprite's rotation based on the entity's angle
     * in the Model.
     * @param entityAngle the entity's angle in the Model.
     */
    void updateRotation(double entityAngle);

    /**
     * Updates this sprite's image.
     * @param image the new image.
     */
    void updateImage(Image image);

    /**
     * Returns this sprite's position relative to the View.
     * @return this sprite's position.
     */
    Point2D getSpritePosition();
}
