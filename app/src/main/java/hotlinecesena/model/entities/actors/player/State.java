package hotlinecesena.model.entities.actors.player;

import java.util.Set;

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
     * Changes of state must be handled in here manually through player.setState()
     * 
     * @param actions
     * @param timeElapsed
     */
    void handle(Pair<Set<PlayerAction>, Point2D> actions, double timeElapsed);
}
