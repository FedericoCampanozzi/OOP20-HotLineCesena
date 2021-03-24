package hotlinecesena.model.entities.actors;

import java.util.Optional;

import hotlinecesena.model.entities.AbstractMovableEntity;
import hotlinecesena.model.entities.items.Item;
import hotlinecesena.model.inventory.Inventory;
import javafx.geometry.Point2D;

public abstract class AbstractActor extends AbstractMovableEntity implements Actor {

    private final double maxHealth;
    private double currentHealth;
    private ActorStatus state = ActorStatus.NORMAL;
    private final Inventory inventory;

    protected AbstractActor(final Point2D pos, final double angle, final double speed,
            final double maxHealth, final Inventory inv) {
        super(pos, angle, speed);
        this.maxHealth = this.currentHealth = maxHealth;
        this.inventory = inv;
    }

    @Override
    public void attack() {
        final Optional<Item> weapon = this.inventory.getEquipped();
        if (weapon.isPresent()) {
            weapon.get().usage().get().accept(this);
        }
    }

    @Override
    public void reload() {
        this.inventory.reloadEquipped();
    }

    @Override
    public void takeDamage(double damage) {
        if (this.currentHealth > 0) {
            this.currentHealth = this.currentHealth > damage ? this.currentHealth - damage : 0;
        }
    }

    @Override
    public void heal(double hp) {
        if (this.currentHealth + hp <= this.maxHealth) {
            this.currentHealth += hp;
        } else {
            this.currentHealth = this.maxHealth;
        }
    }

    @Override
    public double getMaxHealth() {
        return this.maxHealth;
    }

    @Override
    public double getCurrentHealth() {
        return this.currentHealth;
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public ActorStatus getActorStatus() {
        return this.state;
    }

    @Override
    public void setActorStatus(ActorStatus s) {
        this.state = s;
    }
}
