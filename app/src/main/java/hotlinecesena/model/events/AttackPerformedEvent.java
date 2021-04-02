package hotlinecesena.model.events;

import hotlinecesena.model.entities.Entity;
import hotlinecesena.model.entities.items.Weapon;

public class AttackPerformedEvent extends AbstractEvent {
    
    private Weapon weaponUsed;

    protected AttackPerformedEvent(final Entity source, final Weapon weaponUsed) {
        super(source);
        this.weaponUsed = weaponUsed;
    }
    
    public Weapon getWeaponUsed() {
        return this.weaponUsed;
    }
}
