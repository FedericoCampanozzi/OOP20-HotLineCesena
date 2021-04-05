package hotlinecesena.model.events;

import hotlinecesena.model.entities.Entity;
import hotlinecesena.model.entities.items.Weapon;

public abstract class AbstractWeaponEvent extends AbstractEvent {

    private final Weapon weapon;

    protected AbstractWeaponEvent(final Entity source, final Weapon weapon) {
        super(source);
        this.weapon = weapon;
    }

    public final Weapon getWeapon() {
        return weapon;
    }
}
