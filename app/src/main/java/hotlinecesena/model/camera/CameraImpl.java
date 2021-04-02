package hotlinecesena.model.camera;

import java.util.Objects;

import hotlinecesena.model.entities.Entity;
import hotlinecesena.utilities.MathUtils;
import javafx.geometry.Point2D;

public class CameraImpl implements Camera {

    private static final int ACCEL = 30;
    private static final double SHARPNESS = 0.2;
    private Point2D cameraPos = Point2D.ZERO;
    private Entity entity;
    
    public CameraImpl(final Entity entity) {
        this.attachTo(entity);
    }

    @Override
    public void attachTo(final Entity entity) throws NullPointerException {
        this.entity = Objects.requireNonNull(entity);
    }

    @Override
    public Point2D getCameraPosition() {
        return this.cameraPos;
    }

    @Override
    public void update(final double timeElapsed) {
        final double blend = MathUtils.blend(SHARPNESS, ACCEL, timeElapsed);
        this.cameraPos = MathUtils.lerp(cameraPos, entity.getPosition(), blend);
    }
}
