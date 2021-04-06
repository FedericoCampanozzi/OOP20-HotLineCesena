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
     * Gets the angle this actor is currently facing.
     *
     * @return the actor's angle.
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
