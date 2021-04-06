package hotlinecesena.model.events;

import hotlinecesena.model.entities.Entity;

public abstract class AbstractEvent implements Event {

    private final Entity source;

    protected AbstractEvent(final Entity source) {
        this.source = source;
    }

    @Override
    public final Entity getSource() {
        return source;
    }
}
