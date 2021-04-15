package hotlinecesena.model.inventory;

import java.util.Map;

import hotlinecesena.model.entities.items.Item;
import hotlinecesena.model.entities.items.Weapon;

/**
 * Models a factory for {@link Inventory}.
 */
public interface InventoryFactory {

    /**
     * Creates an empty inventory.
     * @return an empty inventory.
     */
    Inventory createEmpty();

    /**
     * Creates an inventory containing one pistol and
     * a small amount of ammo.
     * @return an inventory containing one pistol and
     * a small amount of ammo.
     */
    Inventory createWithPistolOnly();

    /**
     * Creates an inventory with custom contents.
     * @param weapon the weapon to be added.
     * @param items the items to be added
     * @return an inventory with the aforementioned custom
     * contents.
     */
    Inventory createCustom(Weapon weapon, Map<Item, Integer> items);
}
