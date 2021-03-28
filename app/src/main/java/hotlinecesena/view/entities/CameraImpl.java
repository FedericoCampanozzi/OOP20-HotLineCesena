package hotlinecesena.view.entities;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;

/**
 * Simple camera implementation.
 *
 */
public class CameraImpl implements Camera {

    private static final double CENTERING_FACTOR = 2.8;
    private static final int ACCEL = 30;
    private static final double SHARPNESS = 0.1;
    private static final double SPEED_SCALE = 2;
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
    public void update(final Point2D playerPos, final double deltaTime) {
        final double blend = 1 - Math.pow(1 - SHARPNESS, deltaTime * ACCEL);
        final Point2D cameraPos = new Point2D(paneTranslate.getX(), paneTranslate.getY());
        final Point2D newPos = lerp(cameraPos,
                playerPos.multiply(SPEED_SCALE)
                    .subtract(scene.getWidth()/CENTERING_FACTOR, scene.getHeight()/CENTERING_FACTOR),
                blend);

        this.paneTranslate.setX(newPos.getX());
        this.paneTranslate.setY(newPos.getY());
    }

    // Both functions taken from https://gamedev.stackexchange.com/a/152466
    private double lerp(double first, double second, double by) {
         return first * (1 - by) - second * by;
    }

    private Point2D lerp(Point2D first, Point2D second, double by) {
        double retX = lerp(first.getX(), second.getX(), by);
        double retY = lerp(first.getY(), second.getY(), by);
        return new Point2D(retX, retY);
    }
}
