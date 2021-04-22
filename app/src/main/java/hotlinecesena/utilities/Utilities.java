package hotlinecesena.utilities;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.util.Pair;

/**
 * A utility Static class for simplify commons calculations
 * @author Federico
 */
public class Utilities {
	
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
	public static Pair<Integer,Integer> sumPair( Pair<Integer,Integer>... points) {
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
	public static Pair<Integer,Integer> subPair(Pair<Integer,Integer> p, Pair<Integer,Integer>... point) {
		int k = p.getKey();
		int v = p.getValue();
		for(Pair<Integer,Integer> points : point) {
			k -= points.getKey();
			v -= points.getValue();
		}
		return new Pair<>(k, v);
	}
	
	/**
	 * Multiply a integer point from scalar
	 * @param p The point
	 * @param scalar The natural number
	 * @return The result point
	 */
	public static Pair<Integer,Integer> mulPairScalar(Pair<Integer,Integer> p, int scalar) {
		return new Pair<>(scalar * p.getKey(), scalar * p.getValue());
	}
	
	/**
	 * Convert integer position in a spatial position
	 */
	public static Point2D convertPairToPoint2D(Pair<Integer,Integer> p) {
		return new Point2D((double)p.getKey(), (double)p.getValue());
	}
	
	/**
	 * Convert spatial position in a integer position 
	 */
	public static Pair<Integer,Integer> convertPoint2DToPair(Point2D p) {
		return new Pair<Integer,Integer>((int)p.getX(), (int)p.getY());
	}
	
	/**
	 * Convert from color from {@link java.awt.Color} to {@link javafx.scene.paint.Color}
	 */
	public static javafx.scene.paint.Color convertColor(java.awt.Color color) {
		return new javafx.scene.paint.Color(
				color.getRed() / 255.0d,
				color.getGreen() / 255.0d,
				color.getBlue() / 255.0d,
				color.getAlpha() / 255.0d  );
	}
	
	/**
	 * Convert seconds to string time
	 * @param format How output have to be formatted
	 * @param seconds Time in seconds
	 * @return The format string
	 */
	public static String convertSecondsToTimeString(long seconds, String format) {
		long s = (seconds / 1000) % 60;
		long m = (seconds / (1000 * 60)) % 60;
		long h = (seconds / (1000 * 60 * 60)) % 24;
		return String.format(format, h, m, s);
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