package hotlinecesena.model.entities.actors;

import hotlinecesena.model.entities.Entity;
import hotlinecesena.model.inventory.Inventory;

/**
 * 
 * Animated entity that is able to perform a set of basic actions.
 *
 */
public interface Actor extends Entity {

    /**
     * Makes this actor move in a certain direction at a specified speed.
     * 
     * @param direction the direction the actor will move in.
     * @param speed the speed at which the actor will move.
     */
    void move(Direction direction, double speed);

    /**
     * 
     * Sets the angle this actor will face.
     * 
     * @param angle
     */
    void setAngle(double angle);
    
    /**
     * Gets the angle this actor is currently facing.
     * 
     * @return the actor's angle.
     */
    double getAngle();
    
    /**
     * 
     * Makes this actor attack.
     */
    void attack();
    
    /**
     * Makes this actor reload its firearm, if it has one.
     */
    void reload();

    /**
     * Makes this actor receive damage from an external source.
     * 
     * @param damage
     */
    void takeDamage(double damage);
    
    /**
     * Returns the maximum health value for this actor.

     * @return the maximum health value.
     */
    double getMaxHealth();
    
    /**
     * Returns this actor's current health value.
     * 
     * @return the actor's current health.
     */
    double getCurrentHealth();
    
    /**
     * Returns a reference to this actor's inventory.
     * 
     * @return the actor's inventory.
     */
    Inventory getInventory();

    /**
     * Returns this actor's current {@link ActorState} value.
     * 
     * @return 
     */
    ActorState getState();
    
    /**
     * Sets this actor's {@link ActorState} to {@code s}.
     * 
     * @param s
     */
    void setState(ActorState s);
}
