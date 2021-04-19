package hotlinecesena.controller.entities.player.command;

import hotlinecesena.model.entities.actors.player.Player;
import javafx.geometry.Point2D;

/**
 * Encapsulates Player::move.
 */
public final class MoveCommand implements Command {

    private final Point2D direction;

    /**
     * Instantiates a new {code MoveCommand}.
     * @param direction the direction the player will
     * move in.
     */
    public MoveCommand(final Point2D direction) {
        this.direction = direction;
    }

    @Override
    public void execute(final Player player) {
        player.move(direction);
    }
}
