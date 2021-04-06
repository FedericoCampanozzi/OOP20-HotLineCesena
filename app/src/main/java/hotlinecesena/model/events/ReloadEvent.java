package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.items.Weapon;

/**
 *
 * Notifies that an actor has reloaded their weapon.
 * @param <A> an interface that extends {@link Actor}.
 */
public class ReloadEvent<A extends Actor> extends AbstractWeaponEvent<A> {

    public ReloadEvent(final A source, final Weapon weapon) {
        super(source, weapon);
    }
}
