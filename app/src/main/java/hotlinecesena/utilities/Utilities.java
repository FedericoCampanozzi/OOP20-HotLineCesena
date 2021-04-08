package hotlinecesena.utilities;

import java.util.Random;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.util.Pair;

public class Utilities {
	
	public static double distance(Pair<Integer,Integer> p1, Pair<Integer, Integer> p2) {
		return Math.sqrt((p2.getKey() - p1.getKey()) * (p2.getKey() - p1.getKey()) + (p2.getValue() - p1.getValue()) * (p2.getValue() - p1.getValue()));
	}
	
	public static Pair<Integer,Integer> sumPair(Pair<Integer,Integer> p1, Pair<Integer, Integer> p2) {
		return new Pair<>(p1.getKey() + p2.getKey(), p1.getValue() + p2.getValue());
	}
	
	public static Pair<Integer,Integer> subPair(Pair<Integer,Integer> p1, Pair<Integer, Integer> p2) {
		return new Pair<>(p1.getKey() - p2.getKey(), p1.getValue() - p2.getValue());
	}
	
	public static Pair<Integer,Integer> mulPairScalar(Pair<Integer,Integer> p, int scalar) {
		return new Pair<>(scalar * p.getKey(), scalar * p.getValue());
	}
	
	public static boolean intersect(BoundingBox b1, BoundingBox b2) {
		return b1.intersects(b2);
	}
	
	public static Point2D convertPairToPoint2D(Pair<Integer,Integer> p, int tileSize) {
		return new Point2D(tileSize * p.getKey(), tileSize * p.getValue());
	}
	
	public static int RandomBetween(Random rnd, int lowerBound, int upperBound) {
		if(upperBound < lowerBound) {
			throw new IllegalArgumentException();
		}
		return rnd.nextInt(upperBound - lowerBound) + lowerBound;
	}
}