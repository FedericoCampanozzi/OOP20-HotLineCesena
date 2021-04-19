package hotlinecesena.controller.entities.player;

import hotlinecesena.controller.entities.EntityController;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.view.entities.Sprite;
import hotlinecesena.view.input.InputListener;

/**
 *
 * Factory for {@link EntityController}s which will
 * handle aspects related to the {@link Player}.
 */
public interface PlayerControllerFactory {

    /**
     * Instantiates a new {@link EntityController} which will
     * handle aspects related to the {@link Player}.
     * @param player the {@code Player} instance this controller will handle.
     * @param sprite the player's sprite.
     * @param listener a reference to the {@link InputListener} used by the View.
     * @return a new player controller.
     */
    EntityController create(Player player, Sprite sprite, InputListener listener);
}
