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
     * Calculates an integer sum between two points.
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @return a new {@code Point2D}
     */
    public static Point2D roundedSumPoint2D(final Point2D arg1, final Point2D arg2) {
        return new Point2D((int) arg1.getX() + (int) arg2.getX(),
                (int) arg1.getY() + (int) arg2.getY());
    }

    /**
     * Calculates the distance between two points.
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @return the integer value of the difference
     */
    public static int distanceBetweenPoint2D(final Point2D arg1, final Point2D arg2) {
        return (int) (Math.abs(arg1.getX() - arg2.getX())
                + Math.abs(arg1.getY() - arg2.getY()));
    }
    
    /**
	 * Get a distance of two integer position
	 */
	public static double distance(Pair<Integer,Integer> p1, Pair<Integer, Integer> p2) {
		return Math.sqrt((p2.getKey() - p1.getKey()) * (p2.getKey() - p1.getKey()) + (p2.getValue() - p1.getValue()) * (p2.getValue() - p1.getValue()));
	}
	
    /**
	 * Sum a variable number of positions
	 * @param points All points to be added together
	 * @return The final position
	 */
	@SafeVarargs
	public static Pair<Integer,Integer> sum( Pair<Integer,Integer>... points) {
		int k = 0;
		int v = 0;
		for(Pair<Integer,Integer> point : points) {
			k += point.getKey();
			v += point.getValue();
		}
		return new Pair<>(k, v);
	}
	
	/**
	 * Subtract to p some points
	 * @param p The point that have to be subtracted
	 * @param points All points to be subtracted
	 * @return The result point
	 */
	@SafeVarargs
	public static Pair<Integer,Integer> subtract(Pair<Integer,Integer> p, Pair<Integer,Integer>... point) {
		int k = p.getKey();
		int v = p.getValue();
		for(Pair<Integer,Integer> points : point) {
			k -= points.getKey();
			v -= points.getValue();
		}
		return new Pair<>(k, v);
	}
	
	/**
	 * Generate a number from lowerBound (include) to upperBound (exclude)
	 * @param rnd instance of random
	 * @param lowerBound the minimums value possible
	 * @param upperBound the maximums value possible
	 * @return a random int
	 */
	public static int randomBetween(Random rnd, int lowerBound, int upperBound) {
		if(upperBound  < lowerBound) {
			throw new IllegalArgumentException();
		}
		return rnd.nextInt(upperBound - lowerBound) + lowerBound;
	}
}
