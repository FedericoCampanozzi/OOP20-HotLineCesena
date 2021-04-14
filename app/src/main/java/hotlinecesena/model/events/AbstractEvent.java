package hotlinecesena.model.events;

import java.util.Arrays;
import java.util.List;

import hotlinecesena.model.entities.Entity;

/**
 * Common class for all events related to entities.
 * Extracts the interfaces implemented by the given
 * entity and returns them when needed.
 * @param <E> an interface that extends Entity.
 */
public abstract class AbstractEvent<E extends Entity> implements Event<E> {

    private final List<Class<?>> sourceInterfaces;

    /**
     * Common constructor for events.
     * @param source the entity that has triggered this event.
     */
    protected AbstractEvent(final E source) {
        this.sourceInterfaces = Arrays.asList(source.getClass().getInterfaces());
    }

    @Override
    public final List<Class<?>> getSourceInterfaces() {
        return sourceInterfaces;
    }
}
