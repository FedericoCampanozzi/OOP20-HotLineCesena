package hotlinecesena.model.entities.actors.Enemy.AI.Strategy;

import java.util.Random;

import hotlinecesena.model.entities.actors.DirectionList;
import javafx.geometry.Point2D;

public class RandomMovement implements MovementStrategy{
    
    @Override
    public DirectionList move(Point2D pos, boolean pursuit) {
        int pick = new Random().nextInt(DirectionList.values().length);
        return DirectionList.values()[pick];
    }

}
