package hotlinecesena.model.camera;

import java.util.Objects;

import hotlinecesena.model.entities.Entity;
import hotlinecesena.utilities.MathUtils;
import javafx.geometry.Point2D;

/**
 * Camera implementation. It makes use of interpolation to simulate
 * smooth movement, making it time-dependent.
 *
 */
public final class CameraImpl implements Camera {

    private static final int ACCEL = 30;
    private static final double SHARPNESS = 0.2;
    private Point2D cameraPos = Point2D.ZERO;
    private Entity entity;

    /**
     *
     * @param entity the {@link Entity} to be followed by this camera.
     * @throws NullPointerException if {@code entity} is null.
     */
    public CameraImpl(final Entity entity) {
        this.attachTo(entity);
    }

    /**
     * @throws NullPointerException if the supplied {@code Entity} is null.
     */
    @Override
    public void attachTo(final Entity entity) {
        this.entity = Objects.requireNonNull(entity);
    }

    @Override
    public Point2D getCameraPosition() {
        return cameraPos;
    }

    /**
     * Interpolation is time-dependent.
     */
    @Override
    public void update(final double timeElapsed) {
        final double blend = MathUtils.blend(SHARPNESS, ACCEL, timeElapsed);
        cameraPos = MathUtils.lerp(cameraPos, entity.getPosition(), blend);
    }
}
