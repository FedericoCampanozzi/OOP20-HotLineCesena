package hotlinecesena.model.entities.actors.enemy.ai;

import java.util.Set;

import javafx.geometry.Point2D;

/**
 * Models the actions that the enemy will perform during the game.
 * @see Enemy
 */
public interface AI {

    /**
     * Sets the new enemy position.
     * @param pos the new enemy position
     */
    void setEnemyPos(Point2D pos);

    /**
     * Returns the new position for the enemy to
     * move to.
     * @param target the position that wants to be tracked
     * @param pursuit the state in which the enemy is in
     * @param map
     * @return the position to move to
     */
    Point2D getNextMove(Point2D target, boolean pursuit, Set<Point2D> map);

    /**
     * Returns the new enemy rotation based on the
     * state in which the enemy is in.
     * @param target the position that wants to be tracked
     * @param pursuit the state in which the enemy is in
     * @return the angle that the enemy should face
     */
    double getRotation(Point2D target, boolean pursuit);

    /**
     * Returns if the enemy is in pursuit of the target
     * by calculating if the noise radius overlap with the
     * two.
     * @param target the position that wants to be tracked
     * @param noise the radius of the noise made by a player action
     * @return the state in which the enemy will be in
     * @see Player
     */
    boolean isInPursuit(Point2D target, double noise);

    /**
     * Returns if the enemy has the {@code target} in line of
     * sight and is close enough to get the enemy attention
     * to shoot.
     * @param target the position that wants to be tracked
     * @return the action that should be performed by the enemy
     */
    boolean isShooting(Point2D target);

    /**
     * Returns the collection of all position to check
     * if target is obscured by a wall used to JUnit
     * tests purpose.
     * @return the collections of all wall objects position
     */
    Set<Point2D> getWallSet();

}
