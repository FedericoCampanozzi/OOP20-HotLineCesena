package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.items.WeaponType;

/**
 * Notifies that an actor has used their weapon.
 */
public final class AttackPerformedEvent extends AbstractItemEvent<WeaponType> {

    /**
     * Instantiates a new {@code AttackPerformedEvent}.
     * @param <A> an interface that extends {@link Actor}.
     * @param source the entity that triggered this event.
     * @param weaponType the {@link WeaponType} of the weapon used.
     */
    public <A extends Actor> AttackPerformedEvent(final A source, final WeaponType weaponType) {
        super(source, weaponType);
    }
}
