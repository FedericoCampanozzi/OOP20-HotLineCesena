package hotlinecesena.model.entities;

import java.util.Objects;

import com.google.common.eventbus.EventBus;

import hotlinecesena.model.dataccesslayer.DataAccessLayer;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.events.Event;
import hotlinecesena.model.events.Subscriber;
import javafx.geometry.Point2D;

/**
 *
 * Template for generic entities.
 */
public abstract class AbstractEntity implements Entity {

    private Point2D position;
    private final double width;
    private final double height;
    private final EventBus bus;

    /**
     *
     * @param position starting position in which this entity will be located.
     * @param width this entity's width.
     * @param height this entity's height.
     * @throws NullPointerException if given position is null.
     */
    protected AbstractEntity(final Point2D position, final double width, final double height) {
        this.position = Objects.requireNonNull(position);
        this.width = width;
        this.height = height;
        bus = new EventBus();
    }

    @Override
    public final Point2D getPosition() {
        return position;
    }

    /**
     * Sets this entity's current position to {@code pos}.
     * <br>
     * Position may not be modified by external objects.
     *
     * @param pos the new position.
     * @throws NullPointerException if the supplied {@link Point2D} is null.
     */
    protected final void setPosition(final Point2D pos) {
        position = Objects.requireNonNull(pos);
    }

    @Override
    public final double getWidth() {
        return width;
    }

    @Override
    public final double getHeight() {
        return height;
    }

    /**
     * Convenience method to be used internally.
     * @return the instance of the {@link JSONDataAccessLayer}.
     */
    protected final DataAccessLayer getGameMaster() {
        return JSONDataAccessLayer.getInstance();
    }

    /**
     * Publishes an {@link Event} on this entity's {@link EventBus}.
     * <br>
     * Events may not be posted by external objects.
     *
     * @param event the event to be published
     * @throws NullPointerException if the given event is null.
     */
    protected final void publish(final Event<? extends Entity> event) {
        bus.post(Objects.requireNonNull(event));
    }

    /**
     * @throws NullPointerException if the supplied subscriber is null.
     */
    @Override
    public final void register(final Subscriber subscriber) {
        bus.register(Objects.requireNonNull(subscriber));
    }

    /**
     * @throws NullPointerException if the supplied subscriber is null.
     */
    @Override
    public final void unregister(final Subscriber subscriber) {
        bus.unregister(Objects.requireNonNull(subscriber));
    }
}
