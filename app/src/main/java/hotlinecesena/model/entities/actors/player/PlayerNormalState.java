package hotlinecesena.model.entities.actors.player;

import static hotlinecesena.model.entities.actors.player.CommandType.*;

import java.util.Set;

import hotlinecesena.model.entities.actors.ActorStateList;
import hotlinecesena.model.entities.actors.state.AbstractState;
import javafx.geometry.Point2D;

public class PlayerNormalState extends AbstractState<Player> {

    public PlayerNormalState(final Player player) {
        super(player);
    }

    @Override
    protected void handleAllowedCommands(Set<CommandType> receivedCommands, Point2D rotation, double timeElapsed) {
        this.handleMovement(receivedCommands, timeElapsed);
        this.handleRotation(rotation);

        if (receivedCommands.contains(RELOAD)) {
            // During the next update, ReloadingState will begin the reloading action and start a "timer".
            this.getActor().setState(ActorStateList.RELOADING);
        // Handling all remaining commands which do not trigger a state change.
        } else {
            if (receivedCommands.contains(ATTACK)) {
                this.getActor().attack();
            }
            if (receivedCommands.contains(USE)) {
                this.getActor().use();
            }
            if (receivedCommands.contains(PICK_UP)) {
                this.getActor().pickUp();
            }
        }
    }
}
