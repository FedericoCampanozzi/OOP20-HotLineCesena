package hotlinecesena.model.inventory;

import java.util.Map;
import java.util.Optional;

import hotlinecesena.model.entities.items.Item;

public class NaiveInventoryImpl implements Inventory {

    private static final double DEFAULT_RELOAD_TIME = 2.0;
    private double reloadTimeRemaining = 0.0;
    private Optional<Item> equippable = Optional.empty();
    private Map<Item, Integer> stackables;

    @Override
    public void add(Item item) {
//        if (isWeapon(item)) {
//            if (this.equippable.isPresent()) {
//                this.drop(this.equippable.get());
//            }
//            this.equippable = Optional.of(item);
//        } else {
//            
//        }
        final int oldQuantity = this.stackables.containsKey(item) ? this.stackables.get(item) : 0;
        this.stackables.put(item, 1 + oldQuantity);
    }

    @Override
    public Optional<Item> getEquipped() {
        return this.equippable;
    }

    @Override
    public void reloadEquipped() {
        if (!this.isReloading()) {
            this.reloadTimeRemaining = DEFAULT_RELOAD_TIME;
        }
    }

    @Override
    public boolean isReloading() {
        return this.reloadTimeRemaining > 0.0;
    }
    
    public void update(double timeElapsed) {
        if (this.isReloading()) {
            reloadTimeRemaining -= timeElapsed;
            if (reloadTimeRemaining <= 0.0) {
//                this.equippable.get().reload();
            }
        }
    }

    private void drop(Item item) {
        //TODO Remove from inventory and then add to DAL?
    }
}
