package hotlinecesena.model.entities.actors;

import java.util.Objects;

import javax.annotation.Nonnull;

import hotlinecesena.model.entities.AbstractMovableEntity;
import hotlinecesena.model.events.AttackPerformedEvent;
import hotlinecesena.model.events.DamageReceivedEvent;
import hotlinecesena.model.events.DeathEvent;
import hotlinecesena.model.events.ReloadEvent;
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
    private Status status = ActorStatus.IDLE;
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
    protected AbstractActor(@Nonnull final Point2D position, final double angle, final double width,
            final double height, final double speed, final double maxHealth,
            @Nonnull final Inventory inventory) {
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
                if (w.getCurrentAmmo() == 0) {
                    this.reload();
                } else {
                    final int previousAmmo = w.getCurrentAmmo();
                    w.usage().accept(this);
                    /*
                     * Limit the number of events published by checking
                     * if the weapon did actually shoot.
                     */
                    if (w.getCurrentAmmo() < previousAmmo) {
                        status = ActorStatus.ATTACKING;
                        this.publish(new AttackPerformedEvent(this, w.getWeaponType()));
                    }
                }
            });
        }
    }

    @Override
    public final void reload() {
        if (this.isAlive() && !inventory.isReloading()) {
            inventory.reloadWeapon();
            /*
             * Inventory may not initiate reloading if the weapon's
             * magazine is already full or if no spare ammo is available.
             */
            if (inventory.isReloading()) {
                // If reloading has begun, it's safe to retrieve the weapon.
                this.publish(new ReloadEvent(this, inventory.getWeapon().orElseThrow().getWeaponType()));
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
            this.publish(new DamageReceivedEvent(this, damage));
        }
        if (currentHealth == 0) {
            status = ActorStatus.DEAD;
            this.publish(new DeathEvent(this));
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
    public final Status getActorStatus() {
        return status;
    }

    /**
     * Sets this actor's {@link ActorStatus} to {@code s}.
     * Status may not be modified by external objects.
     *
     * @param s the new status
     * @throws NullPointerException if the given status is null.
     */
    protected final void setActorStatus(@Nonnull final ActorStatus s) {
        status = Objects.requireNonNull(s);
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
