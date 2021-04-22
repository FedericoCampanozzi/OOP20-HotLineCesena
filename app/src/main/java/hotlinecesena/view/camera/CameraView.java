package hotlinecesena.view.camera;

import javafx.geometry.Point2D;
import javafx.scene.layout.Region;

/**
 *
 * Controller for JavaFX which follows the movements of an {@link Entity}.
 *
 */
public interface CameraView {

    /**
     * Returns the region associated to this camera.
     * @return the region associated to this camera.
     */
    Region getRegion();

    /**
     * Attaches this camera to a JavaFX {@link Region}.
     * @param region the {@code Region} which will be translated
     * by this camera.
     */
    void setRegion(Region region);

    /**
     * Returns this camera's current coordinates.
     * @return this camera's current coordinates.
     */
    Point2D getPosition();

    /**
     * Sets this camera's position.
     * @param newPosition the new position this camera will
     * be set to.
     */
    void setPosition(Point2D newPosition);

    /**
     * Detaches this camera from the currently set {@link Region}.
     */
    void detachCameraFromRegion();
}
