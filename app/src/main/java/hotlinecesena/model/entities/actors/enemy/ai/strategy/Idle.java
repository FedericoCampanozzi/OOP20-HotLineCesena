package hotlinecesena.model.entities.actors.enemy.ai.strategy;

import java.util.Set;

import hotlinecesena.model.entities.actors.DirectionList;
import javafx.geometry.Point2D;

public class Idle implements MovementStrategy{
    
    @Override
    public Point2D move(Point2D enemy, Point2D player, boolean pursuit, Set<Point2D> map) {
        return DirectionList.NONE.get();
    }

}
