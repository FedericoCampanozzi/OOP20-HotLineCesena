package hotlinecesena.model.inventory;

import java.util.Map;
import java.util.Optional;

import hotlinecesena.model.entities.items.Item;
import hotlinecesena.model.entities.items.Weapon;

public class NaiveInventoryImpl implements Inventory {

    private double reloadTimeRemaining = 0.0;
    private Optional<Weapon> weapon = Optional.empty();
    private Map<Item, Integer> stackables;
    private int ammoForReloading;

    @Override
    public void add(Item item) {
//        if (isWeapon(item)) {
//            if (this.equippable.isPresent()) {
//                this.drop(this.equippable.get());
//            }
//            this.equippable = Optional.of(item);
//        } else {
//            
//        }
        final int oldQuantity = this.stackables.containsKey(item) ? this.stackables.get(item) : 0;
        this.stackables.put(item, 1 + oldQuantity);
    }

    @Override
    public Optional<Weapon> getWeapon() {
        return this.weapon;
    }

    @Override
    public void reloadWeapon() {
        if (this.weapon.isPresent() && !this.isReloading()) {
            final Weapon w = this.weapon.get();
            final int ammoOwned = this.stackables.getOrDefault(w.getCompatibleAmmunition(), 0);
            if (ammoOwned > 0) {
                this.reloadTimeRemaining = w.getReloadTime();
            }
        }
    }

    @Override
    public boolean isReloading() {
        return this.reloadTimeRemaining > 0.0;
    }

    @Override
    public void update(double timeElapsed) {
        this.updateReloading(timeElapsed);
    }
    
    private void updateReloading(double timeElapsed) {
        if (this.isReloading()) {
            reloadTimeRemaining -= timeElapsed;
            if (reloadTimeRemaining <= 0.0) {
                final int ammoNeeded = weapon.get().getMagazineSize() - weapon.get().getCurrentAmmo();
                if (this.ammoForReloading > ammoNeeded) {
                    weapon.get().reload(ammoNeeded);
                    stackables.put(weapon.get().getCompatibleAmmunition(), ammoForReloading - ammoNeeded);
                } else {
                    weapon.get().reload(ammoForReloading);
                    stackables.put(weapon.get().getCompatibleAmmunition(), 0);
                }
                ammoForReloading = 0;
            }
        }
    }
}
