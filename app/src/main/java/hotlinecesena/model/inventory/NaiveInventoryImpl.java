package hotlinecesena.model.inventory;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import hotlinecesena.model.entities.items.Item;
import hotlinecesena.model.entities.items.Weapon;

public class NaiveInventoryImpl implements Inventory {

    private double reloadTimeRemaining = 0.0;
    private final Optional<Weapon> weapon = Optional.empty();
    private Map<Item, Integer> stackables;
    private int ammoForReloading;

    @Override
    public void add(final Item item, final int quantity) {
        Objects.requireNonNull(item);
        //        if (isWeapon(item)) {
        //            if (this.equippable.isPresent()) {
        //                this.drop(this.equippable.get());
        //            }
        //            this.equippable = Optional.of(item);
        //        } else {
        //
        //        }
        final int ownedQuantity = stackables.getOrDefault(item, 0);
        stackables.put(item, quantity + ownedQuantity);
    }

    @Override
    public Optional<Weapon> getWeapon() {
        return weapon;
    }

    @Override
    public void reloadWeapon() {
        if (weapon.isPresent() && !this.isReloading()) {
            final Weapon w = weapon.get();
            final int ammoOwned = stackables.getOrDefault(w.getCompatibleAmmunition(), 0);
            if (ammoOwned > 0) {
                reloadTimeRemaining = w.getReloadTime();
            }
        }
    }

    @Override
    public boolean isReloading() {
        return reloadTimeRemaining > 0.0;
    }

    @Override
    public void update(final double timeElapsed) {
        this.updateReloading(timeElapsed);
    }

    private void updateReloading(final double timeElapsed) {
        if (this.isReloading()) {
            reloadTimeRemaining -= timeElapsed;
            if (reloadTimeRemaining <= 0.0) {
                final int ammoNeeded = weapon.get().getMagazineSize() - weapon.get().getCurrentAmmo();
                if (ammoForReloading > ammoNeeded) {
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
