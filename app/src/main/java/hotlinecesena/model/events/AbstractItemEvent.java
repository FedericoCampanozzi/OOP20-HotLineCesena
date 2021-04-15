package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;

/**
 * Base class for all events related to items or weapons.
 * @param <I> an enum
 */
public abstract class AbstractItemEvent<I extends Enum<I>> extends AbstractEvent {

    private final I itemType;

    protected <A extends Actor> AbstractItemEvent(final A source, final I itemType) {
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
