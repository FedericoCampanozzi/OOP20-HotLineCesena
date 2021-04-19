package hotlinecesena.controller.camera;

import java.util.Objects;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import hotlinecesena.utilities.MathUtils;
import hotlinecesena.view.camera.CameraView;
import hotlinecesena.view.entities.Sprite;
import hotlinecesena.view.input.InputListener;
import javafx.geometry.Point2D;

/**
 * Camera implementation.
 */
public final class CameraImpl implements Camera {

    private static final double ACCEL = 30.0;
    private static final double SHARPNESS = 0.2;
    private static final double MOUSE_LEEWAY = 0.002;
    private final CameraView cameraView;
    private Sprite entitySprite;
    private final InputListener listener;

    /**
     * Instantiates a new CameraImpl and binds it to the given {@link Sprite}.
     * @param cameraView the {@link CameraView} this controller will handle.
     * @param entitySprite the {@code Sprite} this camera will be bound to.
     * @param listener the {@link InputLister} this camera will use to retrieve
     * mouse coordinates.
     * @throws NullPointerException if the given cameraView, entitySprite or listener
     * are null.
     */
    public CameraImpl(@Nonnull final CameraView cameraView, @Nonnull final Sprite entitySprite,
            @Nonnull final InputListener listener) {
        this.cameraView = Objects.requireNonNull(cameraView);
        this.bindToSprite(entitySprite);
        this.listener = Objects.requireNonNull(listener);
    }

    /**
     * @throws NullPointerException if the given Sprite is null.
     */
    @Override
    public void bindToSprite(@Nonnull final Sprite sprite) {
        entitySprite = Objects.requireNonNull(sprite);
    }

    @Override
    public Point2D getPosition() {
        return cameraView.getPosition();
    }

    /**
     * @implSpec Updates the {@link CameraView} position based on the movements of
     * the attached {@link Sprite} and the distance between Sprite and cursor.
     */
    @Override
    public Consumer<Double> getUpdateMethod() {
        return deltaTime -> {
            final double blend = MathUtils.blend(SHARPNESS, ACCEL, deltaTime);
            // Difference between mouse coordinates and sprite coordinates
            final Point2D spriteMouseDistance = listener.deliverInputs()
                    .getValue()
                    .subtract(entitySprite.getPositionRelativeToScene());
            final Point2D targetPos = entitySprite.getPositionRelativeToParent()
                    .subtract(cameraView.getRegion().getWidth() / 2.0, cameraView.getRegion().getHeight() / 2.0)
                    .add(spriteMouseDistance.multiply(MOUSE_LEEWAY)); //Move camera away based on mouse coordinates.

            final Point2D newPos = MathUtils.lerp(cameraView.getPosition(), targetPos, blend);
            cameraView.setPosition(newPos);
        };
    }
}
