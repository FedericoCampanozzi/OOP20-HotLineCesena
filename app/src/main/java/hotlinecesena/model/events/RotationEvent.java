package hotlinecesena.model.events;

import hotlinecesena.model.entities.Entity;

public class RotationEvent extends AbstractEvent {

    private final double newAngle;

    public RotationEvent(final Entity source, final double newAngle) {
        super(source);
        this.newAngle = newAngle;
    }

    public double getNewAngle() {
        return this.newAngle;
    }
}
