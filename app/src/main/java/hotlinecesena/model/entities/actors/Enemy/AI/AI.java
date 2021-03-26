package hotlinecesena.model.entities.actors.Enemy.AI;

import hotlinecesena.model.entities.actors.DirectionList;
import javafx.geometry.Point2D;

public interface AI {

    public DirectionList getNextMove(Point2D pos, boolean pursuit);
}
