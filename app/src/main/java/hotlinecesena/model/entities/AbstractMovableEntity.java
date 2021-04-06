package hotlinecesena.model.entities;

import java.util.Objects;

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
     * @implNote
     * Template method.
     *
     * @throws NullPointerException if the supplied direction is null.
     */
    @Override
    public void move(final Point2D direction) {
        Objects.requireNonNull(direction);
        if (!direction.equals(Point2D.ZERO) && this.canInitiateMovement(direction)) {
            final Point2D oldPos = this.getPosition();
            final Point2D newPos = oldPos.add(direction.multiply(speed));
            // TODO
            // if (!this.isColliding(newPos)) {
            this.setPosition(newPos);
            this.publish(new MovementEvent<>(this, newPos));
            this.onSuccessfulMovement();
            //            }
        }
    }

    /**
     * Other conditions that need to be satisfied in order to begin
     * movement.
     * @param direction the movement direction.
     * @return {@code true} if this entity can move, {@code false} otherwise.
     */
    protected abstract boolean canInitiateMovement(Point2D direction);

    /**
     * Actions to be performed right after this entity has moved.
     * Implementation is not mandatory.
     */
    protected abstract void onSuccessfulMovement();

    //private boolean isColliding(final Point2D newPos) {
    //    return this.getGameMaster().getPhysicsCollision().getObstacles()
    //            .stream()
    //            .anyMatch(o -> o.intersects(newPos.getX(), newPos.getY(), this.getWidth(), this.getHeight()));
    //}

    //protected abstract void onObstacleCollision();

    //protected abstract void onActorCollision();

    @Override
    public final double getAngle() {
        return angle;
    }

    /**
     * @implNote
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
