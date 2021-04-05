package hotlinecesena.model.entities;

import java.util.Objects;

import hotlinecesena.model.entities.actors.DirectionList;
import hotlinecesena.model.events.MovementEvent;
import javafx.geometry.Point2D;

/**
 *
 * Template for generic entities capable of moving.
 *
 */
public abstract class AbstractMovableEntity extends AbstractEntity implements MovableEntity {

    private double speed;

    /**
     * @param position starting position in which this entity will be located.
     * @param angle starting angle that this entity will face.
     * @param speed the speed at which this entity will move.
     * @throws NullPointerException if given position is null.
     */
    protected AbstractMovableEntity(final Point2D position, final double angle, final double speed) {
        super(position, angle);
        this.speed = speed;
    }

    /**
     * @throws NullPointerException if the supplied direction is null.
     */
    @Override
    public void move(final Point2D direction) {
        Objects.requireNonNull(direction);
        if (!direction.equals(DirectionList.NONE.get())) {
            final Point2D oldPos = this.getPosition();
            final Point2D newPos = oldPos.add(direction.multiply(speed));
            this.setPosition(newPos);
            this.publish(new MovementEvent(this, newPos));
        }
    }

    @Override
    public final double getSpeed() {
        return speed;
    }

    @Override
    public final void setSpeed(final double speed) {
        this.speed = speed;
    }
}
