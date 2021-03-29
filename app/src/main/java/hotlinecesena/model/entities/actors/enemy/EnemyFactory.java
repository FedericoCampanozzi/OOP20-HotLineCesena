package hotlinecesena.model.entities.actors.enemy;

import java.util.Set;

import javafx.geometry.Point2D;

public interface EnemyFactory {

    public Enemy getEnemy (Point2D pos, EnemyType type, Set<Point2D> walkable, Set<Point2D> walls);
}
