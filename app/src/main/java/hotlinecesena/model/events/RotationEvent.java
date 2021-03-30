package hotlinecesena.model.events;

import hotlinecesena.model.entities.Entity;

public class RotationEvent<E extends Entity> extends AbstractEvent<E> {

    private final double newAngle;

    public RotationEvent(final E source, final double newAngle) {
        super(source);
        this.newAngle = newAngle;
    }

    public double getNewAngle() {
        return this.newAngle;
    }
}
