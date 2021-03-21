package hotlinecesena.model.entities;

import javafx.geometry.Point2D;

public abstract class AbstractMovableEntity extends AbstractEntity implements MovableEntity {
    
    private double speed;

    protected AbstractMovableEntity(final Point2D pos, final double angle, final double speed) {
        super(pos, angle);
        this.speed = speed;
    }

    @Override
    public void move(Point2D direction) {
        final Point2D oldPos = this.getPosition();
        this.setPosition(oldPos.add(direction.multiply(this.speed)));
    }

    @Override
    public double getSpeed() {
        return this.speed;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

}
