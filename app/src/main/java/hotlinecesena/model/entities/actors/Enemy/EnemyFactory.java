package hotlinecesena.model.entities.actors.enemy;

import javafx.geometry.Point2D;

public interface EnemyFactory {

    public Enemy getEnemy (Point2D pos, EnemyType type);
}
