package hotlinecesena.view.camera;

import java.util.Objects;

import hotlinecesena.model.camera.Camera;
import hotlinecesena.model.entities.Entity;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;

/**
 * Simple camera controller for JavaFX.
 *
 */
public class CameraControllerImpl implements CameraController {

    private static final float CENTERING_FACTOR = 2.23f; // By trial and error
    private static final float SPEED_SCALE = 2.0f;
    private final Translate paneTranslate = new Translate();
    private final Camera camera;
    private Pane pane;

    public CameraControllerImpl(final Camera camera, final Pane pane) {
        this.camera = Objects.requireNonNull(camera);
        this.setPane(pane);
    }

    @Override
    public void setPane(final Pane pane) throws NullPointerException {
        this.pane = Objects.requireNonNull(pane);
        pane.getTransforms().add(this.paneTranslate);
    }

    @Override
    public void removePane() {
        pane.getTransforms().remove(this.paneTranslate);
    }

    public void setEntity(Entity entity) throws NullPointerException {
        this.camera.attachTo(Objects.requireNonNull(entity));
    }

    @Override
    public void update(final double deltaTime) {
        this.camera.update(deltaTime);
        final Point2D newTranslate = this.camera.getCameraPosition()
                .multiply(SPEED_SCALE)
                .subtract(pane.getWidth()/CENTERING_FACTOR, pane.getHeight()/CENTERING_FACTOR);
        this.paneTranslate.setX(-newTranslate.getX());
        this.paneTranslate.setY(-newTranslate.getY());
    }
}
