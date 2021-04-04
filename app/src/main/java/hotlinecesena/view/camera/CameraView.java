package hotlinecesena.view.camera;

import hotlinecesena.model.entities.Entity;
import javafx.scene.layout.Pane;

/**
 * 
 * Controller for JavaFX which follows the movements of a {@link Camera}.
 *
 */
public interface CameraController {

    /**
     * Attaches a {@link Translate} to a JavaFX {@link Pane} to simulate camera movement.
     * 
     * @param pane the {@code Pane} which will be translated.
     * @throws NullPointerException if the supplied {@code Pane} is null.
     */
    void setPane(Pane pane) throws NullPointerException;
    
    /**
     * Detaches the {@link Translate} from this camera's current JavaFX {@link Pane}.
     */
    void removePane();
    
    /**
     * Orders the {@link Camera} model to follow a specific {@link Entity}.
     * 
     * @param entity
     * @throws NullPointerException if the supplied {@code Entity} is null.
     */
    void setEntity(Entity entity) throws NullPointerException;

    /**
     * Updates the {@link Translate} position based on {@link Camera} movements.
     * 
     * @param deltaTime
     */
    void update(double deltaTime);
}
