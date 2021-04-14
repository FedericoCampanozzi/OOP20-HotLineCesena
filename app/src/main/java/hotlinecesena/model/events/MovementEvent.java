package hotlinecesena.model.events;

import hotlinecesena.model.entities.MovableEntity;
import javafx.geometry.Point2D;

/**
 *
 * Notifies that an entity has moved.
 */
public final class MovementEvent extends AbstractEvent {

    private final Point2D newPos;

    /**
     * Instantiates a new {@code MovementEvent}.
     * @param <M> an interface that extends {@link MovableEntity}.
     * @param source the MovableEntity that triggered this event.
     * @param newPos new position of the MovableEntity.
     */
    public <M extends MovableEntity> MovementEvent(final M source, final Point2D newPos) {
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
