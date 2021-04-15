package hotlinecesena.model.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import hotlinecesena.model.entities.items.CollectibleType;
import hotlinecesena.model.entities.items.Item;
import hotlinecesena.model.entities.items.Weapon;

/**
 * Very simple inventory capable of containing one {@link Weapon} and
 * a virtually unlimited number of {@link CollectibleType} items, that
 * is, items that cannot be actively used.
 */
public final class NaiveInventoryImpl implements Inventory {

    private Optional<Weapon> weapon = Optional.empty();
    private final Map<CollectibleType, Integer> collectibles = new HashMap<>();
    private double reloadTimeRemaining = 0.0;
    private Weapon reloadBuffer;

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
    public NaiveInventoryImpl(@Nullable final Weapon weapon,
            @Nonnull final Map<CollectibleType, Integer> collectibles) {
        this.weapon = Optional.ofNullable(weapon);
        this.collectibles.putAll(Objects.requireNonNull(collectibles));
    }

    /**
     * @throws NullPointerException if the given item is null.
     */
    @Override
    public void add(@Nonnull final CollectibleType collectible, final int quantity) {
        Objects.requireNonNull(collectible);
        final int ownedQuantity = collectibles.getOrDefault(collectible, 0);
        final int newQuantity = quantity + ownedQuantity;
        collectibles.put(
                collectible,
                newQuantity > collectible.getMaxStacks() ? collectible.getMaxStacks() : newQuantity);
    }

    @Override
    public void addWeapon(@Nonnull final Weapon weapon) {
        this.weapon = Optional.of(weapon);
    }

    /**
     * @throws NullPointerException if the given item is null.
     */
    @Override
    public int getQuantityOf(@Nonnull final Item item) {
        Objects.requireNonNull(item);
        if (item instanceof Weapon) {
            final Weapon w = (Weapon) item;
            return weapon.isPresent() && weapon.get().getWeaponType() == w.getWeaponType() ? 1 : 0;
        } else {
            return collectibles.getOrDefault(item, 0);
        }
    }

    @Override
    public Optional<Weapon> getWeapon() {
        return weapon;
    }

    /**
     * @implNote Not implemented.
     */
    @Override
    public void switchToNextWeapon() {
    }

    /**
     * @implNote Not implemented.
     */
    @Override
    public void switchToPreviousWeapon() {
    }

    /**
     * @implSpec
     * Based on time.
     */
    @Override
    public void reloadWeapon() {
        weapon.ifPresent(weapon -> {
            if (!this.isReloading() && weapon.getCurrentAmmo() < weapon.getMagazineSize()) {
                final int ammoOwned = collectibles.getOrDefault(weapon.getCompatibleAmmunition(), 0);
                if (ammoOwned > 0) {
                    /*
                     * As a precaution, save the current weapon instance to
                     * later check if it has changed or disappeared.
                     */
                    reloadBuffer = weapon;
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
                /*
                 * If the weapon hasn't changed or disappeared for some
                 * obscure reason, finish reloading it.
                 * Otherwise, reset everything.
                 */
                weapon.filter(w -> w == reloadBuffer)
                .ifPresent(w -> {
                    final int ammoNeeded = w.getMagazineSize() - w.getCurrentAmmo();
                    final int ammoOwned = collectibles.getOrDefault(w.getCompatibleAmmunition(), 0);
                    if (ammoOwned > ammoNeeded) {
                        w.reload(ammoNeeded);
                        collectibles.put(
                                w.getCompatibleAmmunition(),
                                ammoOwned - ammoNeeded);
                    } else {
                        w.reload(ammoOwned);
                        collectibles.put(
                                w.getCompatibleAmmunition(),
                                0);
                    }
                });
                reloadBuffer = null;
            }
        }
    }
}
