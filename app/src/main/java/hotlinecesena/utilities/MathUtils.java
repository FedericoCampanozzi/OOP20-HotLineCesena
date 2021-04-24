package hotlinecesena.utilities;

import java.util.Random;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.util.Pair;

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

    /**
     * Computes the sum between two {@link Point2D}s and returns
     * a new point with the coordinates rounded to the nearest integers.
     * @param arg1 the first point
     * @param arg2 the second point
     * @return a new point that is the sum between the two given {@code Point2D}s
     * with the coordinates rounded to the nearest integers.
     */
    public static Point2D roundedSumPoint2D(final Point2D arg1, final Point2D arg2) {
        return new Point2D((int) arg1.getX() + (int) arg2.getX(),
                (int) arg1.getY() + (int) arg2.getY());
    }

    /**
     * Computes the distance between two {@link Point2D}s and rounds it to
     * the nearest integer.
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @return the integer value of the difference
     */
    public static int distanceBetweenPoint2D(final Point2D arg1, final Point2D arg2) {
        return (int) (Math.abs(arg1.getX() - arg2.getX())
                + Math.abs(arg1.getY() - arg2.getY()));
    }

    /**
     * Computes the distance between two pairs of integers.
     * @param p1 the first pair
     * @param p2 the second pair
     * @return the distance between the given pairs.
     */
    public static double distance(final Pair<Integer, Integer> p1, final Pair<Integer, Integer> p2) {
        return Math.sqrt((p2.getKey() - p1.getKey()) * (p2.getKey() - p1.getKey())
                + (p2.getValue() - p1.getValue()) * (p2.getValue() - p1.getValue()));
    }

    /**
     * Sums a variable number of pairs of integers.
     * @param points the series of pairs to be summed together.
     * @return the sum of all given pairs.
     */
    @SafeVarargs
    public static Pair<Integer, Integer> sum(final Pair<Integer, Integer>... points) {
        int k = 0;
        int v = 0;
        for (final Pair<Integer,  Integer> point : points) {
            k += point.getKey();
            v += point.getValue();
        }
        return new Pair<>(k, v);
    }

    /**
     * Subtracts a variable number of pairs of integers from {@code p}.
     * @param p the subtrahend.
     * @param points the series of pairs that will be subtracted from p.
     * @return the result of the subtraction.
     */
    @SafeVarargs
    public static Pair<Integer, Integer> subtract(final Pair<Integer, Integer> p, final Pair<Integer, Integer>... points) {
        int k = p.getKey();
        int v = p.getValue();
        for (final Pair<Integer, Integer> point : points) {
            k -= point.getKey();
            v -= point.getValue();
        }
        return new Pair<>(k, v);
    }

    /**
     * Generates a random number ranging from lowerBound (included) to upperBound (excluded).
     * @param rnd instance of {@link Random}
     * @param lowerBound the minimum value
     * @param upperBound the maximum value
     * @return a random integer ranging between the given bounds
     */
    public static int randomBetween(final Random rnd, final int lowerBound, final int upperBound) {
        if (upperBound < lowerBound) {
            throw new IllegalArgumentException("Upper bound must be greater than the lower bound");
        }
        return rnd.nextInt(upperBound - lowerBound) + lowerBound;
    }
}
