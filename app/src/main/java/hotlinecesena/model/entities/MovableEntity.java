package hotlinecesena.model.entities;

import javafx.geometry.Point2D;

/**
 *
 * Represents a generic entity capable of moving in a specified direction.
 *
 */
public interface MovableEntity extends Entity {

    /**
     * Makes this entity move in a certain direction.
     *
     * @param direction the direction this entity will move in.
     */
    void move(Point2D direction);

    /**
     * Tests whether this entity will collide with another.
     * @param newPosition the new position in which this entity is going to move.
     * @param other the other entity.
     * @return {@code true} if this entity will collide with
     * another, {@code false} otherwise.
     */
    boolean isCollidingWith(Point2D newPosition, Entity other);

    /**
     * Gets the angle this entity is currently facing.
     *
     * @return the entity's angle.
     */
    double getAngle();

    /**
     * Sets the angle this entity will face.
     * @param angle the new angle this entity will face.
     */
    void setAngle(double angle);

    /**
     *
     * @return the speed at which this entity moves.
     */
    double getSpeed();

    /**
     * Sets this entity's movement speed.
     *
     * @param speed
     */
    void setSpeed(double speed);
}
