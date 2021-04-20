package hotlinecesena.model.events;

import java.util.Objects;

import javax.annotation.Nonnull;

import com.google.common.eventbus.EventBus;

/**
 * Implements a {@link Publisher} wrapping Google Guava's {@link EventBus}.
 */
public class PublisherComponent implements Publisher {

    private final EventBus bus;

    /**
     * Instantiates a new {@code PublisherComponent} which
     * other classes may use to publish events.
     */
    public PublisherComponent() {
        bus = new EventBus();
    }

    /**
     * @throws NullPointerException if the given event is null.
     */
    @Override
    public final void publish(@Nonnull final Event event) {
        bus.post(Objects.requireNonNull(event));
    }

    /**
     * @throws NullPointerException if the supplied subscriber is null.
     */
    @Override
    public final void register(@Nonnull final Subscriber subscriber) {
        bus.register(Objects.requireNonNull(subscriber));
    }

    /**
     * @throws NullPointerException if the supplied subscriber is null.
     */
    @Override
    public final void unregister(@Nonnull final Subscriber subscriber) {
        bus.unregister(Objects.requireNonNull(subscriber));
    }
}
