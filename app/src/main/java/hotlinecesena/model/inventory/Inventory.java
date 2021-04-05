package hotlinecesena.model.inventory;

import java.util.Optional;

import hotlinecesena.model.entities.items.Item;
import hotlinecesena.model.entities.items.Weapon;

/**
 *
 * Also needs to define a reloading time.
 *
 */
public interface Inventory {

    void add(Item item, int quantity);

    Optional<Weapon> getWeapon();

    void reloadWeapon();

    boolean isReloading();

    void update(double timeElapsed);
}
