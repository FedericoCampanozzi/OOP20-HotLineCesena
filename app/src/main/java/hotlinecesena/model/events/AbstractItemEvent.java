package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;

public abstract class AbstractItemEvent<A extends Actor, I extends Enum<I>> extends AbstractEvent<A> {

    private final I itemType;

    public AbstractItemEvent(final A source, final I itemType) {
        super(source);
        this.itemType = itemType;
    }

    public final I getItemType() {
        return this.itemType;
    }
}
