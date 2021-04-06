package hotlinecesena.model.inventory;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import hotlinecesena.model.entities.items.Item;
import hotlinecesena.model.entities.items.Weapon;

/**
 *
 * Very simple inventory capable of containing one weapon and a virtually
 * unlimited number of collectible items, that is, items that cannot be
 * actively used.
 *
 */
public final class NaiveInventoryImpl implements Inventory {

    private Optional<Weapon> weapon = Optional.empty();
    private Map<Item, Integer> collectibles;
    private double reloadTimeRemaining = 0.0;
    private int ammoForReloading;

    /**
     * Instantiates a {@code NaiveInventoryImpl} with no collectible items and no weapon.
     */
    public NaiveInventoryImpl() {
    }

    /**
     * Instantiates a {@code NaiveInventoryImpl} with a weapon and an arbitrary quantity
     * of collectible items.
     * @param weapon the starting weapon to be equipped in this inventory. Can be {@code null}.
     * @param collectibles the starting quantity of collectibles. Can be an empty {@link Map}.
     * @throws NullPointerException if the given {@code collectibles} is null.
     */
    public NaiveInventoryImpl(final Weapon weapon, final Map<Item, Integer> collectibles) {
        this.weapon = Optional.ofNullable(weapon);
        this.collectibles = Objects.requireNonNull(collectibles);
    }

    @Override
    public void add(final Item item, final int quantity) {
        Objects.requireNonNull(item);
        if (item instanceof Weapon) {
            final Weapon w = (Weapon) item;
            weapon.ifPresent(this::drop);
            weapon = Optional.of(w);
        } else {
            final int ownedQuantity = collectibles.getOrDefault(item, 0);
            final int newQuantity = quantity + ownedQuantity;
            collectibles.put(item, newQuantity > item.getMaxStacks() ? item.getMaxStacks() : newQuantity);
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

    /**
     * @implSpec
     * Depends on time.
     *
     */
    @Override
    public void reloadWeapon() {
        weapon.ifPresent(weapon -> {
            if (!this.isReloading()) {
                final int ammoOwned = collectibles.getOrDefault(weapon.getCompatibleAmmunition(), 0);
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

    /**
     * @implSpec
     * Updates the remaining reload time.
     */
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
                    collectibles.put(w.getCompatibleAmmunition(), ammoForReloading - ammoNeeded);
                } else {
                    w.reload(ammoForReloading);
                    collectibles.put(w.getCompatibleAmmunition(), 0);
                }
                ammoForReloading = 0;
            }
        }
    }
}
