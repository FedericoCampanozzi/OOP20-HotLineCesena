package hotlinecesena.controller.entities.player;

import hotlinecesena.view.entities.Sprite;
import hotlinecesena.view.input.InputListener;

/**
 *
 * Models a factory for {@link PlayerController}s.
 *
 */
public interface PlayerControllerFactory {

    /**
     * Instantiates a new {@code PlayerController} with the given {@link Sprite}
     * and {@link InputListener}.
     * @param sprite the player's sprite.
     * @param listener a reference to the {@link InputListener} used by the View.
     * @return a new {@code PlayerController} with the given {@code Sprite}
     * and {@code InputListener}.
     */
    PlayerController createPlayerController(Sprite sprite, InputListener listener);
}
