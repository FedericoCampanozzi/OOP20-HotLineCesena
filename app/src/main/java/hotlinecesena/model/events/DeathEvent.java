package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;

/**
 * Notifies the death of an actor.
 * @param <A> an interface that extends {@link Actor}.
 */
public final class DeathEvent<A extends Actor> extends AbstractEvent<A> {

    /**
     * Instantiates a new {@code DeathEvent}.
     * @param source the Actor that triggered this event.
     */
    public DeathEvent(final A source) {
        super(source);
    }
}
