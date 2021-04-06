package hotlinecesena.model.entities.actors.enemy;

import java.util.Set;

import hotlinecesena.model.inventory.NaiveInventoryImpl;
import javafx.geometry.Point2D;

/**
 * Class that represent the generic factory implementation
 */
public class EnemyFactoryImpl implements EnemyFactory {

    @Override
    public Enemy getEnemy(final Point2D pos, final EnemyType type,
            final Set<Point2D> walkable, final Set<Point2D> walls) {

        switch(type) {
            case BOSS:
                return new EnemyImpl(pos, new NaiveInventoryImpl(), EnemyType.PATROLLING, walkable, walls);
            default:
                return new EnemyImpl(pos, new NaiveInventoryImpl(), type, walkable, walls);
        }
    }
}
