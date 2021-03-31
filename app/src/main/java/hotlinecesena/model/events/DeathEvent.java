package hotlinecesena.model.events;

import hotlinecesena.model.entities.Entity;

public class DeathEvent extends AbstractEvent {

    public DeathEvent(Entity source) {
        super(source);
    }
}
