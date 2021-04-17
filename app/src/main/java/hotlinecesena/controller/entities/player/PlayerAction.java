package hotlinecesena.controller.entities.player;

import java.util.Optional;

import hotlinecesena.model.entities.actors.Direction;
import hotlinecesena.model.entities.actors.DirectionList;
import hotlinecesena.model.entities.actors.player.Player;

/**
 *
 * List of constants used as a bridge between Model and Controller.
 *
 * They contain commands that the Player may receive from its Controller class.
 *
 */
public enum PlayerAction {

    /**
     * Move north.
     */
    MOVE_NORTH(null, DirectionList.NORTH),

    /**
     * Move south.
     */
    MOVE_SOUTH(null, DirectionList.SOUTH),

    /**
     * Move east.
     */
    MOVE_EAST(null, DirectionList.EAST),

    /**
     * Move west.
     */
    MOVE_WEST(null, DirectionList.WEST),

    /**
     * Attack.
     */
    ATTACK(Player::attack, null),

    /**
     * Reload weapon.
     */
    RELOAD(Player::reload, null),

    /**
     * Activate an item on ground.
     */
    USE(Player::use, null);

    private Optional<Command> command;
    private Optional<Direction> direction;

    PlayerAction(final Command command, final Direction direction) {
        this.command = Optional.ofNullable(command);
        this.direction = Optional.ofNullable(direction);
    }

    /**
     * Returns the {@link Command} encapsulated by this constant.
     * @return an {@link Optional} containing the {@code Command}
     * associated with this constant, or an empty {@code Optional}
     * if this constant represents a movement action.
     */
    public Optional<Command> getCommand() {
        return command;
    }

    /**
     * Returns the {@link Direction} encapsulated by this constant.
     * @return an {@link Optional} containing a direction, or an
     * empty {@code Optional} if this constant does not represent a
     * movement action.
     */
    public Optional<Direction> getDirection() {
        return direction;
    }
}
