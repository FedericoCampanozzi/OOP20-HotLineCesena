package hotlinecesena.model.entities;

import javafx.geometry.Point2D;

public abstract class AbstractMovableEntity extends AbstractEntity implements MovableEntity {
    
    private double speed;

    protected AbstractMovableEntity(final Point2D pos, final double angle, final double speed) {
        super(pos, angle);
        this.speed = speed;
    }

    /**
     * Can be overridden by subclasses if a different movement logic is required.
     */
    @Override
    public void move(Point2D direction) {
        final Point2D oldPos = this.getPosition();
        this.setPosition(oldPos.add(direction.multiply(this.speed)));
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
