package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.items.Weapon;

/**
 *
 * Notifies that an actor has used their weapon.
 * @param <A> an interface that extends {@link Actor}.
 */
public final class AttackPerformedEvent<A extends Actor> extends AbstractWeaponEvent<A> {

    public AttackPerformedEvent(final A source, final Weapon weapon) {
        super(source, weapon);
    }
}
