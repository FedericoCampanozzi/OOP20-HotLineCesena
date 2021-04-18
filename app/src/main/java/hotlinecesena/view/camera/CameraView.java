package hotlinecesena.view.camera;

import hotlinecesena.controller.Updatable;
import hotlinecesena.view.entities.Sprite;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;

/**
 *
 * Controller for JavaFX which follows the movements of a {@link Sprite}.
 *
 */
public interface CameraView extends Updatable {

    /**
     * Binds this camera to a given {@link Sprite}.
     * @param sprite the sprite to which this camera will be bound to.
     */
    void bindToSprite(Sprite sprite);

    /**
     * Attaches a {@link Translate} to a JavaFX {@link Pane} to simulate camera movement.
     * @param pane the {@code Pane} which will be translated.
     */
    void setPane(Pane pane);

    /**
     * Detaches the {@link Translate} from this camera's current JavaFX {@link Pane}.
     */
    void removeCameraFromPane();

    /**
     * Returns this camera's {@link Translate} coordinates.
     * @return this camera's {@code Translate} coordinates.
     */
    Point2D getPosition();
}
