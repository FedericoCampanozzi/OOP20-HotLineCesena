package hotlinecesena.model.events;

import hotlinecesena.model.entities.MovableEntity;
import javafx.geometry.Point2D;

/**
 *
 * Notifies that an entity has moved.
 * @param <M> an interface that extends {@link MovableEntity}.
 */
public final class MovementEvent<M extends MovableEntity> extends AbstractEvent<M> {

    private final Point2D newPos;

    public MovementEvent(final M source, final Point2D newPos) {
        super(source);
        this.newPos = newPos;
    }

    /**
     * Returns the source's new position.
     * @return the new position.
     */
    public Point2D getPosition() {
        return newPos;
    }
}
