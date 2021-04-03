package hotlinecesena.model.entities.actors.player;

import hotlinecesena.model.entities.actors.Actor;

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
     *
     * Updates certain player functionalities that are based on time.
     *
     * @param timeElapsed
     */
    void update(double timeElapsed);
}
