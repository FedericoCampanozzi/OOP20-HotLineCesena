package hotlinecesena.model.events;

import hotlinecesena.model.entities.Entity;

public final class DeathEvent extends AbstractEvent {

    public DeathEvent(final Entity source) {
        super(source);
    }
}
