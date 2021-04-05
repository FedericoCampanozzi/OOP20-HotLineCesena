package hotlinecesena.utilities;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.util.Pair;

public class Utilities {
	
	public static Pair<Integer,Integer> sumPair(Pair<Integer,Integer> p1, Pair<Integer, Integer> p2) {
		return new Pair<>(p1.getKey() + p2.getKey(), p1.getValue() + p2.getValue());
	}
	
	public static Pair<Integer,Integer> subPair(Pair<Integer,Integer> p1, Pair<Integer, Integer> p2) {
		return new Pair<>(p1.getKey() - p2.getKey(), p1.getValue() - p2.getValue());
	}
	
	public static boolean intersect(BoundingBox b1, BoundingBox b2) {
		return b1.intersects(b2);
	}
	
	public static Point2D convertPairToPoint2D(Pair<Integer,Integer> p, int tileSize) {
		return new Point2D(tileSize * p.getKey(), tileSize * p.getValue());
	}
}