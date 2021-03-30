package hotlinecesena.model.events;

import hotlinecesena.model.entities.Entity;
import hotlinecesena.model.entities.items.Weapon;

public class AttackEvent<E extends Entity> extends AbstractEvent<E> {
    
    private Weapon weaponUsed;

    protected AttackEvent(final E source, final Weapon weaponUsed) {
        super(source);
        this.weaponUsed = weaponUsed;
    }
    
    public Weapon getWeaponUsed() {
        return this.weaponUsed;
    }
}
