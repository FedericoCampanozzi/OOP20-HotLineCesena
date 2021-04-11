package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;

/**
 *
 * Base class for all events related to items or weapons.
 *
 * @param <A> an interface extending {@link Actor}
 * @param <I> an enum
 */
public abstract class AbstractItemEvent<A extends Actor, I extends Enum<I>> extends AbstractEvent<A> {

    private final I itemType;

    public AbstractItemEvent(final A source, final I itemType) {
        super(source);
        this.itemType = itemType;
    }

    /**
     * Returns an enum constant representing the item or weapon
     * related to this event.
     * @return an enum constant
     */
    public final I getItemType() {
        return this.itemType;
    }


}
