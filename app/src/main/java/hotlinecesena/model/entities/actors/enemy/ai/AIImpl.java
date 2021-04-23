package hotlinecesena.model.entities.actors.enemy.ai;

import java.util.Set;

import hotlinecesena.controller.physics.EnemyPhysics;
import hotlinecesena.model.entities.actors.DirectionList;
import hotlinecesena.model.entities.actors.enemy.ai.strategy.MovementStrategy;
import hotlinecesena.model.entities.actors.enemy.ai.strategy.Idle;
import hotlinecesena.model.entities.actors.enemy.ai.strategy.RandomMovement;
import hotlinecesena.model.entities.actors.enemy.ai.strategy.Patrolling;
import hotlinecesena.model.entities.actors.enemy.EnemyType;
import javafx.geometry.Point2D;

/**
 * Class that represent the generic AI implementation.
 */
public final class AIImpl implements AI {

    private static final int FIELD_OF_VIEW = 90;
    private static final int VISION_RADIUS = 5;
    private static final int HALF = 2;
    private static final double LOOK_NORTH = -90;
    private static final double LOOK_SOUTH = 90;
    private static final double LOOK_EAST = 0;
    private static final double LOOK_EAST_NORTH = -45;
    private static final double LOOK_EAST_SOUTH = 45;
    private static final double LOOK_WEST = 180;
    private static final double LOOK_WEST_NORTH = -135;
    private static final double LOOK_WEST_SOUTH = 135;

    private final MovementStrategy strategy;
    private final Set<Point2D> wallSet;
    private Point2D current;
    private Point2D nextMove;
    private double rotation;

    /**
     * Class constructor.
     * @param pos the starting position
     * @param type the enemy movement ability
     * @param rotation the starting rotation
     * @param walls the collections of all the wall objects position
     * @see EnemyType
     */
    public AIImpl(final Point2D pos, final EnemyType type,
            final double rotation, final Set<Point2D> walls) {

        this.current = pos;
        this.strategy = this.getStrategy(type);
        this.rotation = rotation;
        this.wallSet = walls;
    }

    /**
     * Returns the type of movement that the enemy
     * will be able to perform to move around the
     * game world.
     * @param type the enemy movement ability
     * @return the movement implementation
     * @see EnemyType
     */
    private MovementStrategy getStrategy(final EnemyType type) {
        switch (type) {
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

    /**
     * Returns a new angle based on the enemy {@code nextMove}
     * direction.
     * @return the angle rotation
     * @see DirectionList
     */
    private double rotationToDirection() {
        if (this.nextMove.equals(DirectionList.NORTH.get())) {
            return LOOK_NORTH;
        } else if (this.nextMove.equals(DirectionList.EAST.get())) {
            return LOOK_EAST;
        } else if (this.nextMove.equals(DirectionList.SOUTH.get())) {
            return LOOK_SOUTH;
        } else if (this.nextMove.equals(DirectionList.WEST.get())) {
            return LOOK_WEST;
        } else if (this.nextMove.equals(new Point2D(-1, 1))) {
            return LOOK_WEST_SOUTH;
        } else if (this.nextMove.equals(new Point2D(1, 1))) {
            return LOOK_EAST_SOUTH;
        } else if (this.nextMove.equals(new Point2D(1, -1))) {
            return LOOK_EAST_NORTH;
        } else if (this.nextMove.equals(new Point2D(-1, -1))) {
            return LOOK_WEST_NORTH;
        } else {
            return this.rotation;
        }
    }

    /**
     * Calculates and returns the angle from the player
     * position to the {@code target} position.
     * @param target the position that wants to be tracked
     * @return the angle rotation to said {@code target}
     */
    private double rotationToTarget(final Point2D target) {
        double distanceX, distanceY;

        distanceX = Math.abs(target.getX() - this.current.getX());
        distanceY = Math.abs(target.getY() - this.current.getY());

        return Math.toDegrees(Math.atan2(this.current.getY() > target.getY() ? -distanceY : distanceY,
                this.current.getX() > target.getX() ? -distanceX : distanceX));
    }

    /**
     * Calculates if the {@code target} is inside the area
     * of the enemy based on the specified {@code radius}.
     * @param target the position that wants to be tracked
     * @param radius the circle radius
     * @return if the position of {@code target} is inside the
     * calculated circle
     */
    private boolean isInArea(final Point2D target, final int radius) {
        return (target.getX() - this.current.getX()) * (target.getX() - this.current.getX())
                + (target.getY() - this.current.getY()) * (target.getY() - this.current.getY()) <= radius * radius;
    }

    /**
     * Calculates if the {@code target} is in the enemy
     * field of view and if it's visible from the enemy
     * current position.
     * @param target the position that wants to be tracked
     * @return if the {@code target} is in the line of sight
     * of the enemy
     */
    private boolean inLineOfSight(final Point2D target) {
        final double negative45DegreesAngle = this.rotation - (FIELD_OF_VIEW / HALF);
        final double positive45DegreesAngle = this.rotation + (FIELD_OF_VIEW / HALF);

        return this.rotationToTarget(target) >= negative45DegreesAngle
                && this.rotationToTarget(target) <= positive45DegreesAngle
                && !EnemyPhysics.isWallInBetween(target, this.current, this.wallSet);
    }

    @Override
    public void setEnemyPos(final Point2D pos) {
        this.current = pos;
    }

    @Override
    public Point2D getNextMove(final Point2D player, final boolean pursuit,
            final Set<Point2D> map) {

        this.nextMove = this.strategy.move(this.current, player, pursuit, map);
        return this.nextMove;
    }

    @Override
    public double getRotation(final Point2D target, final boolean pursuit) {
        this.rotation = !pursuit ? this.rotationToDirection() : this.rotationToTarget(target);
        return this.rotation;
    }

    @Override
    public boolean isInPursuit(final Point2D target, final double noise) {
        return this.isInArea(target, (int) noise) || this.isShooting(target);
    }

    @Override
    public boolean isShooting(final Point2D target) {
        return this.isInArea(target, VISION_RADIUS) && this.inLineOfSight(target);
    }

    @Override
    public Set<Point2D> getWallSet() {
        return this.wallSet;
    }
}
