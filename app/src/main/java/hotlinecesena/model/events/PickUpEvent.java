package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;

/**
 *
 * Notifies that an Actor has picked up an item.
 *
 * @param <A> an interface extending {@link Actor}
 * @param <I> an enum
 */
public final class PickUpEvent<A extends Actor, I extends Enum<I>> extends AbstractEvent<A> {

    private final I itemType;

    public PickUpEvent(final A source, final I itemType) {
        super(source);
        this.itemType = itemType;
    }

    public I getItemType() {
        return this.itemType;
    }
}
