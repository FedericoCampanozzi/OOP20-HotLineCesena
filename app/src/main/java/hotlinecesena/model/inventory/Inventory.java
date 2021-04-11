package hotlinecesena.model.inventory;

import java.util.Optional;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.items.Item;
import hotlinecesena.model.entities.items.Weapon;

/**
 *
 * Models an inventory for {@link Actor}s which may contain weapons and/or items.
 *
 */
public interface Inventory {

    /**
     * Adds an {@link Item} to this inventory.
     * @param item the {@code Item} to be added.
     * @param quantity quantity of the item to be added.
     */
    void add(Item item, int quantity);

    /**
     * Returns the quantity of a given {@link Item} present in this inventory.
     * @param item the item to look for in the inventory
     * @return the quantity of the given item, or {@code 0} if it's not present.
     */
    int getQuantityOf(Item item);

    /**
     * Returns the weapon currently equipped in this inventory.
     * @return an {@link Optional} containing the equipped weapon, if
     * present, otherwise returns an empty optional.
     */
    Optional<Weapon> getWeapon();

    /**
     * Handles reloading for the currently equipped weapon.
     */
    void reloadWeapon();

    /**
     * Checks to see if the reload action for the equipped weapon is currently ongoing.
     * @return {@code true} if this inventory is currently reloading the equipped
     * weapon, {@code false} otherwise.
     */
    boolean isReloading();

    /**
     * Updates features of this inventory that depend on time.
     * @param timeElapsed the time elapsed since the last update.
     */
    void update(double timeElapsed);
}
