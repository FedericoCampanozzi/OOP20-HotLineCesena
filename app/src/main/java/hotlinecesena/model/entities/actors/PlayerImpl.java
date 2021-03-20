package hotlinecesena.model.entities.actors;

import java.util.Map;
import java.util.Optional;

import hotlinecesena.model.entities.items.Item;
import hotlinecesena.model.entities.items.UsableItem;
import hotlinecesena.model.entities.items.Weapon;
import hotlinecesena.model.inventory.Inventory;
import javafx.geometry.Point2D;

public class PlayerImpl extends AbstractActor implements Player {
    
    private final Map<ActorState, Double> noiseLevels;

    public PlayerImpl(Point2D pos, double health, double angle, Inventory inv, Map<ActorState, Double> noise) {
        super(pos, health, angle, inv);
        this.noiseLevels = noise;
    }

    @Override
    public void pickUpItem(Item item, int index) {
        this.getInventory().addItem(item, index);
    }
    
    @Override
    public void pickUpWeapon(Weapon w, int index) {
        this.getInventory().addWeapon(w, index);
    }

    @Override
    public void useItem(int index) {
        final Optional<Item> item = this.getInventory().getOwnedItems().get(index);
        if (item.isPresent() && item.get() instanceof UsableItem) {
            ((UsableItem) item.get()).use(this);
        }
    }
    
    @Override
    public void equipWeapon(int index) {
        this.getInventory().setEquippedWeapon(index);
    }
    
    @Override
    public void dropItem(int index) {
        this.getInventory().dropItem(index);
    }

    @Override
    public double getNoiseLevelByState() {
        return this.noiseLevels.get(this.getState()) +
                (this.getState() == ActorStateList.ATTACKING ? getInventory().getEquippedWeapon().get().getNoise(): 0);
    }

    @Override
    public void dropWeapon(int index) {
        this.getInventory().dropWeapon(index);
    }
}
