package hotlinecesena.model.inventory;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import hotlinecesena.model.entities.items.Item;
import hotlinecesena.model.entities.items.Weapon;

public final class NaiveInventoryImpl implements Inventory {

    private Optional<Weapon> weapon = Optional.empty();
    private Map<Item, Integer> stackables;
    private double reloadTimeRemaining = 0.0;
    private int ammoForReloading;

    @Override
    public void add(final Item item, final int quantity) {
        Objects.requireNonNull(item);
        if (item instanceof Weapon) {
            final Weapon w = (Weapon) item;
            weapon.ifPresent(this::drop);
            weapon = Optional.of(w);
        } else {
            final int ownedQuantity = stackables.getOrDefault(item, 0);
            final int newQuantity = quantity + ownedQuantity;
            stackables.put(item, newQuantity > item.getMaxStacks() ? item.getMaxStacks() : newQuantity);
        }
    }

    private void drop(final Item item) {
        //TODO
        weapon = Optional.empty();
    }

    @Override
    public Optional<Weapon> getWeapon() {
        return weapon;
    }

    @Override
    public void reloadWeapon() {
        weapon.ifPresent(weapon -> {
            if (!this.isReloading()) {
                final int ammoOwned = stackables.getOrDefault(weapon.getCompatibleAmmunition(), 0);
                if (ammoOwned > 0) {
                    reloadTimeRemaining = weapon.getReloadTime();
                }
            }
        });
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
                final Weapon w = weapon.get();
                final int ammoNeeded = w.getMagazineSize() - w.getCurrentAmmo();
                if (ammoForReloading > ammoNeeded) {
                    w.reload(ammoNeeded);
                    stackables.put(w.getCompatibleAmmunition(), ammoForReloading - ammoNeeded);
                } else {
                    w.reload(ammoForReloading);
                    stackables.put(w.getCompatibleAmmunition(), 0);
                }
                ammoForReloading = 0;
            }
        }
    }
}
