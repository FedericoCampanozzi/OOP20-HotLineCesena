package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.items.WeaponType;

/**
 * Notifies that an actor has reloaded their weapon.
 */
public class ReloadEvent extends AbstractItemEvent<WeaponType> {

    /**
     * Instantiates a new {@code ReloadEvent}.
     * @param <A> an interface that extends {@link Actor}.
     * @param source the Actor that triggered this event.
     * @param weaponType {@link WeaponType} of the weapon that was reloaded.
     */
    public <A extends Actor> ReloadEvent(final A source, final WeaponType weaponType) {
        super(source, weaponType);
    }
}
