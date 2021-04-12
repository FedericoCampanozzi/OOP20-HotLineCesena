package hotlinecesena.controller.entities.player;

import hotlinecesena.view.entities.Sprite;

/**
 *
 * Models a factory for {@link PlayerController}s.
 *
 */
public interface PlayerControllerFactory {

    /**
     * Instantiates a new {@code PlayerController} with the given {@link Sprite}.
     * @param sprite the player sprite.
     * @return a new {@code PlayerController} with the given {@code Sprite}.
     */
    PlayerController createPlayerController(Sprite sprite);
}
