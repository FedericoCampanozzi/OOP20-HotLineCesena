package hotlinecesena.model.events;

import hotlinecesena.model.entities.Entity;
import hotlinecesena.model.entities.items.Weapon;

public class ReloadEvent extends AbstractWeaponEvent {

    public ReloadEvent(final Entity source, final Weapon weapon) {
        super(source, weapon);
    }
}
