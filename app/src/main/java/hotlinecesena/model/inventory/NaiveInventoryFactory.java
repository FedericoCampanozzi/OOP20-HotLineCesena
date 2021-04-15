package hotlinecesena.model.inventory;

import java.util.Map;

import javax.annotation.Nonnull;

import hotlinecesena.model.entities.items.CollectibleType;
import hotlinecesena.model.entities.items.Item;
import hotlinecesena.model.entities.items.Weapon;
import hotlinecesena.model.entities.items.WeaponImpl;
import hotlinecesena.model.entities.items.WeaponType;

/**
 * Factory that uses {@link NaiveInventoryImpl}.
 */
public final class NaiveInventoryFactory implements InventoryFactory {

    @Override
    public Inventory createEmpty() {
        return new NaiveInventoryImpl();
    }

    @Override
    public Inventory createWithPistolOnly() {
        final Weapon pistol = new WeaponImpl(WeaponType.PISTOL);
        return new NaiveInventoryImpl(
                pistol,
                Map.of(CollectibleType.PISTOL_AMMO, CollectibleType.PISTOL_AMMO.getMaxStacks() / 3)
                );
    }

    @Override
    public Inventory createCustom(@Nonnull final Weapon weapon, @Nonnull final Map<Item, Integer> items) {
        return new NaiveInventoryImpl(weapon, items);
    }
}
