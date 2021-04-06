package hotlinecesena.model.events;

import hotlinecesena.model.entities.Entity;

/**
 *
 * Common class for all events related to entities.
 * @param <E> an interface that extends Entity.
 */
public abstract class AbstractEvent<E extends Entity> implements Event<E> {

    private final E source;

    protected AbstractEvent(final E source) {
        this.source = source;
    }

    @Override
    public final E getSource() {
        return source;
    }
}
