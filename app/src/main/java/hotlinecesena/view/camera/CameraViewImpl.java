package hotlinecesena.view.camera;

import java.util.Objects;

import javax.annotation.Nonnull;

import javafx.geometry.Point2D;
import javafx.scene.layout.Region;
import javafx.scene.transform.Translate;

/**
 * Simple camera controller for JavaFX.
 */
public final class CameraViewImpl implements CameraView {

    private Region region;
    private final Translate regionTranslate = new Translate();

    /**
     * Instantiates a new CameraView and binds it to the
     * given {@link Region}.
     * @param region the {@code Region} this camera will be
     * bound to.
     * @throws NullPointerException if the supplied {@code Region} is null.
     */
    public CameraViewImpl(@Nonnull final Region region) {
        this.setRegion(region);
    }

    @Override
    public Region getRegion() {
        return region;
    }

    /**
     * @throws NullPointerException if the supplied {@code Region} is null.
     */
    @Override
    public void setRegion(@Nonnull final Region region) {
        this.detachCameraFromRegion();
        this.region = Objects.requireNonNull(region);
        region.getTransforms().add(regionTranslate);
    }

    @Override
    public Point2D getPosition() {
        return new Point2D(-regionTranslate.getX(), -regionTranslate.getY());
    }

    /**
     * @throws IllegalStateException if this camera is not attached to a Region.
     */
    @Override
    public void setPosition(@Nonnull final Point2D newPosition) {
        if (region == null) {
            throw new IllegalStateException("Camera is not attached to a Region.");
        }
        regionTranslate.setX(-newPosition.getX());
        regionTranslate.setY(-newPosition.getY());
    }

    @Override
    public void detachCameraFromRegion() {
        if (region != null && region.getTransforms().contains(regionTranslate)) {
            region.getTransforms().remove(regionTranslate);
        }
    }
}
