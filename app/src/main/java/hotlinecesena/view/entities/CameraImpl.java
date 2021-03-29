package hotlinecesena.view.entities;

import hotlinecesena.utilities.MathUtils;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;

/**
 * Simple camera implementation.
 *
 */
public class CameraImpl implements Camera {

    private static final int ACCEL = 30;
    private static final float CENTERING_FACTOR = 2.8125f;
    private static final float SHARPNESS = 0.2f;
    private static final float SPEED_SCALE = 2.0f;
    private final Translate paneTranslate = new Translate();
    private final Scene scene;

    public CameraImpl(final Scene scene) {
        this.scene = scene;
    }

    @Override
    public void attachToPane(final Pane pane) {
        pane.getTransforms().add(this.paneTranslate);
    }
    
    @Override
    public void detachFromPane(final Pane pane) {
        if (pane.getTransforms().contains(this.paneTranslate)) {
            pane.getTransforms().remove(this.paneTranslate);
        }
    }

    @Override
    public void update(final Point2D playerPos, final double deltaTime) {
        // Blend formula taken from gamedev.stackexchange.com/a/152466
        final double blend = 1 - Math.pow(1 - SHARPNESS, deltaTime * ACCEL);

        final Point2D cameraPos = new Point2D(paneTranslate.getX(), paneTranslate.getY());
        final Point2D newPos = MathUtils.lerp(
                cameraPos,
                playerPos.multiply(SPEED_SCALE)
                    .subtract(scene.getWidth()/CENTERING_FACTOR, scene.getHeight()/CENTERING_FACTOR),
                blend);
        this.paneTranslate.setX(newPos.getX());
        this.paneTranslate.setY(newPos.getY());
    }
}
