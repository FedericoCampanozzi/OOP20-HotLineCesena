package hotlinecesena.model.entities.items;

import hotlinecesena.model.entities.MovableEntity;
import hotlinecesena.model.entities.actors.Status;

/**
 * A movable entity generated when an actor shoot.
 */
public interface Projectile extends MovableEntity {

	/**
	 * @return the projectile status (MOVING or STOP).
	 */
    Status getProjectileStatus();
}