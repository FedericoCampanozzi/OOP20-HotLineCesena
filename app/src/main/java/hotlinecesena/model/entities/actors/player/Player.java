package hotlinecesena.model.entities.actors.player;

import hotlinecesena.model.entities.actors.Actor;

/**
 *
 * Models the player character and their unique actions.
 *
 */
public interface Player extends Actor {

    /**
     * Uses or picks up items found on ground.
     */
    void use();

    /**
     * Returns the radius of the noise currently emitted by the player based on their state.
     * @return the radius of the noise.
     */
    double getNoiseRadius();
}
