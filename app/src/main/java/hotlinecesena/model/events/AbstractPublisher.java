package hotlinecesena.model.events;

import com.google.common.eventbus.EventBus;

public abstract class AbstractPublisher implements Publisher {

    private final EventBus bus;

    protected AbstractPublisher(final String identifier) {
        this.bus = new EventBus(identifier);
    }

    protected void publish(Event event) {
        this.bus.post(event);
    }

    @Override
    public void register(Subscriber subscriber) {
        this.bus.register(subscriber);
    }

    @Override
    public void unregister(Subscriber subscriber) {
        this.bus.unregister(subscriber);
    }
}
