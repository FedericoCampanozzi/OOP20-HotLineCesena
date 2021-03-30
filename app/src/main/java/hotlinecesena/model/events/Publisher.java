package hotlinecesena.model.events;

/**
 * 
 * Models an object capable of publishing events on the {@link EventBus}.
 *
 */
public interface Publisher {

    /**
     * Registers a {@link Subscriber} that will listen to this object's events.
     * @param subscriber
     */
    void register(Subscriber subscriber);
    
    /**
     * Unrestigers a previously registered {@link Subscriber}.
     * @param subscriber
     */
    void unregister(Subscriber subscriber);
}
