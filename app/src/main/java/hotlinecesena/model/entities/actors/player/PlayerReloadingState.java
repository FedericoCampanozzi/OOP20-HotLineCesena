package hotlinecesena.model.entities.actors.player;

import java.util.Set;

import hotlinecesena.model.entities.actors.ActorStatus;
import javafx.geometry.Point2D;

/**
 * 
 * Handles logics when the player is reloading their weapon.
 * <br>
 * While in this state, the player is most vulnerable and cannot attack.
 *
 */
public class PlayerReloadingState extends AbstractState {

    private static final double RELOADING_TIME = 2.0; //TODO Temporary, to be replaced by weapon reload time
    private double reloadTimeRemaining;

    public PlayerReloadingState(Player player) {
        super(player);
        this.resetTimer();
    }

    @Override
    protected void handleAllowedActions(Set<PlayerAction> receivedCommands, Point2D rotation, double timeElapsed) {
        this.handleMovement(receivedCommands, timeElapsed);
        this.handleRotation(rotation);

        if (this.reloadTimeRemaining > 0.0) {
            this.reloadTimeRemaining -= timeElapsed;
        } else {
            this.getPlayer().reload();
            this.getPlayer().setActorStatus(ActorStatus.NORMAL);
            this.resetTimer();
        }
    }

    private void resetTimer() {
        this.reloadTimeRemaining = RELOADING_TIME;
    }
}
