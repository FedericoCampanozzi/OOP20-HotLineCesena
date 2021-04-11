package hotlinecesena.view.camera;

import java.util.Objects;

import hotlinecesena.utilities.MathUtils;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;

/**
 * Simple camera controller for JavaFX.
 *
 */
public final class CameraViewImpl implements CameraView {

    private static final double HUD_HEIGHT = 100; //TODO Gotta retrieve this from DAL
    private static final double ACCEL = 30.0;
    private static final double SHARPNESS = 0.2;
    private Pane pane;
    private final Translate paneTranslate = new Translate();

    public CameraViewImpl(final Pane pane) {
        this.setPane(pane);
    }

    /**
     * @throws NullPointerException if the supplied {@code Pane} is null.
     */
    @Override
    public void setPane(final Pane pane) {
        this.pane = Objects.requireNonNull(pane);
        pane.getTransforms().add(paneTranslate);
    }

    @Override
    public void removePane() {
        pane.getTransforms().remove(paneTranslate);
    }

    @Override
    public void update(final Point2D spritePosition, final double deltaTime) {
        final double blend = MathUtils.blend(SHARPNESS, ACCEL, deltaTime);
        final Point2D currentPos = new Point2D(-paneTranslate.getX(), -paneTranslate.getY());
        final Point2D newPos = MathUtils.lerp(
                currentPos,
                spritePosition.subtract(
                        pane.getScene().getWidth() / 2,
                        (pane.getScene().getHeight() - HUD_HEIGHT) / 2),
                blend);
        paneTranslate.setX(-newPos.getX());
        paneTranslate.setY(-newPos.getY());
    }
}
