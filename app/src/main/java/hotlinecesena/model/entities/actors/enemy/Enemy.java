package hotlinecesena.model.entities.actors.enemy;

import java.util.Set;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.actors.enemy.ai.AI;
import javafx.geometry.Point2D;

/**
 * Models the enemy actor by adding specific functionalities to them.
 * @see Actor
 */
public interface Enemy extends Actor {

    /**
     * Returns the logic implementation chosen based on
     * {@code EnemyType} that shapes enemy movements and
     * basic functionalities such as pursuit, shooting and
     * rotating the enemy actor to better fit the situation.
     * @return the AI used from the enemy
     * @see EnemyType
     */
    AI getAI();

    /**
     * Sets the current state for the enemy. If it true
     * the enemy will follow the player otherwise will
     * continue with their normal routines.
     * @param pursuit the state in which the enemy is in
     */
    void setIsInPursuit(boolean pursuit);

    /**
     * Gets all of the walkable points that the enemy
     * can traverse.
     * @return the collections of all the points that are
     * walkable by the enemy actor
     */
    Set<Point2D> getWalkable();

    /**
     * Returns if the enemy actor is in pursuit of
     * the player or not.
     * @return the state in which the enemy is in
     */
    boolean isChasingTarget();

}
