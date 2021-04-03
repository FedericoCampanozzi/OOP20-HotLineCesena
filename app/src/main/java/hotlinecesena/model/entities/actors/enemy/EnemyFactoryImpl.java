package hotlinecesena.model.entities.actors.enemy;

import java.util.Set;

import hotlinecesena.model.inventory.NaiveInventoryImpl;
import javafx.geometry.Point2D;

public class EnemyFactoryImpl implements EnemyFactory {

    @Override
    public Enemy getEnemy(Point2D pos, EnemyType type, Set<Point2D> walkable, Set<Point2D> walls) {        
        switch(type) {
            case BOSS:
                return null;
            default:
                return new EnemyImpl(pos, new NaiveInventoryImpl(), type, walkable, walls);
        }
    }

}
