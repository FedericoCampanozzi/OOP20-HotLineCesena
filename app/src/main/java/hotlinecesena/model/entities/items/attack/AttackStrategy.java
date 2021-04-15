package hotlinecesena.model.entities.items.attack;

import hotlinecesena.model.entities.items.WeaponType;
import javafx.geometry.Point2D;

public interface AttackStrategy {

    void shoot(WeaponType type, Point2D actorPosition, double actorAngle, double actorWidth, double actorHeight,
            double projectileSize);

    default Point2D computeStartingPoint(final Point2D actorPosition, final double angle, final double width,
            final double height) {
        final double centerX = actorPosition.getX() + width / 2;
        final double centerY = actorPosition.getY() + height / 2;
        return new Point2D(centerX + width * Math.cos(Math.toRadians(angle)),
                centerY + height * Math.sin(Math.toRadians(angle)));
    }
}
