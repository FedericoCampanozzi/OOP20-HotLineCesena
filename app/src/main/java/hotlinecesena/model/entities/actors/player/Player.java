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
     * Picks up nearby items, weapons or ammo, if there are any.
     */
    void pickUp();
    
    /**
     * Uses items found on ground.
     */
    void use();

    /**
     * 
     * @return the radius of the noise currently emitted by the player based on their state.
     */
    double getNoiseRadius();
    
    /**
     * Handles all commands received by the controller.
     * 
     * @param commandsToHandle
     * @param timeElapsed
     */
    void handle(Pair<Set<PlayerAction>, Point2D> commandsToHandle, double timeElapsed);
}
