package hotlinecesena.model.entities.actors;

import java.util.Map;
import java.util.Optional;

import hotlinecesena.model.entities.items.Item;
import hotlinecesena.model.entities.items.Usable;
import hotlinecesena.model.entities.items.Weapon;
import hotlinecesena.model.inventory.Inventory;
import javafx.geometry.Point2D;

public class PlayerImpl extends AbstractActor implements Player {
    
    private final Map<ActorState, Double> noiseLevels;

    public PlayerImpl(final Point2D pos, final double angle, final double speed,
            final double maxHealth, final Inventory inv, final Map<ActorState, Double> noise) {
        super(pos, angle, speed, maxHealth, inv);
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
        if (item.isPresent() && item.get() instanceof Usable) {
            ((Usable) item.get()).use(Optional.of(null));
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
