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
     * Attaches a {@link Translate} to a JavaFX {@link Pane} to simulate camera movement.
     * 
     * @param pane the {@code Pane} which will be translated.
     */
    void attachToPane(Pane pane);
    
    /**
     * Detaches this camera's {@link Translate} from a JavaFX {@link Pane}, if it was previously attached.
     * 
     * @param pane the {@code Pane} from which the {@code Translate} will be detached.
     */
    void detachFromPane(Pane pane);

    /**
     * Updates the camera position based on an entity's sprite coordinates.
     * 
     * @param spritePos the entity's sprite position on the Scene.
     * @param deltaTime time elapsed since the last frame was rendered. Used for interpolating
     * camera movement.
     */
    void update(Point2D spritePos, double deltaTime);
}
