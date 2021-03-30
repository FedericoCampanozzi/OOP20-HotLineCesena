package hotlinecesena.model.entities.actors.enemy.ai.strategy;

import java.util.Random;
import java.util.Set;

import hotlinecesena.controller.physics.EnemyPhysics;
import hotlinecesena.model.entities.actors.DirectionList;
import javafx.geometry.Point2D;

public class RandomMovement implements MovementStrategy{
    
    private DirectionList nextMove;
    
    @Override
    public Point2D move(Point2D enemy, Point2D player, boolean pursuit, Set<Point2D> map) {
        int pick = new Random().nextInt(DirectionList.values().length-1);
        this.nextMove = DirectionList.values()[pick];
        
        return EnemyPhysics.isMoveAllowed(enemy, this.nextMove.get(), map) ? this.nextMove.get() : DirectionList.NONE.get();
    }
}
