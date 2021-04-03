package hotlinecesena.model.entities.actors.enemy.ai.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import hotlinecesena.controller.physics.EnemyPhysics;
import hotlinecesena.model.entities.actors.DirectionList;
import javafx.geometry.Point2D;

public class Patrolling implements MovementStrategy{
    
    private final Stack<DirectionList> movementStack = new Stack<>();
    private List<Point2D> pathfindingList = new ArrayList<>();
    private DirectionList nextMove;

    public Patrolling() {
        this.fillStack();
        this.nextMove = this.newMove();
    }
    
    private void fillStack() {
        this.movementStack.push(DirectionList.WEST);
        this.movementStack.push(DirectionList.SOUTH);
        this.movementStack.push(DirectionList.EAST);
        this.movementStack.push(DirectionList.NORTH);
    }
    
    private DirectionList newMove() {
        return this.movementStack.pop();
    }
    
    private DirectionList normalMovement(Point2D pos, Set<Point2D> map) {
        this.pathfindingList = new ArrayList<>();

        if(this.movementStack.empty()) {
            this.fillStack();
        }
        
        if(!EnemyPhysics.isMoveAllowed(pos, this.nextMove.get(), map)) {
            this.nextMove = this.newMove();
        }
        
        return this.nextMove;
    }
    
    private Point2D pursuitMovement(Point2D start, Point2D end, Set<Point2D> map) {
        if(this.pathfindingList.isEmpty() ||
                !this.pathfindingList.get(this.pathfindingList.size()-1).equals(end)) {
            this.pathfindingList = Pathfinder.findPath(start, end, map);
        }
        
        if(start.equals(this.pathfindingList.get(0))) {
            this.pathfindingList.remove(0);
        }
        
        Point2D retval = new Point2D(this.pathfindingList.get(0).getX() - start.getX(),
                this.pathfindingList.get(0).getY() - start.getY());
        
        return EnemyPhysics.isMoveAllowed(start, retval, map) ? retval : DirectionList.NONE.get();
    }
    
    @Override
    public Point2D move(Point2D enemy, Point2D player, boolean pursuit, Set<Point2D> map) {
        return !pursuit ? this.normalMovement(enemy, map).get() : this.pursuitMovement(enemy, player, map);
    }
}
