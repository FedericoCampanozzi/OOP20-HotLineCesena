package hotlinecesena.model.entities.actors.state;

import java.util.Set;

import hotlinecesena.model.entities.actors.player.CommandType;
import javafx.geometry.Point2D;
import javafx.util.Pair;

/**
 * 
 * Based on the State pattern.
 * 
 *
 */
public interface State {

    /**
     * Changes of state must be handled here for the Actor through actor.setState()
     * 
     * @param commands
     * @param timeElapsed
     */
    void handle(Pair<Set<CommandType>, Point2D> commands, double timeElapsed);
}
