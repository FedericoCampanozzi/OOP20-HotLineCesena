package hotlinecesena.model.entities.actors.enemy.ai;

import java.util.Set;

import hotlinecesena.controller.physics.EnemyPhysics;
import hotlinecesena.model.entities.actors.DirectionList;
import hotlinecesena.model.entities.actors.enemy.ai.strategy.*;
import hotlinecesena.model.entities.actors.enemy.EnemyType;
import javafx.geometry.Point2D;

public class AIImpl implements AI{
    
    private final static int FIELD_OF_VIEW = 90;
    private final static int NOISE_TRESHOLD = 10;
    private final static int VISION_RADIUS = 3;
    private final static int HEARDABLE_RADIUS = 5;
    private final static double LOOK_NORTH = 90;
    private final static double LOOK_EAST_NORTH = 45;
    private final static double LOOK_EAST = 0;
    private final static double LOOK_EAST_SOUTH = -45;
    private final static double LOOK_SOUTH = -90;
    private final static double LOOK_WEST_SOUTH = -135;
    private final static double LOOK_WEST = 180;
    private final static double LOOK_WEST_NORTH = 135;

    private final MovementStrategy strategy;
    private final Set<Point2D> wallSet;
    private Point2D current;
    private Point2D nextMove;
    private double rotation;

    public AIImpl(Point2D pos, EnemyType type, double rotation, Set<Point2D> walls) {
        this.current = pos;
        this.strategy = this.getStrategy(type);
        this.rotation = rotation;
        this.wallSet = walls;
    }

    private MovementStrategy getStrategy(EnemyType type) {
        switch(type) {
            case IDLE:
                return new Idle();
            case RANDOM_MOVEMENT:
                return new RandomMovement();
            case PATROLLING:
                return new Patrolling();
            default:
                return null;
        }
    }
    

    private double rotationToDirection() {
        if(this.nextMove.equals(DirectionList.NORTH.get())) {
            return LOOK_NORTH;
        } else if(this.nextMove.equals(DirectionList.EAST.get())) {
            return LOOK_EAST;
        } else if(this.nextMove.equals(DirectionList.SOUTH.get())) {
            return LOOK_SOUTH;
        } else if(this.nextMove.equals(DirectionList.WEST.get())) {
            return LOOK_WEST;
        } else if(this.nextMove.equals(new Point2D(-1,1))) {
            return LOOK_WEST_SOUTH;
        } else if(this.nextMove.equals(new Point2D(1,1))) {
            return LOOK_EAST_SOUTH;
        } else if(this.nextMove.equals(new Point2D(1,-1))) {
            return LOOK_EAST_NORTH;
        } else if(this.nextMove.equals(new Point2D(-1,-1))) {
            return LOOK_WEST_NORTH;
        } else {
            return this.rotation;
        }
    }
    
    private double rotationToTarget(Point2D target) {
        double distanceX, distanceY;
        
        distanceX = Math.abs(target.getX() - this.current.getX());
        distanceY = Math.abs(target.getY() - this.current.getY());
        
        return Math.toDegrees(Math.atan2(this.current.getY() > target.getY() ? distanceY : -distanceY,
                this.current.getX() > target.getX() ? -distanceX : distanceX));
    }
    
    private boolean isInArea(Point2D target, int radius) {
        return (target.getX() - this.current.getX()) * (target.getX() - this.current.getX()) +
                (target.getY() - this.current.getY()) * (target.getY() - this.current.getY()) <= radius * radius;
    }
    
    private boolean inLineOfSight(Point2D target) {
        return this.rotationToTarget(target) >= this.rotation - (FIELD_OF_VIEW / 2)
                && this.rotationToTarget(target) <= this.rotation + (FIELD_OF_VIEW / 2)
                && !EnemyPhysics.isWallInBetween(target, this.current, this.wallSet);
    }

    @Override
    public void setEnemyPos(Point2D pos) {
        this.current = pos;
    }

    @Override
    public Point2D getNextMove(Point2D player, boolean pursuit, Set<Point2D> map) {
        this.nextMove = this.strategy.move(this.current, player, pursuit, map);
        return this.nextMove;
    }

    @Override
    public double getRotation(Point2D target, boolean pursuit) {
        this.rotation = !pursuit ? this.rotationToDirection() : this.rotationToTarget(target);
        return this.rotation;
    }

    @Override
    public boolean isInPursuit(Point2D target, double noise) {
        return (noise / EnemyPhysics.distance(this.current, target) >= NOISE_TRESHOLD
                    && this.isInArea(target, HEARDABLE_RADIUS))
                || this.isShooting(target);
    }

    @Override
    public boolean isShooting(Point2D target) {
        return this.isInArea(target, VISION_RADIUS) && this.inLineOfSight(target);
    }
    
    public Set<Point2D> getWallSet() {
        return this.wallSet;
    }
}
