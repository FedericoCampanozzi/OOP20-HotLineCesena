package hotlinecesena.model.entities.items.attack;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.items.Projectile;
import hotlinecesena.model.entities.items.ProjectileImpl;
import hotlinecesena.model.entities.items.WeaponType;
import javafx.geometry.Point2D;

/**
 * Implements a single straight shot performed by an actor.
 */
public class SingleStraightShot implements AttackStrategy {

	/**
	 * Creates a new single projectile
	 */
    @Override
    public void shoot(final WeaponType type, final Point2D actorPosition, final double actorAngle, final double actorWidth,
            final double actorHeight, final double projectileSize) {
        final Projectile proj = new ProjectileImpl(
                this.computeStartingPoint(actorPosition, actorAngle, actorWidth, actorHeight),
                actorAngle, projectileSize, projectileSize, type.getProjectileSpeed(), type.getDamage());
        JSONDataAccessLayer.getInstance().getBullets().getProjectile().add(proj);
    }
}