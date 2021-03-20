package hotlinecesena.model.entities;

import javafx.geometry.Point2D;

/**
 * 
 * Represents a basic game entity to which a set of coordinates is associated.
 *
 */
public interface Entity {
    
    /**
     * 
     * @return this entity's coordinates on the game map.
     */
    Point2D getPosition();
    
    /**
     * Gets the angle this actor is currently facing.
     * 
     * @return the actor's angle.
     */
    double getAngle();
}
