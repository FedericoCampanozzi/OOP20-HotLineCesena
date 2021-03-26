package hotlinecesena.model.entities.actors.player;

import static hotlinecesena.model.entities.actors.player.PlayerAction.*;

import java.util.Set;

import hotlinecesena.model.entities.actors.ActorStatus;
import javafx.geometry.Point2D;

/**
 * 
 * Handles logics when the player's status is set to NORMAL.
 * <br>
 * At present, there are no restrictions on which actions the player may perform
 * while in this state.
 *
 */
public class PlayerNormalState extends AbstractState {

    public PlayerNormalState(final Player player) {
        super(player);
    }

    @Override
    protected void handleAllowedActions(Set<PlayerAction> receivedActions, Point2D rotation, double timeElapsed) {
        this.handleMovement(receivedActions, timeElapsed);
        this.handleRotation(rotation);

        if (receivedActions.contains(RELOAD)) {
            // During the next update, ReloadingState will begin the reloading action and start a "timer".
            this.getPlayer().setActorStatus(ActorStatus.RELOADING);
        // Handling all remaining commands which do not trigger a state change.
        } else {
            if (receivedActions.contains(ATTACK)) {
                this.getPlayer().attack();
            }
        }
    }
}
