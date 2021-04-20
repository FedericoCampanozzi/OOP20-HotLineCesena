package hotlinecesena.model.entities.items.attack;

import java.util.concurrent.ThreadLocalRandom;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.items.ProjectileImpl;
import hotlinecesena.model.entities.items.WeaponType;
import javafx.geometry.Point2D;

/**
 * Implements a shot made of multiple projectiles.
 */
public class SpreadShot implements AttackStrategy {

    private static final int PROJ_NUMBER = 5;

    /**
     * Create {@code PROJ_NUMBER} new projectiles.
     */
    @Override
    public void shoot(final WeaponType type, final Point2D actorPosition, final double actorAngle, final double actorWidth, final double actorHeight, final double projectileSize) {
        final ThreadLocalRandom current = ThreadLocalRandom.current();
        for (int i = 0; i < PROJ_NUMBER; i++) {
            final double randomAngle = current.nextDouble(-20.0, 20.0) + actorAngle;
            final ProjectileImpl proj = new ProjectileImpl(
                    this.computeStartingPoint(actorPosition, actorAngle, actorWidth, actorHeight),
                    randomAngle, projectileSize, projectileSize, type.getProjectileSpeed(), type.getDamage() / PROJ_NUMBER);
            JSONDataAccessLayer.getInstance().getBullets().getProjectile().add(proj);
        }
    }
}
