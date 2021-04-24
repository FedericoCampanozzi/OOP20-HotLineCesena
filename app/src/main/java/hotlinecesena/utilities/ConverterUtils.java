package hotlinecesena.utilities;

import javafx.geometry.Point2D;
import javafx.util.Pair;

public class ConverterUtils {
	
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
}
