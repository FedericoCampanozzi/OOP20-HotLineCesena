package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.items.WeaponType;

/**
 *
 * Notifies that an actor has reloaded their weapon.
 *
 * @param <A> an interface that extends {@link Actor}.
 */
public class ReloadEvent<A extends Actor> extends AbstractItemEvent<A, WeaponType> {

    /**
     * Instantiates a new {@code ReloadEvent}.
     * @param source the Actor that triggered this event.
     * @param weaponType {@link WeaponType} of the weapon that was reloaded.
     */
    public ReloadEvent(final A source, final WeaponType weaponType) {
        super(source, weaponType);
    }
}
