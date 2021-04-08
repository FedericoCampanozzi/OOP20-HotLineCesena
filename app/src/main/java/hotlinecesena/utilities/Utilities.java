package hotlinecesena.utilities;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.util.Pair;

/**
 * static class for simplify come calculations
 * @author Federico
 *
 */
public class Utilities {
	
	/**
	 * get a distance of two integer position
	 * @return the distance
	 */
	public static double distance(Pair<Integer,Integer> p1, Pair<Integer, Integer> p2) {
		return Math.sqrt((p2.getKey() - p1.getKey()) * (p2.getKey() - p1.getKey()) + (p2.getValue() - p1.getValue()) * (p2.getValue() - p1.getValue()));
	}

	/**
	 * Sum a variable number of positions
	 * @param points variable arguments
	 * @return the summed position
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
	 * Subtract to p n points 
	 * @param p point that have to be subtracted
	 * @param points 
	 * @return the result point
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
	 * @param p the point
	 * @param scalar the natural number
	 * @return
	 */
	public static Pair<Integer,Integer> mulPairScalar(Pair<Integer,Integer> p, int scalar) {
		return new Pair<>(scalar * p.getKey(), scalar * p.getValue());
	}
	
	/**
	 * Convert integer positions in a spatial point
	 * @param p the integer position
	 * @param tileSize the size of tile
	 * @return the point
	 */
	public static Point2D convertPairToPoint2D(Pair<Integer,Integer> p, int tileSize) {
		return new Point2D(tileSize * p.getKey(), tileSize * p.getValue());
	}
	
	/**
	 * generate a number from lowerBound (include) to upperBound (exclude)
	 * @param rnd instance of random
	 * @param lowerBound the minimums value possible
	 * @param upperBound the maximums value possible
	 * @return
	 */
	public static int RandomBetween(Random rnd, int lowerBound, int upperBound) {
		if(upperBound < lowerBound) {
			throw new IllegalArgumentException();
		}
		return rnd.nextInt(upperBound - lowerBound) + lowerBound;
	}
}