package hotlinecesena.model.events;

import java.util.List;

import org.apache.commons.lang3.ClassUtils;

import hotlinecesena.model.entities.Entity;

/**
 * Common class for all events related to entities.
 * Extracts the interfaces implemented by the given
 * entity and returns them when needed.
 */
public abstract class AbstractEvent implements Event {

    private final List<Class<?>> sourceInterfaces;

    /**
     * Common constructor for events.
     * @param <E> an interface that extends {@link Entity}.
     * @param source the entity that has triggered this event.
     */
    protected <E extends Entity> AbstractEvent(final E source) {
        sourceInterfaces = ClassUtils.getAllInterfaces(source.getClass());
    }

    @Override
    public final List<Class<?>> getSourceInterfaces() {
        return sourceInterfaces;
    }
}
