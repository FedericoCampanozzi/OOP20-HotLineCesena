package hotlinecesena.model.events;

import hotlinecesena.model.entities.Entity;

public class DamageReceivedEvent extends AbstractEvent {

    private final double damagePoints;

    public DamageReceivedEvent(final Entity source, final double damagePoints) {
        super(source);
        this.damagePoints = damagePoints;
    }

    public double getDamage() {
        return this.damagePoints;
    }
}
