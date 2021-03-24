package hotlinecesena.model.entities.actors.player;

import hotlinecesena.model.entities.actors.Actor;
import javafx.geometry.Point2D;

/**
 * 
 * Functional interface to be used for the Command pattern.
 *
 * @param <A>
 */
@FunctionalInterface
public interface Command<A extends Actor> {

    /**
     * 
     * @param actor
     * @param deltaTime
     */
    void execute(A actor, Point2D dir, double ang);
}
