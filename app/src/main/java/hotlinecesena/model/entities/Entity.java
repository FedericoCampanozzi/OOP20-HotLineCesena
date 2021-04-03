package hotlinecesena.model.entities;

import hotlinecesena.model.events.Publisher;
import javafx.geometry.Point2D;

/**
 *
 * Represents a basic game entity to which a set of coordinates is associated.
 *
 */
public interface Entity extends Publisher {

    /**
     * Gets this entity's current coordinates on the game map.
     *
     * @return this entity's coordinates.
     */
    Point2D getPosition();

    /**
     * Gets the angle this actor is currently facing.
     *
     * @return the actor's angle.
     */
    double getAngle();

    /**
     *
     * Sets the angle this entity will face.
     *
     * @param angle
     */
    void setAngle(double angle);
}
