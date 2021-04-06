package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;

/**
 *
 * Notifies that an Actor has picked up an item.
 *
 * @param <A> an interface extending {@link Actor}
 * @param <I> an enum
 */
public final class PickUpEvent<A extends Actor, I extends Enum<I>> extends AbstractItemEvent<A, I> {

    public PickUpEvent(final A source, final I itemType) {
        super(source, itemType);
    }
}
