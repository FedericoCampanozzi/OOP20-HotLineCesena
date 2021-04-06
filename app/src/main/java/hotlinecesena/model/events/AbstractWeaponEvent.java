package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.items.WeaponType;

/**
 *
 * Common class for events related to weapons.
 * @param <A> an interface that extends {@link Actor}.
 */
public abstract class AbstractWeaponEvent<A extends Actor> extends AbstractEvent<A> {

    private final WeaponType weaponType;

    protected AbstractWeaponEvent(final A source, final WeaponType weaponType) {
        super(source);
        this.weaponType = weaponType;
    }

    /**
     * Returns the {@link WeaponType} relative to the Weapon
     * with which this event has been triggered.
     * @return the weapon used.
     */
    public final WeaponType getWeapon() {
        return weaponType;
    }
}
