package hotlinecesena;

import java.util.Random;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import hotlinecesena.utilities.MathUtils;
import hotlinecesena.utilities.ConverterUtils;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class UtilitiesTest {

	@Test
	public void testSum() {
		final Pair<Integer,Integer> p1 = new Pair<Integer,Integer>(1,1);
		final Pair<Integer,Integer> p2 = new Pair<Integer,Integer>(2,0);
		final Pair<Integer,Integer> p3 = new Pair<Integer,Integer>(-1,0);
		final Pair<Integer,Integer> p4 = new Pair<Integer,Integer>(5,2);
		final Pair<Integer,Integer> p12 = new Pair<Integer,Integer>(3,1);
		final Pair<Integer,Integer> p1234 = new Pair<Integer,Integer>(7,3); 
		assertEquals(p12, MathUtils.sum(p1, p2));
		assertEquals(p1234, MathUtils.sum(p1, p2, p3, p4));
	}
	
	@Test
	public void testSubtract() {
		final Pair<Integer,Integer> p1 = new Pair<Integer,Integer>(1,1);
		final Pair<Integer,Integer> p2 = new Pair<Integer,Integer>(2,0);
		final Pair<Integer,Integer> p3 = new Pair<Integer,Integer>(-1,0);
		final Pair<Integer,Integer> p4 = new Pair<Integer,Integer>(5,2);
		final Pair<Integer,Integer> p12 = new Pair<Integer,Integer>(-1,1);
		final Pair<Integer,Integer> p1234 = new Pair<Integer,Integer>(-5,-1); 
		assertEquals(p12, MathUtils.subtract(p1, p2));
		assertEquals(p1234, MathUtils.subtract(p1, p2, p3, p4));
	}
	
	@Test
	/**
	 * Can't generate a number if upperBound > lowerBound
	 */
	public void wrongParametresForRandom() {
		try {
			MathUtils.randomBetween(new Random(), 10, 5);
		} catch (IllegalArgumentException exc) {
			assertTrue(true);
		} catch (Exception e) {
			fail("Can't generate a number if upperBound > lowerBound");
		}
	}
	
	@Test
	public void testDistance() {
		final Pair<Integer,Integer> p1 = new Pair<Integer,Integer>(1,1);
		final Pair<Integer,Integer> p2 = new Pair<Integer,Integer>(2,0);
		float distance = 1.4142f;
		assertEquals(MathUtils.distance(p1, p2), distance, 0.005f);
	}
	
	@Test
	public void testColorConversion() {
		javafx.scene.paint.Color cJavaFX = new javafx.scene.paint.Color(1, 1, 1, 1);
		java.awt.Color cJavaAWT = new java.awt.Color(255,255, 255, 255);
		assertEquals(ConverterUtils.convertColor(cJavaAWT), cJavaFX);
	}
	
	@Test
	public void testConvertPairToPoint2D() {
		final Pair<Integer,Integer> p = new Pair<Integer,Integer>(1,1);
		assertEquals(p, ConverterUtils.convertPoint2DToPair(ConverterUtils.convertPairToPoint2D(p)));
	}
	
	@Test
	public void testConvertTimeToFormatString() {
		long seconds = 61000L;
		assertEquals("00:01:01", ConverterUtils.convertSecondsToTimeString(seconds, "%02d:%02d:%02d"));
		assertEquals("0h 1m 1s", ConverterUtils.convertSecondsToTimeString(seconds, "%dh %dm %ds"));
	}
}
