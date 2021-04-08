package hotlinecesena;

import javafx.geometry.Point2D;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import hotlinecesena.utilities.Utilities;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilitiesTest {

	@Test
	public void testSumPair() {
		final Pair<Integer,Integer> p1 = new Pair<Integer,Integer>(1,1);
		final Pair<Integer,Integer> p2 = new Pair<Integer,Integer>(2,0);
		final Pair<Integer,Integer> p3 = new Pair<Integer,Integer>(-1,0);
		final Pair<Integer,Integer> p4 = new Pair<Integer,Integer>(5,2);
		final Pair<Integer,Integer> p12 = new Pair<Integer,Integer>(3,1);
		final Pair<Integer,Integer> p1234 = new Pair<Integer,Integer>(7,3); 
		assertEquals(p12, Utilities.sumPair(p1, p2));
		assertEquals(p1234, Utilities.sumPair(p1, p2, p3, p4));
	}
	@Test
	public void testSubPair() {
		final Pair<Integer,Integer> p1 = new Pair<Integer,Integer>(1,1);
		final Pair<Integer,Integer> p2 = new Pair<Integer,Integer>(2,0);
		final Pair<Integer,Integer> p3 = new Pair<Integer,Integer>(-1,0);
		final Pair<Integer,Integer> p4 = new Pair<Integer,Integer>(5,2);
		final Pair<Integer,Integer> p12 = new Pair<Integer,Integer>(-1,1);
		final Pair<Integer,Integer> p1234 = new Pair<Integer,Integer>(-5,-1); 
		assertEquals(p12, Utilities.subPair(p1, p2));
		assertEquals(p1234, Utilities.subPair(p1, p2, p3, p4));
	}
	@Test
	public void testMulPairScalar() {
		final Pair<Integer,Integer> p1 = new Pair<Integer,Integer>(1,1);
		final Pair<Integer,Integer> p1s = new Pair<Integer,Integer>(2,2);
		final int s = 2;
		assertEquals(p1s, Utilities.mulPairScalar(p1, s));
	}
	@Test
	public void testConvertPairToPoint2D() {
		final Pair<Integer,Integer> p1 = new Pair<Integer,Integer>(1,1);
		final Point2D p = new Point2D(5.0, 5.0);
		final int tileSize = 5;
		assertEquals(p, Utilities.convertPairToPoint2D(p1, tileSize));
	}
}
