package hotlinecesena.model.entities;

import hotlinecesena.model.entities.actors.DirectionList;
import hotlinecesena.model.events.MovementEvent;
import javafx.geometry.Point2D;

/**
 * 
 * Template for generic entities capable of moving.
 *
 */
public abstract class AbstractMovableEntity extends AbstractEntity implements MovableEntity {

    private static final double MOVE_NOISE = 5.0;
    private double speed;

    protected AbstractMovableEntity(final Point2D pos, final double angle, final double speed) {
        super(pos, angle);
        this.speed = speed;
    }

    @Override
    public void move(Point2D direction) {
        if (!direction.equals(DirectionList.NONE.get())) {
            final Point2D oldPos = this.getPosition();
            final Point2D newPos = oldPos.add(direction.multiply(this.speed));
            this.setPosition(newPos);
            this.publish(new MovementEvent(this, newPos, MOVE_NOISE));
        }
    }

    @Override
    public final double getSpeed() {
        return this.speed;
    }

    @Override
    public final void setSpeed(double speed) {
        this.speed = speed;
    }
}
