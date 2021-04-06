package hotlinecesena.model.events;

import hotlinecesena.model.entities.Entity;
import javafx.geometry.Point2D;

public final class MovementEvent extends AbstractEvent {

    private final Point2D newPos;

    public MovementEvent(final Entity source, final Point2D newPos) {
        super(source);
        this.newPos = newPos;
    }

    public Point2D getPosition() {
        return newPos;
    }
}
