package hotlinecesena.model.entities.actors;

import java.util.Optional;

import hotlinecesena.model.entities.AbstractMovableEntity;
import hotlinecesena.model.entities.items.Item;
import hotlinecesena.model.inventory.Inventory;
import javafx.geometry.Point2D;

/**
 * 
 * Base class to extend when creating new Actor specializations.
 *
 */
public abstract class AbstractActor extends AbstractMovableEntity implements Actor {

    private final double maxHealth;
    private double currentHealth;
    private ActorStatus status = ActorStatus.NORMAL;
    private final Inventory inventory;

    protected AbstractActor(final Point2D pos, final double angle, final double speed,
            final double maxHealth, final Inventory inv) {
        super(pos, angle, speed);
        this.maxHealth = this.currentHealth = maxHealth;
        this.inventory = inv;
    }

    /**
     * Can be overridden if a concrete implementation does not require an inventory system.
     */
    @Override
    public void attack() {
        if (!this.inventory.isReloading()) {
            final Optional<Item> weapon = this.inventory.getEquipped();
            if (weapon.isPresent()) {
                weapon.get().usage().get().accept(this);
            }
        }
    }

    /**
     * Can be overridden if a concrete implementation does not require an inventory system.
     */
    @Override
    public void reload() {
        this.inventory.reloadEquipped();
    }

    @Override
    public final void takeDamage(double damage) {
        if (this.currentHealth > 0) {
            this.currentHealth = this.currentHealth > damage ? this.currentHealth - damage : 0;
        }
    }

    @Override
    public final void heal(double hp) {
        if (this.currentHealth + hp <= this.maxHealth) {
            this.currentHealth += hp;
        } else {
            this.currentHealth = this.maxHealth;
        }
    }

    @Override
    public final double getMaxHealth() {
        return this.maxHealth;
    }

    @Override
    public final double getCurrentHealth() {
        return this.currentHealth;
    }

    @Override
    public final Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public final ActorStatus getActorStatus() {
        return this.status;
    }

    @Override
    public final void setActorStatus(ActorStatus s) {
        this.status = s;
    }
}
