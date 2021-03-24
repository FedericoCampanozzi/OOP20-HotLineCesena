package hotlinecesena.model.inventory;

import java.util.Optional;

import hotlinecesena.model.entities.items.Item;

/**
 * 
 * Needs to define a reloading time.
 *
 */
public interface Inventory {
    
    void add(Item item);
    
    Optional<Item> getUsable();
    
    Optional<Item> getEquipped();
    
    void reloadEquipped();

    void dropUsable();
    
    void dropEquipped();
    
    boolean isReloading();
}
