package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.items.Item;
import hotlinecesena.model.entities.items.ItemsType;
import hotlinecesena.model.entities.items.WeaponType;

/**
 *
 * Notifies that an Actor has picked up an item or a weapon.
 *
 * @param <A> an interface extending {@link Actor}
 * @param <I> an enum
 */
public final class PickUpEvent<A extends Actor, I extends Enum<I>> extends AbstractItemEvent<A, I> {

    /**
     * Instantiates a new {@code PickUpEvent}.
     * @param source the Actor that triggered this event.
     * @param itemType type of the {@link Item} that was picked up.
     * Can be either an {@link ItemsType}, a {@link WeaponType} or any
     * enum related to game items.
     */
    public PickUpEvent(final A source, final I itemType) {
        super(source, itemType);
    }
}
