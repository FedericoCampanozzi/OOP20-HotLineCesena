package hotlinecesena.model.events;

import hotlinecesena.model.entities.MovableEntity;

/**
 *
 * Notifies that an entity has rotated.
 * @param <M> an interface that extends {@link MovableEntity}.
 */
public final class RotationEvent<M extends MovableEntity> extends AbstractEvent<M> {

    private final double newAngle;

    public RotationEvent(final M source, final double newAngle) {
        super(source);
        this.newAngle = newAngle;
    }

    /**
     * Returns the new angle faced by the entity.
     * @return the new angle.
     */
    public double getNewAngle() {
        return newAngle;
    }
}
