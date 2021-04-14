package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;
import javafx.geometry.Point2D;

/**
 * Base class for all events related to item or weapon pickups.
 * @param <I> an enum
 */
public abstract class AbstractPickUpEvent<I extends Enum<I>> extends AbstractItemEvent<I> {

    private final Point2D position;

    public <A extends Actor> AbstractPickUpEvent(final A source, final I itemType, final Point2D position) {
        super(source, itemType);
        this.position = position;
    }

    /**
     * Returns the coordinates of the location in which the item or weapon
     * was picked up.
     * @return the coordinates of the location in which the item or weapon
     * was picked up.
     */
    public Point2D getItemPosition() {
        return this.position;
    }
}
