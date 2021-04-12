package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;

/**
 *
 * Notifies that an actor has been damaged.
 * @param <A> an interface that extends {@link Actor}.
 */
public final class DamageReceivedEvent<A extends Actor> extends AbstractEvent<A> {

    private final double damagePoints;

    /**
     * Instantiates a new {@code DamageReceivedEvent}.
     * @param source the Actor that triggered this event.
     * @param damagePoints the damage received by the Actor.
     */
    public DamageReceivedEvent(final A source, final double damagePoints) {
        super(source);
        this.damagePoints = damagePoints;
    }

    /**
     * Returns the damage received by the actor.
     * @return the damage points received.
     */
    public double getDamage() {
        return damagePoints;
    }
}
