package hotlinecesena.model.entities.actors.enemy.ai.strategy;

import java.util.Set;

import javafx.geometry.Point2D;

public interface MovementStrategy {
    
    Point2D move(Point2D enemy, Point2D player, boolean pursuit, Set<Point2D> map);
}
