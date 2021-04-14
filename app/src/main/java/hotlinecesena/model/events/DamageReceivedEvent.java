package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;

/**
 *
 * Notifies that an actor has been damaged.

 */
public final class DamageReceivedEvent extends AbstractEvent {

    private final double damagePoints;

    /**
     * Instantiates a new {@code DamageReceivedEvent}.
     * @param <A> an interface that extends {@link Actor}.
     * @param source the Actor that triggered this event.
     * @param damagePoints the damage received by the Actor.
     */
    public <A extends Actor> DamageReceivedEvent(final A source, final double damagePoints) {
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
