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
     * Allows to invoke one or more {@link Player} methods.
     *
     * @param player the {@code Player} instance this
     * command will be given to.
     */
    void execute(Player player);
}
