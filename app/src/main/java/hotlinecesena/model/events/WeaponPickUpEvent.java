package hotlinecesena.model.events;

import java.util.Optional;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.items.Weapon;
import hotlinecesena.model.entities.items.WeaponType;
import javafx.geometry.Point2D;

/**
 * Notifies that an Actor has picked up a weapon.
 */
public class WeaponPickUpEvent extends AbstractPickUpEvent<WeaponType> {

    private final Optional<WeaponType> oldWeapon;

    /**
     * Instantiates a new {@code WeaponPickUpEvent}.
     * @param <A> an interface extending {@link Actor}
     * @param source the Actor that triggered this event.
     * @param newWeapon the {@link WeaponType} of the {@link Weapon}
     * picked up by the actor.
     * @param oldWeapon the weapon held by the actor before picking up
     * the new one.
     * @param position the point on the game map in which the weapon
     * was picked up.
     */
    public <A extends Actor> WeaponPickUpEvent(final A source, final WeaponType newWeapon,
            final WeaponType oldWeapon, final Point2D position) {
        super(source, newWeapon, position);
        this.oldWeapon = Optional.ofNullable(oldWeapon);
    }

    /**
     * Returns, if present, the weapon held by the actor
     * before picking up the new one.
     * @return an Optional containing the weapon held by the actor
     * before picking up the new one,.
     */
    public Optional<WeaponType> getOldWeapon() {
        return oldWeapon;
    }
}
