package hotlinecesena.model.events;

import hotlinecesena.model.entities.MovableEntity;
import javafx.geometry.Point2D;

public class MovementEvent<M extends MovableEntity> extends AbstractEvent<M> {

    private final Point2D newPos;

    public MovementEvent(final M source, final Point2D newPos) {
        super(source);
        this.newPos = newPos;
    }

    public Point2D getPosition() {
        return this.newPos;
    }
}
