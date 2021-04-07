package hotlinecesena.model.entities.actors.player;

import javafx.geometry.Point2D;

/**
 *
 * Models a factory that instantiates the {@link Player}.
 *
 */
public interface PlayerFactory {

    /**
     * Creates a {@code Player} with default values specified
     * by the factory implementation.
     * @return a {@code Player} with default values.
     */
    Player createPlayer();

    /**
     * Creates a {@code Player} with custom position and
     * rotation angle.
     * @param position the starting position of the Player.
     * @param angle the starting angle that the Player will face.
     * @return a {@code Player} with the aforementioned custom values.
     */
    Player createPlayer(Point2D position, double angle);
}
