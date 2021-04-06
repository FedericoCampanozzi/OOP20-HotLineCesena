package hotlinecesena.model.entities;

import java.util.Objects;

import hotlinecesena.model.entities.actors.DirectionList;
import hotlinecesena.model.events.MovementEvent;
import hotlinecesena.model.events.RotationEvent;
import javafx.geometry.Point2D;

/**
 *
 * Template for generic entities capable of moving and rotating.
 *
 */
public abstract class AbstractMovableEntity extends AbstractEntity implements MovableEntity {

    private double angle;
    private double speed;

    /**
     * @param position starting position in which this entity will be located.
     * @param angle starting angle that this entity will face.
     * @param width this entity's width.
     * @param height this entity's height.
     * @param speed the speed at which this entity will move.
     * @throws NullPointerException if given position is null.
     */
    protected AbstractMovableEntity(final Point2D position, final double angle, final double width,
            final double height, final double speed) {
        super(position, width, height);
        this.angle = angle;
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
            // TODO foreach obstacle...
            // if Utilities.intersect(new BoundingBox(newPos.getX(), newPos.getY(), getWidth(), getHeight()), null)...
            // this.onCollision(); else

            this.setPosition(newPos);
            this.publish(new MovementEvent<>(this, newPos));
        }
    }

    //protected abstract void onObstacleCollision();

    //protected abstract void onActorCollision();

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
            this.publish(new RotationEvent<>(this, angle));
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
