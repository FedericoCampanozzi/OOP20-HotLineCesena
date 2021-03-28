package hotlinecesena.view.entities;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

/**
 * 
 * Models a camera following a movable entity.
 *
 */
public interface Camera {

    /**
     * Attaches this camera to a JavaFX pane.
     * 
     * @param pane the {@link Pane} which will be translated.
     */
    void attachToPane(Pane pane);

    /**
     * Updates the camera position based on an entity's sprite coordinates.
     */
    void update(Point2D spritePos, double deltaTime);
}
