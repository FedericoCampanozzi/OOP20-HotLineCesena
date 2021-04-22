package hotlinecesena.model.entities.items.attack;

import hotlinecesena.model.entities.items.WeaponType;
import javafx.geometry.Point2D;

/**
 * Interface for different type of shooting:
 * {@code SingleStraightShot} and {@code SpreadShot}.
 */
public interface AttackStrategy {

	/**
	 * Create a single shot.
	 * @param type
	 * 				The type of the weapon shooting.
	 * @param actorPosition
	 * 				The position of the actor shooting.
	 * @param actorAngle
	 * 				The angle of the actor shooting.
	 * @param actorWidth
	 * 				The width of the actor shooting.
	 * @param actorHeight
	 * 				The height of the actor shooting.
	 * @param projectileSize
	 * 				The size of the projectile.
	 */
    void shoot(WeaponType type, Point2D actorPosition, double actorAngle, double actorWidth, double actorHeight,
            double projectileSize);

    /**
     * Compute the starting position of the shot.
     * @param actorPosition
     * 				The position of the actor shooting.
     * @param angle
     * 				The angle of the actor shooting.
     * @param width
     * 				The width of the actor shooting.
     * @param height
     * 				The height of the actor shooting.
     * @return
     */
    default Point2D computeStartingPoint(final Point2D actorPosition, final double angle, final double width,
            final double height) {
        final double centerX = actorPosition.getX() + width / 2;
        final double centerY = actorPosition.getY() + height / 2;
        return new Point2D(centerX + width * Math.cos(Math.toRadians(angle)),
                centerY + height * Math.sin(Math.toRadians(angle)));
    }
}
