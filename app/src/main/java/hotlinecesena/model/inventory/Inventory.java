package hotlinecesena.model.inventory;

import java.util.Optional;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.items.CollectibleType;
import hotlinecesena.model.entities.items.Item;
import hotlinecesena.model.entities.items.Weapon;

/**
 *
 * Models an inventory for {@link Actor}s which may contain weapons and items.
 *
 */
public interface Inventory {

    /**
     * Adds a given quantity of an {@link CollectibleType} to this inventory.
     * @param collectible the {@code CollectibleType} to be added.
     * @param quantity quantity of the item to be added.
     */
    void add(CollectibleType collectible, int quantity);

    /**
     * Adds a {@link Weapon} to this inventory.
     * @param weapon the {@code Weapon} to be added.
     */
    void addWeapon(Weapon weapon);

    /**
     * Returns the quantity of a given {@link Item} present in this inventory.
     * @param item the item to look for in the inventory
     * @return the quantity of the given item that this inventory is holding.
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
     * Makes the actor equip the next weapon.
     */
    void switchToNextWeapon();

    /**
     * Makes the actor equip the previous weapon.
     */
    void switchToPreviousWeapon();

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
