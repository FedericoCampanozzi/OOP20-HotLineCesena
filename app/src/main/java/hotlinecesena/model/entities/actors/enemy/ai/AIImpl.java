package hotlinecesena.model.entities.actors.enemy.ai;

import hotlinecesena.model.entities.actors.DirectionList;
import hotlinecesena.model.entities.actors.enemy.ai.strategy.*;
import hotlinecesena.model.entities.actors.enemy.EnemyType;
import javafx.geometry.Point2D;

public class AIImpl implements AI{

    private MovementStrategy nextMove;

    public AIImpl(EnemyType type) {
        this.nextMove = this.getStrategy(type);
    }

    private MovementStrategy getStrategy(EnemyType type) {
        MovementStrategy retval;

        switch(type) {
            case IDLE:
                retval = new Idle();
                break;
            case RANDOM_MOVEMENT:
                retval = new RandomMovement();
                break;
            case PATROLLING:
                retval = new Patrolling();
                break;
            default:
                retval = null;
                break;
            
        }

        return retval;
    }

    @Override
    public DirectionList getNextMove(Point2D pos, boolean pursuit) {
        return this.nextMove.move(pos, pursuit);
    }

}
