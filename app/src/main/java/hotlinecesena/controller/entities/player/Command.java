package hotlinecesena.controller.entities.player;

import hotlinecesena.model.entities.actors.player.Player;

/**
 *
 * Interface used for applying the Command pattern.
 *
 */
@FunctionalInterface
public interface Command {

    /**
     * Allows to invoke one or more Player methods.
     *
     * @param player
     */
    void execute(Player player);
}
