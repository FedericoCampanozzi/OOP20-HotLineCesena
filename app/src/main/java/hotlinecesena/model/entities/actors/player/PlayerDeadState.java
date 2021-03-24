package hotlinecesena.model.entities.actors.player;

import java.util.Set;

import hotlinecesena.model.entities.actors.state.AbstractState;
import javafx.geometry.Point2D;

/**
 * 
 * Handles logic when the player's state is set to DEAD.
 * <br>
 * It must ignore all commands coming from the controller.
 *
 */
public class PlayerDeadState extends AbstractState<Player>{

    public PlayerDeadState(Player player) {
        super(player);
    }

    @Override
    protected void handleAllowedCommands(Set<CommandType> receivedCommands, Point2D rotation, double timeElapsed) {
    }
}
