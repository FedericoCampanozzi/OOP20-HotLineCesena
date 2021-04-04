package hotlinecesena.model.entities.actors;

import java.util.Objects;
import java.util.Optional;

import hotlinecesena.model.entities.AbstractMovableEntity;
import hotlinecesena.model.entities.items.Weapon;
import hotlinecesena.model.events.AttackPerformedEvent;
import hotlinecesena.model.events.DamageReceivedEvent;
import hotlinecesena.model.events.DeathEvent;
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

    /**
     *
     * @param pos
     * @param angle
     * @param speed
     * @param maxHealth maximum health points.
     * @param inv the {@link Inventory} used by this actor to access owned items and weapons.
     * @throws NullPointerException if {@code pos} or {@code inv} are null.
     */
    protected AbstractActor(final Point2D pos, final double angle, final double speed,
            final double maxHealth, final Inventory inv) {
        super(pos, angle, speed);
        this.maxHealth = currentHealth = maxHealth;
        inventory = Objects.requireNonNull(inv);
    }

    /**
     * Overridden to prohibit movements when the actor is dead.
     */
    @Override
    public void move(final Point2D direction) {
        if (this.isAlive()) {
            super.move(direction);
        }
    }

    /**
     * Overridden to prohibit rotations when the actor is dead.
     */
    @Override
    public final void setAngle(final double angle) {
        if (this.isAlive()) {
            super.setAngle(angle);
        }
    }

    @Override
    public final void attack() {
        if (this.isAlive() && !inventory.isReloading()) {
            final Optional<Weapon> weapon = inventory.getWeapon();
            if (weapon.isPresent() && weapon.get().getCurrentAmmo() > 0) {
                final Weapon w = weapon.get();
                w.usage().get().accept(this);
                this.publish(new AttackPerformedEvent(this, w));
            }
        }
    }

    @Override
    public final void reload() {
        if (this.isAlive()) {
            inventory.reloadWeapon();
        }
    }

    /**
     * @throws IllegalArgumentException if supplied damage is negative.
     */
    @Override
    public final void takeDamage(final double damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Negative damage: " + damage);
        }
        if (this.isAlive()) {
            currentHealth = (currentHealth > damage) ? (currentHealth - damage) : 0;
            this.publish(new DamageReceivedEvent(this, damage));
        }
        if (!this.isAlive()) {
            status = ActorStatus.DEAD; // TODO Discard statuses in favor of events?
            this.publish(new DeathEvent(this));
        }
    }

    /**
     * @throws IllegalArgumentException if supplied hp is negative.
     */
    @Override
    public final void heal(final double hp) {
        if (hp < 0) {
            throw new IllegalArgumentException("Negative hp: " + hp);
        }
        if (this.isAlive()) {
            currentHealth = (currentHealth + hp < maxHealth) ? (currentHealth + hp) : maxHealth;
        }
    }

    @Override
    public final double getMaxHealth() {
        return maxHealth;
    }

    @Override
    public final double getCurrentHealth() {
        return currentHealth;
    }

    /**
     * Convenience method to be used internally.
     * @return {@code true} if the actor is alive, {@code false} otherwise.
     */
    protected final boolean isAlive() {
        return currentHealth > 0;
    }

    @Override
    public final Inventory getInventory() {
        return inventory;
    }

    @Override
    public final ActorStatus getActorStatus() {
        return status;
    }

    @Override
    public final void setActorStatus(final ActorStatus s) {
        status = s;
    }
}
