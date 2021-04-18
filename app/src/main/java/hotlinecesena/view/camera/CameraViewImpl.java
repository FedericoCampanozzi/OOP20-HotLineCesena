package hotlinecesena.view.camera;

import java.util.Objects;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.utilities.MathUtils;
import hotlinecesena.view.entities.Sprite;
import hotlinecesena.view.input.InputListener;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;

/**
 * Simple camera controller for JavaFX.
 *
 */
public final class CameraViewImpl implements CameraView {

    private static final double HUD_SIZE = JSONDataAccessLayer.getInstance().getSettings().getBotHudHeight()
            + JSONDataAccessLayer.getInstance().getSettings().getTopHudHeight();
    private static final double ACCEL = 30.0;
    private static final double SHARPNESS = 0.2;
    private static final double MOUSE_LEEWAY = 0.002;
    private Pane pane;
    private Sprite sprite;
    private final InputListener listener;
    private final Translate paneTranslate = new Translate();

    /**
     * Instantiates a new Camera and binds it to a given {@link Sprite}.
     * @param sprite the {@code Sprite} to which this camera will be bound to.
     * @param listener the {@link InputListener} used to retrieve mouse coordinates.
     * Must be already configured to receive inputs from a {@link Scene}.
     * @throws NullPointerException if the given sprite or listener are null.
     */
    public CameraViewImpl(@Nonnull final Sprite sprite, @Nonnull final InputListener listener) {
        this.bindToSprite(sprite);
        this.listener = Objects.requireNonNull(listener);
    }

    /**
     * @throws NullPointerException if the supplied {@code sprite} is null.
     */
    @Override
    public void bindToSprite(@Nonnull final Sprite sprite) {
        this.sprite = Objects.requireNonNull(sprite);
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
    public void removeCameraFromPane() {
        if (pane != null && pane.getTransforms().contains(paneTranslate)) {
            pane.getTransforms().remove(paneTranslate);
        }
    }

    @Override
    public Point2D getPosition() {
        return new Point2D(-paneTranslate.getX(), -paneTranslate.getY());
    }

    /**
     * @implSpec Updates the {@link Translate} position based on the movements of
     * the attached {@link Sprite} and the distance between Sprite and cursor.
     * @throws IllegalStateException if this camera is not attached to a pane.
     */
    @Override
    public Consumer<Double> getUpdateMethod() {
        return deltaTime -> {
            if (pane == null) {
                throw new IllegalStateException("Camera not properly initialized.");
            }
            final double blend = MathUtils.blend(SHARPNESS, ACCEL, deltaTime);
            // Difference between mouse coordinates and sprite coordinates
            final Point2D spriteMouseDistance = listener.deliverInputs().getValue().subtract(
                    sprite.getPositionRelativeToScene());
            final Point2D targetPos = sprite.getPositionRelativeToParent()
                    .subtract(pane.getScene().getWidth() / 2,
                            (pane.getScene().getHeight() - HUD_SIZE) / 2)
                    //Move camera away from the sprite based on the cursor's position
                    .add(spriteMouseDistance.multiply(MOUSE_LEEWAY));

            final Point2D newPos = MathUtils.lerp(this.getPosition(), targetPos, blend);
            paneTranslate.setX(-newPos.getX());
            paneTranslate.setY(-newPos.getY());
        };
    }
}
