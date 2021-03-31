package hotlinecesena.model.entities;

import hotlinecesena.model.events.AbstractPublisher;
import hotlinecesena.model.events.RotationEvent;
import javafx.geometry.Point2D;

/**
 * 
 * Template for generic entities.
 */
public abstract class AbstractEntity extends AbstractPublisher implements Entity {

    private Point2D position;
    private double angle;

    protected AbstractEntity(final Point2D pos, final double angle) {
        super("Entity");
        this.position = pos;
        this.angle = angle;
    }
    
    @Override
    public final Point2D getPosition() {
        return this.position;
    }

    /**
     * Sets this entity's current position to {@code pos}.
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
}
