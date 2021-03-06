package hotlinecesena.utilities;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import javafx.geometry.Point2D;

/**
 * Collection of static methods to check enemy physics.
 * @see Enemy
 */
public final class EnemyPhysicsUtils {

    /**
     * Class constructor.
     */
    private EnemyPhysicsUtils() {

    }

    /**
     * Calculates if the position that wants to be reached
     * is walkable by the enemy, and checks if there are no
     * other enemies, player or objects that could prevent
     * the movement.
     * @param current the current position
     * @param next the position that wants to be reached
     * @param map the collections of points that are walkable
     * by the enemy
     * @return if the {@code next} position is walkable
     */
    public static boolean isMoveAllowed(final Point2D current, final Point2D next, 
            final Set<Point2D> map) {

        final Set<Point2D> allowedMoveSet = new HashSet<>() {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

        {
           addAll(map); 
        }};

        allowedMoveSet.removeAll(JSONDataAccessLayer.getInstance()
                .getEnemy()
                .getEnemies()
                .stream()
                    .map(e -> e.getPosition())
                    .collect(Collectors.toSet()));
        allowedMoveSet.remove(JSONDataAccessLayer.getInstance()
                .getPlayer()
                .getPly()
                    .getPosition());
        allowedMoveSet.add(current);

        return allowedMoveSet.contains(MathUtils.roundedSumPoint2D(current, next));
    }

    /**
     * Calculates if the {@code current} position has a clear
     * sight to the {@code target} position by traversing the
     * shortest path to each points and checking if any walls
     * are in the way.
     * @param current the current position
     * @param target the targeted position that wants to be checked
     * @param walls the collections of all points that could prevent
     * the two points to see each others
     * @return if the {@code target} is visible from the {@code current}
     * position
     */
    public static boolean isWallInBetween(final Point2D current, final Point2D target,
            final Set<Point2D> walls) {

        int x = (int) current.getX();
        int y = (int) current.getY();

        int distanceX = x - (int) target.getX();
        int distanceY = y - (int) target.getY();

        if (distanceX == 0 && distanceY == 0) {
            return false;
        }

        while ((distanceX > 0 ? x > target.getX() : x < target.getX())
                || (distanceY > 0 ? y > target.getY() : y < target.getY())) {

            if (walls.contains(new Point2D(x, y))) {
                return true;
            }

            if (distanceX != 0) {
                x = distanceX > 0 ? x - 1 : x + 1;
            }
            if (distanceY != 0) {
                y = distanceY > 0 ? y - 1 : y + 1;
            }
        }
        return false;
    }
}
