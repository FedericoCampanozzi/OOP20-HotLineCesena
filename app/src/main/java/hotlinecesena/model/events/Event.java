package hotlinecesena.model.events;

import java.util.Collection;

import hotlinecesena.model.entities.Entity;

/**
 * Models an event that game entities may trigger to notify
 * external controllers.
 * @param <E> an interface extending {@link Entity}.
 */
public interface Event<E extends Entity> {

    /**
     * Returns the interfaces implemented by the entity
     * that has triggered this event.
     * @return the interfaces implemented by the entity
     * that has triggered this event.
     */
    Collection<Class<?>> getSourceInterfaces();
}
