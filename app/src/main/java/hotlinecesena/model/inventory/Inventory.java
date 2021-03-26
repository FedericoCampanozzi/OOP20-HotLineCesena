package hotlinecesena.model.inventory;

import java.util.Optional;

import hotlinecesena.model.entities.items.Item;

/**
 * 
 * Also needs to define a reloading time.
 *
 */
public interface Inventory {
    
    void add(Item item);
    
    Optional<Item> getEquipped();
    
    void reloadEquipped();
    
    boolean isReloading();
}
