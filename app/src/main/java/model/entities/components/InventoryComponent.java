package model.entities.components;

import java.util.List;
import java.util.Optional;

import model.entities.items.Item;
import model.entities.items.Weapon;

public interface InventoryComponent extends Component {

    void add(Item item);

    List<Optional<Item>> getOwnedItems();

    List<Optional<Weapon>> getOwnedWeapons();

    Optional<Weapon> getEquippedWeapon();

    void remove(int index, int quantity);
}
