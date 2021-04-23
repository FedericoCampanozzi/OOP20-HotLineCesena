package hotlinecesena.utilities;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;

/**
 * Collection of static methods describing algorithms or mathematical functions.
 *
 */
public final class MathUtils {

    private MathUtils() {
    }

    /**
     * Converts raw mouse coordinates to degrees.
     *
     * @param coords mouse coordinates
     * @return value in degrees
     */
    public static double mouseToDegrees(final Point2D coords) {
        return Math.toDegrees(Math.atan2(coords.getY(), coords.getX()));
    }

    /**
     *
     * Linear interpolation formula used for camera movement.
     * May give inaccurate results due to floating point approximations.
     * <br>
     * Original formula: {@code first*(1 - by) + second*by}
     * <br>
     * Taken from gamedev.stackexchange.com/a/152466
     *
     * @param first first one-dimensional coordinate
     * @param second second one-dimensional coordinate
     * @param by interpolation factor
     * @return interpolated value
     */
    public static double lerp(final double first, final double second, final double by) {
        return first + by * (second - first);
    }

    /**
     * Linear interpolation formula used for camera movement.
     * May give inaccurate results due to floating point approximations.
     * <br>
     * Taken from gamedev.stackexchange.com/a/152466
     *
     * @param first first two-dimensional coordinate
     * @param second second two-dimensional coordinate
     * @param by interpolation factor
     * @return interpolated value
     */
    public static Point2D lerp(final Point2D first, final Point2D second, final double by) {
        final double retX = lerp(first.getX(), second.getX(), by);
        final double retY = lerp(first.getY(), second.getY(), by);
        return new Point2D(retX, retY);
    }

    /**
     * Blend formula for camera interpolation.
     * Taken from gamedev.stackexchange.com/a/152466
     *
     * @param sharpness
     * @param acceleration
     * @param deltaTime
     * @return blend value
     */
    public static double blend(final double sharpness, final double acceleration, final double deltaTime) {
        return 1 - Math.pow(1 - sharpness, deltaTime * acceleration);
    }

    /**
     * Checks whether two objects, both characterized by a position, a width and a height,
     * are colliding with each other.
     * <br>
     * Adapted from the method {@code intersects} of JavaFX's {@link BoundingBox}.
     * @param p1 coordinates of the first object
     * @param w1 width of the first object
     * @param h1 height of the first object
     * @param p2 coordinates of the second object
     * @param w2 width of the second object
     * @param h2 height of the second object
     * @return {@code true} if the two objects are colliding, {@code false} otherwise.
     */
    public static boolean isCollision(final Point2D p1, final double w1, final double h1,
            final Point2D p2, final double w2, final double h2) {
        return p2.getX() + w2 >= p1.getX()
                && p2.getY() + h2 >= p1.getY()
                && p2.getX() <= p1.getX() + w1
                && p2.getY() <= p1.getY() + h1;
    }
}
