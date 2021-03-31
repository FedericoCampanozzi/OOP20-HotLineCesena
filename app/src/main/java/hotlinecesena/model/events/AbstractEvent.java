package hotlinecesena.model.events;

import hotlinecesena.model.entities.Entity;

public abstract class AbstractEvent implements Event {

    private Entity source;

    protected AbstractEvent(final Entity source) {
        this.source = source;
    }

    @Override
    public Entity getSource() {
        return this.source;
    }
}
