package hotlinecesena.model.entities.items;

import hotlinecesena.model.entities.MovableEntity;
import hotlinecesena.model.entities.actors.Status;

public interface Projectile extends MovableEntity {

    Status getProjectileStatus();
}