package hotlinecesena.view.camera;

import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;

/**
 *
 * Controller for JavaFX which follows the movements of a {@link Camera}.
 *
 */
public interface CameraView {

    /**
     * Attaches a {@link Translate} to a JavaFX {@link Pane} to simulate camera movement.
     *
     * @param pane the {@code Pane} which will be translated.
     */
    void setPane(Pane pane);

    /**
     * Detaches the {@link Translate} from this camera's current JavaFX {@link Pane}.
     */
    void removePane();

    /**
     * Updates the {@link Translate} position based on {@link Camera} movements.
     *
     * @param deltaTime
     */
    void update(double deltaTime);
}
