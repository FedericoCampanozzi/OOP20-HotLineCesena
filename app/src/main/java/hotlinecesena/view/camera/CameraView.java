package hotlinecesena.view.camera;

import hotlinecesena.view.entities.Sprite;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;

/**
 *
 * Controller for JavaFX which follows the movements of a {@link Sprite}.
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
     * Updates the {@link Translate} position based on the movements of a {@link Sprite}.
     *
     * @param spritePosition Sprite position on the view.
     * @param deltaTime time elapsed since the last update.
     */
    void update(Point2D spritePosition, double deltaTime);
}
