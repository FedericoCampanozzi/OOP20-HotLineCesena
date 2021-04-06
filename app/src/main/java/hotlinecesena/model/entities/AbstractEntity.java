package hotlinecesena.model.entities;

import java.util.Objects;

import com.google.common.eventbus.EventBus;

import hotlinecesena.model.events.Event;
import hotlinecesena.model.events.RotationEvent;
import hotlinecesena.model.events.Subscriber;
import javafx.geometry.Point2D;

/**
 *
 * Template for generic entities.
 */
public abstract class AbstractEntity implements Entity {

    private Point2D position;
    private double angle;
    private final double width;
    private final double height;
    private final EventBus bus;

    /**
     *
     * @param position starting position in which this entity will be located.
     * @param angle starting angle that this entity will face.
     * @param width this entity's width.
     * @param height this entity's height.
     * @throws NullPointerException if given position is null.
     */
    protected AbstractEntity(final Point2D position, final double angle, final double width, final double height) {
        this.position = Objects.requireNonNull(position);
        this.angle = angle;
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
    public final double getAngle() {
        return angle;
    }

    /**
     * @implSpec
     * Can be overridden if subclasses require that other conditions be satisfied
     * before setting a new angle.
     */
    @Override
    public final void setAngle(final double angle) {
        if (this.angle != angle) {
            this.angle = angle;
            this.publish(new RotationEvent(this, angle));
        }
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
     * Posts an {@link Event} on this entity's {@link EventBus}.
     * <br>
     * Events may not be posted by external objects.
     *
     * @param event
     * @throws NullPointerException if the supplied event is null.
     */
    protected void publish(final Event event) {
        bus.post(Objects.requireNonNull(event));
    }

    /**
     * @throws NullPointerException if the supplied subscriber is null.
     */
    @Override
    public void register(final Subscriber subscriber) {
        bus.register(Objects.requireNonNull(subscriber));
    }

    /**
     * @throws NullPointerException if the supplied subscriber is null.
     */
    @Override
    public void unregister(final Subscriber subscriber) {
        bus.unregister(Objects.requireNonNull(subscriber));
    }
}
