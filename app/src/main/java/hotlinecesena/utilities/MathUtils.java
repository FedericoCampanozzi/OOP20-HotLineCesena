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
    public static double blend(final double sharpness, final int acceleration, final double deltaTime) {
        return 1 - Math.pow(1 - sharpness, deltaTime * acceleration);
    }
}
