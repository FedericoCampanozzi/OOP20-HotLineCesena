package hotlinecesena.model.entities.actors.enemy;

import java.util.Set;

import hotlinecesena.model.inventory.Inventory;
import javafx.geometry.Point2D;

/**
 * Model a factory that generate enemies
 */
public interface EnemyFactory {

    /**
     * Returns a new specific enemy. Based on the {@code EnemyType}
     * the enemy will be able to move around the game map, chase down
     * the player and shoot the weapon in his {@code Inventory}
     * @param pos the starting position
     * @param type the type of movement that the enemy will inherit 
     * @param walkable collections of points that are traversable by
     * the enemy
     * @param walls collections of all wall objects that could reduce
     * enemy vision
     * @return a new enemy implementation
     * @see Enemy
     * @see EnemyImpl
     * @see EnemyType
     * @see Inventory
     */
    public Enemy getEnemy (Point2D pos, EnemyType type, Set<Point2D> walkable, Set<Point2D> walls);

}
