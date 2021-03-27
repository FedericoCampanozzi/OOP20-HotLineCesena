package hotlinecesena.model.entities.actors.player;

import java.util.Optional;

import hotlinecesena.model.entities.actors.Direction;
import hotlinecesena.model.entities.actors.DirectionList;

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
    MOVE_NORTH(Optional.empty(), Optional.of(DirectionList.NORTH)),

    /**
     * Move south.
     */
    MOVE_SOUTH(Optional.empty(), Optional.of(DirectionList.SOUTH)),

    /**
     * Move east.
     */
    MOVE_EAST(Optional.empty(), Optional.of(DirectionList.EAST)),

    /**
     * Move west.
     */
    MOVE_WEST(Optional.empty(), Optional.of(DirectionList.WEST)),

    /**
     * Attack.
     */
    ATTACK(Optional.of(p -> p.attack()), Optional.empty()),

    /**
     * Reload weapon.
     */
    RELOAD(Optional.of(p -> p.reload()), Optional.empty()),

    /**
     * Activate an item on ground.
     */
    USE(Optional.of(p -> p.use()), Optional.empty());

    private Optional<Command> command;
    private Optional<Direction> direction;

    PlayerAction(final Optional<Command> command, final Optional<Direction> dir) {
        this.command = command;
        this.direction = dir;
    }

    /**
     * 
     * @return an {@link Optional} encapsulating the {@link Command} associated with this constant.
     */
    public Optional<Command> getCommand() {
        return this.command;
    }

    /**
     * 
     * @return an {@link Optional} which encapsulates a direction.
     * <br>
     * If this constant does not represent a movement action, an empty {@link Optional} will be returned.
     */
    public Optional<Direction> getDirection() {
        return this.direction;
    }
}
