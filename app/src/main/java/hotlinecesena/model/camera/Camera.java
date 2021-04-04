package hotlinecesena.model.camera;

import hotlinecesena.model.entities.Entity;
import javafx.geometry.Point2D;

/**
 * Models a camera that follows an {@link Entity}'s movements.
 *
 */
public interface Camera {

    /**
     * Attaches this camera to an entity.
     *
     * @param entity the {@code Entity} to be followed.
     */
    void attachTo(Entity entity);

    /**
     * Gets the current camera position.
     * @return the camera position.
     */
    Point2D getCameraPosition();

    /**
     * Updates aspects of the camera which depend on time.
     * @param timeElapsed the time elapsed since the last update.
     */
    void update(double timeElapsed);
}
