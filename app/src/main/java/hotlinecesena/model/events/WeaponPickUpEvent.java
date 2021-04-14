package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.items.Weapon;
import hotlinecesena.model.entities.items.WeaponType;
import javafx.geometry.Point2D;

/**
 *
 * Notifies that an Actor has picked up a weapon.
 *
 * @param <A> an interface extending {@link Actor}
 */
public class WeaponPickUpEvent<A extends Actor> extends AbstractPickUpEvent<A, WeaponType> {

    /**
     * Instantiates a new {@code WeaponPickUpEvent}.
     * @param source the Actor that triggered this event.
     * @param weaponType the {@link WeaponType} of the {@link Weapon}
     * @param position the point on the game map in which the weapon
     * was picked up.
     */
    public WeaponPickUpEvent(final A source, final WeaponType weaponType, final Point2D position) {
        super(source, weaponType, position);
    }
}
