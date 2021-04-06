package hotlinecesena.model.entities.actors;

import java.util.Objects;

import hotlinecesena.model.entities.AbstractMovableEntity;
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
    private ActorStatus status = ActorStatus.IDLE;
    private final Inventory inventory;

    /**
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
     * @implNote
     * Overridden to prohibit movements when the actor is dead.
     */
    @Override
    public void move(final Point2D direction) {
        if (this.isAlive() && !direction.equals(DirectionList.NONE.get())) {
            super.move(direction);
            status = ActorStatus.MOVING;
        }
    }

    @Override
    public final void attack() {
        if (this.isAlive()) {
            inventory.getWeapon().ifPresent(weapon -> {
                if (!inventory.isReloading() && weapon.getCurrentAmmo() > 0) {
                    weapon.usage().accept(this);
                    status = ActorStatus.ATTACKING;
                    this.publish(new AttackPerformedEvent<>(this, weapon));
                }
            });
        }
    }

    @Override
    public final void reload() {
        if (this.isAlive()) {
            inventory.reloadWeapon();
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
}
