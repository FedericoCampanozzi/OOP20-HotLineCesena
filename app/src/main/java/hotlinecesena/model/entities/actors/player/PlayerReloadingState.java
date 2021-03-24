package hotlinecesena.model.entities.actors.player;

import java.util.Set;

import hotlinecesena.model.entities.actors.ActorStateList;
import hotlinecesena.model.entities.actors.state.AbstractState;
import javafx.geometry.Point2D;

public class PlayerReloadingState extends AbstractState<Player> {

    private static final double RELOADING_TIME = 2.0; //TODO Temporary
    private double reloadTimeRemaining;

    public PlayerReloadingState(Player player) {
        super(player);
        this.reloadTimeRemaining = RELOADING_TIME;
    }

    @Override
    protected void handleAllowedCommands(Set<CommandType> receivedCommands, Point2D rotation, double timeElapsed) {
        this.handleMovement(receivedCommands, timeElapsed);
        this.handleRotation(rotation);

        if (this.reloadTimeRemaining > 0.0) {
            this.reloadTimeRemaining -= timeElapsed;
        } else {
            this.getActor().reload();
            this.getActor().setState(ActorStateList.NORMAL);
            this.reloadTimeRemaining = RELOADING_TIME;
        }
    }
}
