package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;
import javafx.geometry.Point2D;

public class AbstractPickUpEvent<A extends Actor, I extends Enum<I>> extends AbstractItemEvent<A, I> {

    private final Point2D position;

    public AbstractPickUpEvent(final A source, final I itemType, final Point2D position) {
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
