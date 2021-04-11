package hotlinecesena.model.entities;

import java.util.Objects;

import javax.annotation.Nonnull;

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
    protected AbstractMovableEntity(@Nonnull final Point2D position, final double angle, final double width,
            final double height, final double speed) {
        super(position, width, height);
        this.angle = angle;
        this.speed = speed;
    }

    /**
     *
     * @apiNote
     * No default implementation supplied.
     * Subclasses may require different movement logics.
     */
    @Override
    public abstract void move(@Nonnull Point2D direction);

    /**
     *
     * @throws NullPointerException if the given position or entity are null.
     */
    @Override
    public final boolean isCollidingWith(@Nonnull final Point2D newPosition, @Nonnull final Entity other) {
        Objects.requireNonNull(newPosition);
        final Point2D otherPos = Objects.requireNonNull(other).getPosition();
        return otherPos.getX() + other.getWidth() >= newPosition.getX()
                && otherPos.getY() + other.getHeight() >= newPosition.getY()
                && otherPos.getX() <= newPosition.getX() + this.getWidth()
                && otherPos.getY() <= newPosition.getY() + this.getHeight();
    }

    @Override
    public final double getAngle() {
        return angle;
    }

    /**
     * @apiNote
     * Template method depending on canInitiateRotation().
     */
    @Override
    public final void setAngle(final double angle) {
        if (this.angle != angle && this.canInitiateRotation()) {
            this.angle = angle;
            this.publish(new RotationEvent<>(this, angle));
        }
    }

    /**
     * Other conditions that need to be satisfied in order to begin
     * rotation.
     * @return {@code true} if this entity can rotate, {@code false} otherwise.
     */
    protected abstract boolean canInitiateRotation();

    @Override
    public final double getSpeed() {
        return speed;
    }

    @Override
    public final void setSpeed(final double speed) {
        this.speed = speed;
    }
}
