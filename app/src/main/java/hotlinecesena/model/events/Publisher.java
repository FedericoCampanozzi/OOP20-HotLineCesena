package hotlinecesena.model.events;

import com.google.common.eventbus.EventBus;

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
     * Unregisters a previously registered {@link Subscriber}.
     * @param subscriber
     */
    void unregister(Subscriber subscriber);
}
