package hotlinecesena.model.entities.actors.Enemy.AI.Strategy;

import java.util.Stack;
import hotlinecesena.model.entities.actors.DirectionList;
import javafx.geometry.Point2D;

public class Patrolling implements MovementStrategy{
    
    private DirectionList nextMove;
    private Stack<DirectionList> movementStack;

    public Patrolling() {
        this.fillStack();
        this.newMove();
    }
    
    private void fillStack() {
        this.movementStack = new Stack<>() {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

        {
           push(DirectionList.WEST);
           push(DirectionList.SOUTH);
           push(DirectionList.EAST);
           push(DirectionList.NORTH);
        }};
    }
     
    private void newMove() {
        this.nextMove = this.movementStack.pop();
    }
    
    private DirectionList normalMovement() {
        if(this.movementStack.empty()) {
            this.fillStack();
        }
        
        /*if(isMovementAllowed(pos, nextpos)) {
            this.newMove();
        }*/
        
        return this.nextMove;
    }
    
    private DirectionList pursuitMovement() {
        return null;
    }
    
    @Override
    public DirectionList move(Point2D pos, boolean pursuit) {
        return !pursuit ? normalMovement() : pursuitMovement();
    }
}
