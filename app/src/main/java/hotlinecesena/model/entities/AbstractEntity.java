package hotlinecesena.model.entities;

import java.util.Objects;

import javax.annotation.Nonnull;

import hotlinecesena.model.dataccesslayer.DataAccessLayer;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.events.Event;
import hotlinecesena.model.events.Publisher;
import hotlinecesena.model.events.PublisherComponent;
import hotlinecesena.model.events.Subscriber;
import javafx.geometry.Point2D;

/**
 *
 * Template for generic entities. Delegates event publishing
 * to an external {@link Publisher}.
 */
public abstract class AbstractEntity implements Entity {

    private Point2D position;
    private final double width;
    private final double height;
    private final Publisher publisher;

    /**
     *
     * @param position starting position in which this entity will be located.
     * @param width this entity's width.
     * @param height this entity's height.
     * @throws NullPointerException if the given position is null.
     */
    protected AbstractEntity(@Nonnull final Point2D position, final double width, final double height) {
        this.position = Objects.requireNonNull(position);
        this.width = width;
        this.height = height;
        publisher = new PublisherComponent();
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
    protected final void setPosition(@Nonnull final Point2D pos) {
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

    @Override
    public final void publish(@Nonnull final Event event) {
        publisher.publish(event);
    }

    @Override
    public final void register(@Nonnull final Subscriber subscriber) {
        publisher.register(subscriber);
    }

    @Override
    public final void unregister(@Nonnull final Subscriber subscriber) {
        publisher.unregister(subscriber);
    }

    /**
     * Convenience method to be used internally.
     * @return the instance of the {@link JSONDataAccessLayer}.
     */
    protected final DataAccessLayer getGameMaster() {
        return JSONDataAccessLayer.getInstance();
    }
}
