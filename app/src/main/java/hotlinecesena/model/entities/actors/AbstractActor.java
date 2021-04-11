package hotlinecesena.model.entities.actors;

import java.util.Objects;

import hotlinecesena.model.entities.AbstractMovableEntity;
import hotlinecesena.model.events.AttackPerformedEvent;
import hotlinecesena.model.events.DamageReceivedEvent;
import hotlinecesena.model.events.DeathEvent;
import hotlinecesena.model.events.ReloadEvent;
import hotlinecesena.model.events.Subscriber;
import hotlinecesena.model.inventory.Inventory;
import javafx.geometry.Point2D;

/**
 *
 * Base class to extend when creating new Actor specializations.
 *
 */
public abstract class AbstractActor extends AbstractMovableEntity implements Actor, Subscriber {

    private final double maxHealth;
    private double currentHealth;
    private ActorStatus status = ActorStatus.IDLE;
    private final Inventory inventory;

    /**
     * Base constructor for AbstractActors.
     * @param position starting position in which this actor will be located.
     * @param angle starting angle that this actor will face.
     * @param width this actor's width.
     * @param height this actor's height.
     * @param speed the speed at which this actor will move.
     * @param maxHealth maximum health points.
     * @param inventory the {@link Inventory} used by this actor to access owned items and weapons.
     * @throws NullPointerException if the given {@code position} or {@code inventory} are null.
     */
    protected AbstractActor(final Point2D position, final double angle, final double width, final double height,
            final double speed, final double maxHealth, final Inventory inventory) {
        super(position, angle, width, height, speed);
        this.maxHealth = maxHealth;
        currentHealth = maxHealth;
        this.inventory = Objects.requireNonNull(inventory);
    }

    /**
     * Prohibits rotations when this Actor is dead.
     */
    @Override
    protected final boolean canInitiateRotation() {
        return this.isAlive();
    }

    @Override
    public final void attack() {
        if (this.isAlive()) {
            inventory.getWeapon().ifPresent(w -> {
                final int previousAmmo = w.getCurrentAmmo();
                w.usage().accept(this);
                if (w.getCurrentAmmo() < previousAmmo) {
                    status = ActorStatus.ATTACKING;
                    this.publish(new AttackPerformedEvent<>(this, w.getWeaponType()));
                }
            });
        }
    }

    @Override
    public final void reload() {
        if (this.isAlive() && !inventory.isReloading()) {
            inventory.reloadWeapon();
            if (inventory.isReloading()) {
                this.publish(new ReloadEvent<>(this, inventory.getWeapon().get().getWeaponType()));
            }
        }
    }

    /**
     * @throws IllegalArgumentException if the given damage is negative.
     */
    @Override
    public final void takeDamage(final double damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Negative damage: " + damage);
        }
        if (this.isAlive()) {
            currentHealth = (currentHealth > damage) ? (currentHealth - damage) : 0;
            this.publish(new DamageReceivedEvent<>(this, damage));
        }
        if (!this.isAlive()) {
            status = ActorStatus.DEAD;
            this.publish(new DeathEvent<>(this));
        }
    }

    /**
     * @throws IllegalArgumentException if the given hp is negative.
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

    /**
     * Sets this actor's {@link ActorStatus} to {@code s}.
     * Status may not be modified by external objects.
     *
     * @param s the new status
     */
    protected final void setActorStatus(final ActorStatus s) {
        status = s;
    }

    /**
     * @implSpec
     * Updates the inventory and sets the {@link ActorStatus} to {@code IDLE}.
     */
    @Override
    public final void update(final double timeElapsed) {
        if (status != ActorStatus.DEAD) {
            this.setActorStatus(ActorStatus.IDLE);
            this.getInventory().update(timeElapsed);
        }
    }
}
