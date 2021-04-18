package hotlinecesena.model.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import hotlinecesena.model.entities.items.CollectibleType;
import hotlinecesena.model.entities.items.Item;
import hotlinecesena.model.entities.items.Weapon;

/**
 * <p>
 * Very simple inventory capable of containing one {@link Weapon} and
 * a virtually unlimited number of {@link CollectibleType} items.
 * </p>
 * Trying to add items which are not instance of {@code Weapon} or
 * {@code CollectibleType} will result in an {@link IllegalArgumentException}.
 */
public final class NaiveInventoryImpl implements Inventory {

    private static final String ERROR_MSG = "This inventory only supports Weapon and CollectibleType items";
    private Optional<Weapon> weapon = Optional.empty();
    private final Map<Item, Integer> collectibles = new HashMap<>();
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
     * @param items the starting quantity of collectibles. Can be an empty {@link Map}.
     * @throws NullPointerException if the given {@code items} is null.
     * @throws IllegalArgumentException if the given {@code items} contains objects
     * which are not instance of {@link CollectibleType}.
     */
    public NaiveInventoryImpl(@Nullable final Weapon weapon, @Nonnull final Map<Item, Integer> items) {
        this.weapon = Optional.ofNullable(weapon);
        Objects.requireNonNull(items);
        if (items.entrySet().stream()
                .map(Entry::getKey)
                .anyMatch(item -> !(item instanceof CollectibleType))) {
            throw new IllegalArgumentException(ERROR_MSG);
        }
        items.forEach(this::add);
    }

    /**
     * @throws NullPointerException if the given item is null.
     * @throws IllegalArgumentException if the given item is not a
     * {@link Weapon} or a {@link CollectibleType}.
     */
    @Override
    public void add(@Nonnull final Item item, final int quantity) {
        Objects.requireNonNull(item);
        if (item instanceof Weapon) {
            weapon = Optional.of((Weapon) item);
        } else if (item instanceof CollectibleType) {
            final int ownedQuantity = collectibles.getOrDefault(item, 0);
            final int newQuantity = quantity + ownedQuantity;
            collectibles.put(
                    item,
                    newQuantity > item.getMaxStacks() ? item.getMaxStacks() : newQuantity);
        } else {
            throw new IllegalArgumentException(ERROR_MSG);
        }
    }

    /**
     * @throws NullPointerException if the given item is null.
     */
    @Override
    public int getQuantityOf(@Nonnull final Item item) {
        Objects.requireNonNull(item);
        if (item instanceof Weapon) {
            final Weapon w = (Weapon) item;
            return weapon.isPresent() && weapon.orElseThrow().getWeaponType() == w.getWeaponType() ? 1 : 0;
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
