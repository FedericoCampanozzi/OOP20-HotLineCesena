package hotlinecesena.model.entities;

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
    private final EventBus bus;

    protected AbstractEntity(final Point2D position, final double angle) {
        this.position = position;
        this.angle = angle;
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
     */
    protected final void setPosition(final Point2D pos) {
        position = pos;
    }

    @Override
    public final double getAngle() {
        return angle;
    }

    @Override
    public void setAngle(final double angle) {
        if (this.angle != angle) {
            this.angle = angle;
            this.publish(new RotationEvent(this, angle));
        }
    }

    /**
     * Posts an {@link Event} on this entity's {@link EventBus}.
     * <br>
     * Events may not be posted by external objects.
     *
     * @param event
     */
    protected void publish(final Event event) {
        bus.post(event);
    }

    @Override
    public void register(final Subscriber subscriber) {
        bus.register(subscriber);
    }

    @Override
    public void unregister(final Subscriber subscriber) {
        bus.unregister(subscriber);
    }
}
