package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.items.Item;
import hotlinecesena.model.entities.items.ItemsType;
import javafx.geometry.Point2D;

/**
 *
 * Notifies that an Actor has picked up an item.
 *
 * @param <A> an interface extending {@link Actor}
 */
public final class ItemPickUpEvent<A extends Actor> extends AbstractPickUpEvent<A, ItemsType> {

    /**
     * Instantiates a new {@code ItemPickUpEvent}.
     * @param source the Actor that triggered this event.
     * @param itemType the {@link ItemsType} of the {@link Item}
     * @param position the point on the game map in which the item
     * was picked up.
     */
    public ItemPickUpEvent(final A source, final ItemsType itemType, final Point2D position) {
        super(source, itemType, position);
    }
}
