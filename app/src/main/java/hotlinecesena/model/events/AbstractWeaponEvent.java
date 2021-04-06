package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.items.Weapon;

/**
 *
 * Common class for events related to weapons.
 * @param <A> an interface that extends {@link Actor}.
 */
public abstract class AbstractWeaponEvent<A extends Actor> extends AbstractEvent<A> {

    private final Weapon weapon;

    protected AbstractWeaponEvent(final A source, final Weapon weapon) {
        super(source);
        this.weapon = weapon;
    }

    /**
     * Returns the weapon with which this event has been triggered.
     * @return the weapon used.
     */
    public final Weapon getWeapon() {
        return weapon;
    }
}
