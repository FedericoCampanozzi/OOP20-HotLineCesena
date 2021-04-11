package hotlinecesena;

import java.util.Random;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import hotlinecesena.utilities.Utilities;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
	/**
	 * Can't generate a number if upperBound > lowerBound
	 */
	public void wrongParametresForRandom() {
		try {
			Utilities.RandomBetween(new Random(), 10, 5);
		} catch (IllegalArgumentException exc) {
			assertTrue(true);
		} catch (Exception e) {
			fail("Can't generate a number if upperBound > lowerBound");
		}
	}

}
