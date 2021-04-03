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

    protected AbstractEntity(final Point2D pos, final double angle) {
        this.position = pos;
        this.angle = angle;
        this.bus = new EventBus();
    }
    
    @Override
    public final Point2D getPosition() {
        return this.position;
    }

    /**
     * Sets this entity's current position to {@code pos}.
     * <br>
     * Position may not be modified by external objects.
     * 
     * @param pos the new position.
     */
    protected final void setPosition(final Point2D pos) {
        this.position = pos;
    }

    @Override
    public final double getAngle() {
        return this.angle;
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
