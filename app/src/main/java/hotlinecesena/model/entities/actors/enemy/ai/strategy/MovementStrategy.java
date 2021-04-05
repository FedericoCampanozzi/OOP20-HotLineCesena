package hotlinecesena.model.entities.actors.enemy.ai.strategy;

import java.util.Set;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.actors.enemy.EnemyType;
import javafx.geometry.Point2D;

/**
 * Interface used to implement different types of movement based on {@code EnemyType}
 * @see EnemyType
 */
public interface MovementStrategy {

    /**
     * Returns a Point2D object that can be reached and walked on by the enemy
     * by checking if the position is in the {@code Set} and if its free of
     * other {@link Actor}
     * @param enemy the enemy position
     * @param player the player position
     * @param pursuit the current enemy state
     * @param map all of the points that can be walked on by the enemy
     * @return a allowed direction as a Point2D to which the enemy will move to
     * @see Player
     * @see Enemy
     * @see Point2D
     */
    Point2D move(Point2D enemy, Point2D player, boolean pursuit, Set<Point2D> map);

}
