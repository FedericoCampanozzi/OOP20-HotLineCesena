package hotlinecesena.model.events;

import hotlinecesena.model.entities.Entity;

/**
 * Loosely based on github.com/iluwatar/java-design-patterns/tree/master/data-bus
 * and adapted for use with Guava's EventBus without singletons.
 * @param <E> an interface extending {@link Entity}.
 */
public interface Event<E extends Entity> {

    /**
     * Returns the {@link Entity} (or a subclass of {@code Entity})
     * that has triggered this event.
     * @return the {@code Entity} that has triggered this event.
     */
    E getSource();
}
