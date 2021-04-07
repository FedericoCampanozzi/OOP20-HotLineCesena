package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.items.WeaponType;

/**
 *
 * Notifies that an actor has used their weapon.
 * @param <A> an interface that extends {@link Actor}.
 */
public final class AttackPerformedEvent<A extends Actor> extends AbstractItemEvent<A, WeaponType> {

    /**
     * Instantiates a new {@code AttackPerformedEvent}.
     * @param source the entity that triggered this event.
     * @param weaponType the {@link WeaponType} of the weapon used.
     */
    public AttackPerformedEvent(final A source, final WeaponType weaponType) {
        super(source, weaponType);
    }
}
