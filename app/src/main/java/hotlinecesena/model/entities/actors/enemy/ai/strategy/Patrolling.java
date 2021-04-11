package hotlinecesena.model.entities.actors.enemy.ai.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;

import hotlinecesena.controller.physics.EnemyPhysics;
import hotlinecesena.model.entities.actors.DirectionList;
import javafx.geometry.Point2D;

/**
 * This instance of {@link MovementStrategy} is the most sophisticated
 * of all the implementation. The {@code Patrolling} type of enemies
 * are able to change between two states, based on {@code pursuit}
 * they can patrol or path find their way to the indicated target
 */
public class Patrolling implements MovementStrategy {

    private final Stack<DirectionList> movementStack = new Stack<>();
    private List<Point2D> pathfindingList = new ArrayList<>();
    private DirectionList nextMove;

    /**
     * Class constructor.
     */
    public Patrolling() {
        this.fillStack();
        this.nextMove = this.newMove();
    }

    /**
     * Fills {@code movementStack} once it's empty
     * by pushing directions in a clockwise fashion.
     * @see DirectionList
     */
    private void fillStack() {
        this.movementStack.push(DirectionList.WEST);
        this.movementStack.push(DirectionList.SOUTH);
        this.movementStack.push(DirectionList.EAST);
        this.movementStack.push(DirectionList.NORTH);
    }

    /**
     * Return a new move if the {@code nextMove} is not allowed 
     * and returns the top object from {@code movementStack}.
     * @return the next {@code direction}
     */
    private DirectionList newMove() {
        return this.movementStack.pop();
    }

    /**
     * Return a new move if it's allowed. This method is called
     * if the enemy is not in pursuit of the player and
     * performs a clockwise motion from this the name {@code Patroling}
     * enemy
     * @param pos the current position of the enemy
     * @param map collections of all allowed position for the enemy
     * @return the next direction to go to
     */
    private DirectionList normalMovement(final Point2D pos, final Set<Point2D> map) {
        this.pathfindingList = new ArrayList<>();

        if (this.movementStack.empty()) {
            this.fillStack();
        }

        if (!EnemyPhysics.isMoveAllowed(pos, this.nextMove.get(), map)) {
            this.nextMove = this.newMove();
        }

        return EnemyPhysics.isMoveAllowed(pos, this.nextMove.get(), map) ? this.nextMove : DirectionList.NONE;
    }

    /**
     * Return a new move based on the top object in {@code pathfindingList}
     * once that position is reached that object gets removed and a new point is presented
     * This method is called if the enemy is in pursuit of the player.
     * If {@code pathfindingList} is empty or if the position of the player is changed a
     * new path is calculated
     * @param start the current position of the enemy
     * @param end the current position of the player
     * @param map collections of all allowed position for the enemy
     * @return then next direction to go to
     */
    private Point2D pursuitMovement(final Point2D start, final Point2D end,
            final Set<Point2D> map) {

        Optional<Point2D> retval = Optional.empty();

        if (this.pathfindingList.isEmpty()
                || !this.pathfindingList.get(this.pathfindingList.size() - 1).equals(end)) {
            this.pathfindingList = Pathfinder.findPath(start, end, map);
        }

        if (!this.pathfindingList.isEmpty() && start.equals(this.pathfindingList.get(0))) {
            this.pathfindingList.remove(0);
        }

        if (!this.pathfindingList.isEmpty()) {
            retval = Optional.of(new Point2D(this.pathfindingList.get(0).getX() - start.getX(),
                    this.pathfindingList.get(0).getY() - start.getY()));
        }

        return retval.isPresent() && EnemyPhysics.isMoveAllowed(start, retval.get(), map) ? retval.get() : DirectionList.NONE.get();
    }

    /**
     * Based on {@code pursuit}, the method {@code move} perform a patrol or pursuit following
     * the player.
     */
    @Override
    public Point2D move(final Point2D enemy, final Point2D player,
            final boolean pursuit, final Set<Point2D> map) {

        return !pursuit ? this.normalMovement(enemy, map).get() : this.pursuitMovement(enemy, player, map);
    }
}
