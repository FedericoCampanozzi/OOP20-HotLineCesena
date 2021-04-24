package hotlinecesena.model.events;

import java.util.Collection;

/**
 * Models an event that game entities may trigger to notify
 * external objects.
 */
public interface Event {

    /**
     * Returns the interfaces implemented by the entity
     * that has triggered this event.
     * @return the interfaces implemented by the entity
     * that has triggered this event.
     */
    Collection<Class<?>> getSourceInterfaces();
}
