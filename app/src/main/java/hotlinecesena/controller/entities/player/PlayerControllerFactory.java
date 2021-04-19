package hotlinecesena.controller.entities.player;

import hotlinecesena.controller.entities.EntityController;
import hotlinecesena.view.entities.Sprite;
import hotlinecesena.view.input.InputListener;

/**
 *
 * Models a factory for player controllers.
 *
 */
public interface PlayerControllerFactory {

    /**
     * Instantiates a new player controller with the given {@link Sprite}
     * and {@link InputListener}.
     * @param sprite the player's sprite.
     * @param listener a reference to the {@link InputListener} used by the View.
     * @return a new player controller with the given {@code Sprite}
     * and {@code InputListener}.
     */
    EntityController createPlayerController(Sprite sprite, InputListener listener);
}
