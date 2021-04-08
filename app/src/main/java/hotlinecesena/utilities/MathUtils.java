package hotlinecesena.utilities;

import javafx.geometry.Point2D;

/**
 * Collection of static methods describing algorithms or mathematical functions.
 *
 */
public final class MathUtils {

    private MathUtils() {
    }

    /**
     * Normalizes a vector given its magnitude.
     *
     * @param vector the vector to be normalized
     * @param magnitude the vector's magnitude
     * @return the normalized vector
     */
    public static Point2D normalizeWithMagnitude(final Point2D vector, final double magnitude) {
        return vector.multiply(1 / magnitude);
    }

    /**
     * Converts raw mouse coordinates to degrees.
     *
     * @param coords mouse coordinates
     * @return value in degrees
     */
    public static double mouseToDegrees(final Point2D coords) {
        final double angle = Math.toDegrees(Math.atan2(coords.getY(), coords.getX()));
        return angle < 0 ? 360 + angle : angle;
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
        // Compute center coordinates for each object
        final Point2D center1 = p1.subtract(w1 * 0.5, h1 * 0.5);
        final Point2D center2 = p2.subtract(w2 * 0.5, h2 * 0.5);
        return center2.getX() + w2 >= center1.getX()
                && center2.getY() + h2 >= center1.getY()
                && center2.getX() <= center1.getX() + w1
                && center2.getY() <= center1.getY() + h1;
    }
}
