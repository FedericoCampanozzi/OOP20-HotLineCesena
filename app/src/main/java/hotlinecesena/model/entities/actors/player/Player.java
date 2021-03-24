package hotlinecesena.model.entities.actors.player;

import java.util.Set;

import hotlinecesena.model.entities.actors.Actor;
import javafx.geometry.Point2D;
import javafx.util.Pair;

/**
 * 
 * Models the player character and their unique actions.
 *
 */
public interface Player extends Actor {

    /**
     * Picks up a nearby item or weapon, if there are any.
     */
    void pickUp();
    
    /**
     * Uses a usable item currently held in the inventory.
     */
    void use();
    
    /**
     * Drops a usable item from the inventory.
     */
    void dropUsable();
    
    /**
     * Drops an equipped from the inventory.
     */
    void dropEquipped();

    /**
     * 
     * @return the radius of the noise currently emitted by the player based on their state.
     */
    double getNoiseRadius();
    
    /**
     * Delegates the handling of all commands to the State objects.
     * 
     * @param commandsToHandle
     * @param timeElapsed
     */
    void handleCurrentState(Pair<Set<CommandType>, Point2D> commandsToHandle, double timeElapsed);
}
