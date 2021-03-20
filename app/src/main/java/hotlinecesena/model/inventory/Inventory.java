package hotlinecesena.model.inventory;

import java.util.List;
import java.util.Optional;

import hotlinecesena.model.entities.items.Item;
import hotlinecesena.model.entities.items.Weapon;

public interface Inventory {
    
    void addItem(Item item, int index);
    
    void addWeapon(Weapon w, int index);
    
    List<Optional<Item>> getOwnedItems();
    
    List<Optional<Weapon>> getOwnedWeapons();
    
    Optional<Weapon> getEquippedWeapon();
    
    void reloadWeapon();

    void removeItem(int index, int quantity);
    
    void removeWeapon(int index);

    void dropItem(int index);

    void dropWeapon(int index);
}
