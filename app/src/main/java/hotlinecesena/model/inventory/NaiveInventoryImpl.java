package hotlinecesena.model.inventory;

import java.util.Optional;

import hotlinecesena.model.entities.items.Item;

public class NaiveInventoryImpl implements Inventory {

    @Override
    public void add(Item item) {
        // TODO Auto-generated method stub

    }

    @Override
    public Optional<Item> getUsable() {
        return Optional.empty();
    }

    @Override
    public Optional<Item> getEquipped() {
        return Optional.empty();
    }

    @Override
    public void reloadEquipped() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dropUsable() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dropEquipped() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isReloading() {
        // TODO Auto-generated method stub
        return false;
    }

}
