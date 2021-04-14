package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;

/**
 * Notifies the death of an actor.
 */
public final class DeathEvent extends AbstractEvent {

    /**
     * Instantiates a new {@code DeathEvent}.
     * @param <A> an interface that extends {@link Actor}.
     * @param source the Actor that triggered this event.
     */
    public <A extends Actor> DeathEvent(final A source) {
        super(source);
    }
}
