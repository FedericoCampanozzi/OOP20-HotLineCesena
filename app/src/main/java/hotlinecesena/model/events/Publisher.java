package hotlinecesena.model.events;

/**
 *
 * Models an object capable of publishing events to
 * registered {@link Subscriber}s.
 *
 */
public interface Publisher {

    /**
     * Publishes an {@link Event} which {@link Subscriber}s may
     * receive and handle accordingly.
     * @param event the event to be published
     */
    void publish(Event event);

    /**
     * Registers a {@link Subscriber} that will listen to this object's events.
     * @param subscriber
     */
    void register(Subscriber subscriber);

    /**
     * Unregisters a previously registered {@link Subscriber}.
     * @param subscriber
     */
    void unregister(Subscriber subscriber);
}
