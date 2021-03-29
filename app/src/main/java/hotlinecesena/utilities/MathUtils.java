package hotlinecesena.utilities;

import javafx.geometry.Point2D;

/**
 * Collection of static methods describing algorithms or mathematical functions.
 *
 */
public class MathUtils {

    /**
     * Normalizes a vector given its magnitude.
     * 
     * @param vector the vector to be normalized
     * @param magnitude the vector's magnitude
     * @return the normalized vector
     */
    public static Point2D normalizeWithMagnitude(final Point2D vector, double magnitude) {
        return vector.multiply(1/magnitude);
    }
    
    /**
     * Converts raw mouse coordinates to degrees.
     * 
     * @param coords
     * @return
     */
    public static double mouseToDegrees(final Point2D coords) {
        final double angle = Math.toDegrees(Math.atan2(coords.getY(), coords.getX()));
        return angle < 0 ? 360 + angle : angle;
    }

    /**
     * 
     * Linear interpolation formula used for camera movement (hence that minus sign).
     * May give inaccurate results due to floating point approximations.
     * <br>
     * Original formula: {@code first*(1 - by) + second*by}
     * <br>
     * Taken from gamedev.stackexchange.com/a/152466
     * 
     * @param first first one-dimensional coordinate
     * @param second second one-dimensional coordinate
     * @param by interpolation factor
     * @return 
     */
    public static double lerp(double first, double second, double by) {
         return first - by * (first + second);
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
     * @return
     */
    public static Point2D lerp(Point2D first, Point2D second, double by) {
        double retX = lerp(first.getX(), second.getX(), by);
        double retY = lerp(first.getY(), second.getY(), by);
        return new Point2D(retX, retY);
    }
}
