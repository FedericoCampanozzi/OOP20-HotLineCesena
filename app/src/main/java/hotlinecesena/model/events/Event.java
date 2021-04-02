package hotlinecesena.model.events;

import hotlinecesena.model.entities.Entity;

/**
 * Loosely based on github.com/iluwatar/java-design-patterns/tree/master/data-bus
 * and adapted for use with Guava's EventBus without singletons.
 */
public interface Event {

    /**
     * 
     * @return the object which produced this event.
     */
    Entity getSource();
}
