package hotlinecesena.model.events;

/**
 * Loosely based on github.com/iluwatar/java-design-patterns/tree/master/data-bus
 * and adapted for use with Guava's EventBus without singletons.
 */
public interface Event<T> {

    /**
     * 
     * @return the object which produced this event.
     */
    T getSource();
}
