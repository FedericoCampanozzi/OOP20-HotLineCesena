package hotlinecesena.model.entities.actors.player;

import static hotlinecesena.model.entities.actors.player.PlayerAction.MOVE_EAST;
import static hotlinecesena.model.entities.actors.player.PlayerAction.MOVE_NORTH;
import static hotlinecesena.model.entities.actors.player.PlayerAction.MOVE_SOUTH;
import static hotlinecesena.model.entities.actors.player.PlayerAction.MOVE_WEST;

import java.util.Set;

import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.entities.actors.DirectionList;
import hotlinecesena.utilities.MathUtils;
import javafx.geometry.Point2D;
import javafx.util.Pair;

public abstract class AbstractState implements State {

    private final Player player;

    protected AbstractState(final Player player) {
        this.player = player;
    }

    /*
     * Template method
     */
    public final void handle(final Pair<Set<PlayerAction>, Point2D> actionsToHandle, final double timeElapsed) {
        if (this.player.getCurrentHealth() == 0) {
            this.player.setActorStatus(ActorStatus.DEAD);
        } else {
            this.handleAllowedActions(actionsToHandle.getKey(), actionsToHandle.getValue(), timeElapsed);
        }
    }

    /**
     * 
     * Subclasses must complete this method by handling commands allowed while in a specific state.
     * 
     * @param receivedCommands
     * @param rotation
     * @param timeElapsed
     */
    protected abstract void handleAllowedActions(final Set<PlayerAction> receivedCommands,
            final Point2D rotation, final double timeElapsed);

    /**
     * "Shortcut" method for handling movement commands only.
     * 
     * @param movements
     * @param timeElapsed
     */
    protected void handleMovement(final Set<PlayerAction> movements, final double timeElapsed) {
        Point2D newMovementDir = DirectionList.NONE.get();
        if (movements.contains(MOVE_NORTH)) {
            newMovementDir = newMovementDir.add(DirectionList.NORTH.get());
        }
        if (movements.contains(MOVE_SOUTH)) {
            newMovementDir = newMovementDir.add(DirectionList.SOUTH.get());
        }
        if (movements.contains(MOVE_EAST)) {
            newMovementDir = newMovementDir.add(DirectionList.EAST.get());
        }
        if (movements.contains(MOVE_WEST)) {
            newMovementDir = newMovementDir.add(DirectionList.WEST.get());
        }

        if (!newMovementDir.equals(DirectionList.NONE.get())) {
            final double magnitude = newMovementDir.magnitude();
            if (magnitude > 1) {
                /*
                 * If we don't normalize the vector, then the actor speed would amount to
                 * ||(1, 1)|| * actor.getSpeed() if it were to e.g. attempt to move south-east.
                 */
                newMovementDir = MathUtils.normalizeDiagonalMovement(newMovementDir);
            }
            this.player.move(newMovementDir.multiply(timeElapsed));
        }
    }

    /**
     * "Shortcut" method for handling rotation only.
     * 
     * @param rotation
     */
    protected void handleRotation(final Point2D rotation) {
        final double oldAngle = this.player.getAngle();
        final double newAngle = Math.atan2(rotation.getY(), rotation.getX());
        if (oldAngle != newAngle) {
            this.player.setAngle(newAngle);
        }
    }

    /**
     * Returns the player instance.
     * @return
     */
    protected Player getPlayer() {
        return this.player;
    }
}
