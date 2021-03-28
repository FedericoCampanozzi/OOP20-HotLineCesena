package hotlinecesena.utilities;

import javafx.geometry.Point2D;

public class MathUtils {

    public static final double SIN_45 = .707106;
    
    public static Point2D normalizeDiagonalMovement(final Point2D point) {
        return point.multiply(SIN_45);
    }
    
    public static double mouseToDegrees(final Point2D coords) {
        final double angle = Math.toDegrees(Math.atan2(coords.getY(), coords.getX()));
        return angle < 0 ? 360 + angle : angle;
    }
}
