package hotlinecesena.model.entities.actors.state;

import static hotlinecesena.model.entities.actors.player.CommandType.MOVE_EAST;
import static hotlinecesena.model.entities.actors.player.CommandType.MOVE_NORTH;
import static hotlinecesena.model.entities.actors.player.CommandType.MOVE_SOUTH;
import static hotlinecesena.model.entities.actors.player.CommandType.MOVE_WEST;

import java.util.Set;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.entities.actors.DirectionList;
import hotlinecesena.model.entities.actors.player.CommandType;
import javafx.geometry.Point2D;
import javafx.util.Pair;

public abstract class AbstractState<A extends Actor> implements State {

    private final A actor;

    protected AbstractState(final A actor) {
        this.actor = actor;
    }

    /*
     * Template method
     */
    public final void handle(final Pair<Set<CommandType>, Point2D> commandsToHandle, final double timeElapsed) {
        if (this.actor.getCurrentHealth() == 0) {
            this.actor.setActorStatus(ActorStatus.DEAD);
        } else {
            this.handleAllowedCommands(commandsToHandle.getKey(), commandsToHandle.getValue(), timeElapsed);
        }
    }

    /**
     * Subclasses must complete this method.
     * @param receivedCommands
     * @param rotation
     * @param timeElapsed
     */
    protected abstract void handleAllowedCommands(final Set<CommandType> receivedCommands,
            final Point2D rotation, final double timeElapsed);

    /**
     * "Shortcut" method for handling movement commands only.
     * 
     * @param movements
     * @param timeElapsed
     */
    protected void handleMovement(final Set<CommandType> movements, final double timeElapsed) {
        Point2D newMovementDirection = DirectionList.NONE.get();
        if (movements.contains(MOVE_NORTH)) {
            newMovementDirection = newMovementDirection.add(DirectionList.NORTH.get());
        }
        if (movements.contains(MOVE_SOUTH)) {
            newMovementDirection = newMovementDirection.add(DirectionList.SOUTH.get());
        }
        if (movements.contains(MOVE_EAST)) {
            newMovementDirection = newMovementDirection.add(DirectionList.EAST.get());
        }
        if (movements.contains(MOVE_WEST)) {
            newMovementDirection = newMovementDirection.add(DirectionList.WEST.get());
        }

        if (!newMovementDirection.equals(DirectionList.NONE.get())) {
            final double magnitude = newMovementDirection.magnitude();
            if (magnitude > 1) {
                /*
                 * If we don't normalize the vector, then the actor speed would amount to
                 * ||(1, 1)|| * actor.getSpeed() if it were to e.g. attempt to move south-east.
                 */
                newMovementDirection = new Point2D(newMovementDirection.getX() / magnitude,
                                                   newMovementDirection.getY() / magnitude);
            }
            this.actor.move(newMovementDirection.multiply(timeElapsed));
        }
    }

    /**
     * "Shortcut" method for handling rotation only.
     * 
     * @param rotation
     */
    protected void handleRotation(final Point2D rotation) {
        final double oldAngle = this.actor.getAngle();
        final double newAngle = Math.atan2(rotation.getY(), rotation.getX());
        if (oldAngle != newAngle) {
            this.actor.setAngle(newAngle);
        }
    }

    /**
     * Returns the actor instance.
     * @return
     */
    protected A getActor() {
        return this.actor;
    }
}
