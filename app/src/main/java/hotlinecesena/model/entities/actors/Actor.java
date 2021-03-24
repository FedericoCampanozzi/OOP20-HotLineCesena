package hotlinecesena.model.entities.actors;

import hotlinecesena.model.entities.MovableEntity;
import hotlinecesena.model.inventory.Inventory;

/**
 * 
 * Animated entity that is able to perform a set of basic actions.
 *
 */
public interface Actor extends MovableEntity {
    
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
     * Heals this actor by a certain amount of health points.
     * 
     * @param hp
     */
    void heal(double hp);
    
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
     * Returns this actor's current {@link ActorStatus} value.
     * 
     * @return 
     */
    ActorStatus getActorStatus();
    
    /**
     * Sets this actor's {@link ActorStatus} to {@code s}.
     * 
     * @param s
     */
    void setActorStatus(ActorStatus s);
}
