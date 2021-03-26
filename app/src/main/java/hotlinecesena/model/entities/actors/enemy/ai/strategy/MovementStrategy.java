package hotlinecesena.model.entities.actors.enemy.ai.strategy;

import hotlinecesena.model.entities.actors.DirectionList;
import javafx.geometry.Point2D;

public interface MovementStrategy {
    
    DirectionList move(Point2D pos, boolean pursuit);
}
