package hotlinecesena.model.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

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
    private final Map<Item, Integer> collectibles = new HashMap<>();
    private double reloadTimeRemaining = 0.0;
    private Pair<Weapon, Integer> reloadBuffer;

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
    public NaiveInventoryImpl(@Nullable final Weapon weapon, @Nonnull final Map<Item, Integer> collectibles) {
        this.weapon = Optional.ofNullable(weapon);
        this.collectibles.putAll(Objects.requireNonNull(collectibles));
    }

    /**
     * @implSpec If the given {@code item} is an instance of
     * {@link Weapon}, the {@code quantity} parameter will
     * be ignored.
     * @throws NullPointerException if the given item is null.
     */
    @Override
    public void add(@Nonnull final Item item, final int quantity) {
        Objects.requireNonNull(item);
        if (item instanceof Weapon) {
            weapon = Optional.of((Weapon) item);
        } else {
            final int ownedQuantity = collectibles.getOrDefault(item, 0);
            final int newQuantity = quantity + ownedQuantity;
            collectibles.put(item, newQuantity > item.getMaxStacks() ? item.getMaxStacks() : newQuantity);
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
                    reloadBuffer = new ImmutablePair<>(weapon, ammoOwned);
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
                weapon.filter(w -> w == reloadBuffer.getLeft())
                .ifPresent(w -> {
                    final int ammoNeeded = w.getMagazineSize() - w.getCurrentAmmo();
                    if (reloadBuffer.getRight() > ammoNeeded) {
                        w.reload(ammoNeeded);
                        collectibles.put(w.getCompatibleAmmunition(), reloadBuffer.getRight() - ammoNeeded);
                    } else {
                        w.reload(reloadBuffer.getRight());
                        collectibles.put(w.getCompatibleAmmunition(), 0);
                    }
                });
                reloadBuffer = null;
            }
        }
    }
}
