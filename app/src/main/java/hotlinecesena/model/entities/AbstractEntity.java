package hotlinecesena.model.entities;

import javafx.geometry.Point2D;

public abstract class AbstractEntity implements Entity {
    
    private Point2D position;
    
    protected AbstractEntity(final Point2D pos) {
        this.position = pos;
    }

    @Override
    public Point2D getPosition() {
        return this.position;
    }
    
    @Override
    public void setPosition(final Point2D pos) {
        this.position = pos;
    }

}
