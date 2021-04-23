package hotlinecesena.model.entities.actors.player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import hotlinecesena.model.dataccesslayer.datastructure.DataPhysicsCollision.Obstacle;
import hotlinecesena.model.entities.Entity;
import hotlinecesena.model.entities.actors.AbstractActor;
import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.entities.actors.Status;
import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.entities.items.ItemsType;
import hotlinecesena.model.entities.items.Weapon;
import hotlinecesena.model.events.ItemPickUpEvent;
import hotlinecesena.model.events.MovementEvent;
import hotlinecesena.model.events.WeaponPickUpEvent;
import hotlinecesena.model.inventory.Inventory;
import hotlinecesena.utilities.MathUtils;
import javafx.geometry.Point2D;

/**
 * {@link Player} implementation.
 */
public final class PlayerImpl extends AbstractActor implements Player {

    private static final double DEFAULT_NOISE_LEVEL = 0.0;
    private final Map<ActorStatus, Double> noiseLevels;
    private final Collection<Obstacle> obstacles;
    private final Collection<Enemy> enemies;
    private final Map<Point2D, ItemsType> itemsOnMap;
    private final Map<Point2D, Weapon> weaponsOnMap;
    private final double itemUsageRadius = this.getWidth() * 0.75;

    /**
     * Instantiates a new {@code Player}.
     * @param position starting position in which this actor will be located.
     * @param angle starting angle that this actor will face.
     * @param width this actor's width.
     * @param height this actor's height.
     * @param speed the speed at which this actor will move.
     * @param maxHealth maximum health points.
     * @param inventory the {@link Inventory} used by this actor to access owned items and weapons.
     * @param noise a {@link Map} associating noise levels to certain or all {@link ActorStatus}es.
     * @param obstacles obstacles on the game map.
     * @param enemies enemies on the game map.
     * @param items items on the game map.
     * @param weapons weapons on the game map.
     * @throws NullPointerException if the given {@code position}, {@code inventory},
     * {@code noise}, {@code obstacles}, {@code enemies}, {@code items} or {@code weapons}
     * are null.
     */
    public PlayerImpl(@Nonnull final Point2D position, final double angle, final double width,
            final double height, final double speed, final double maxHealth,
            @Nonnull final Inventory inventory, @Nonnull final Map<ActorStatus, Double> noise,
            @Nonnull final Collection<Obstacle> obstacles, @Nonnull final Collection<Enemy> enemies,
            @Nonnull final Map<Point2D, ItemsType> items, final Map<Point2D, Weapon> weapons) {
        super(position, angle, width, height, speed, maxHealth, inventory);
        noiseLevels = Objects.requireNonNull(noise);
        this.obstacles = Objects.requireNonNull(obstacles);
        this.enemies = Objects.requireNonNull(enemies);
        itemsOnMap = Objects.requireNonNull(items);
        weaponsOnMap = Objects.requireNonNull(weapons);
    }

    /**
     * @throws NullPointerException if the supplied direction is null.
     */
    @Override
    protected void executeMovement(@Nonnull final Point2D direction) {
        if (this.isAlive()) {
            final Point2D oldPos = this.getPosition();
            final Point2D newPos = oldPos.add(direction.multiply(this.getSpeed()));
            final Stream<Enemy> enemyStream = enemies
                    .stream()
                    .filter(e -> e.getActorStatus() != ActorStatus.DEAD);
            if (!(this.hasCollided(newPos, enemyStream) || this.hasCollided(newPos, obstacles.stream()))) {
                this.setPosition(newPos);
                this.publish(new MovementEvent(this, newPos));
                this.setActorStatus(ActorStatus.MOVING);
            }
        }
    }

    /*
     * Convenience method to check for collisions on a given stream of entities.
     */
    private boolean hasCollided(final Point2D newPos, final Stream<? extends Entity> stream) {
        return stream.anyMatch(e -> this.isCollidingWith(newPos, e));
    }

    @Override
    public double getNoiseRadius() {
        final Status status = this.getActorStatus();
        return status == ActorStatus.ATTACKING ? this.getInventory().getWeapon().get().getNoise()
                : noiseLevels.getOrDefault(status, DEFAULT_NOISE_LEVEL);
    }

    @Override
    public void use() {
        this.useItem();
        this.pickUpWeapon();
    }

    /*
     * Uses nearby items, if there are any.
     */
    private void useItem() {
        final Set<Point2D> toBeRemoved = new HashSet<>();
        itemsOnMap.forEach((itemPos, item) -> {
            if (MathUtils.isCollision(this.getPosition(), this.getWidth(), this.getHeight(),
                    itemPos, itemUsageRadius, itemUsageRadius)) {
                item.usage().accept(this);
                toBeRemoved.add(itemPos);
                this.publish(new ItemPickUpEvent(this, item, itemPos));
            }
        });
        toBeRemoved.forEach(itemsOnMap::remove);
    }

    /*
     * Picks up a nearby weapon, if present.
     */
    private void pickUpWeapon() {
        if (!this.getInventory().isReloading()) {
            final Optional<Entry<Point2D, Weapon>> weaponFound = weaponsOnMap.entrySet()
                    .stream()
                    .filter(e -> MathUtils.isCollision(this.getPosition(), this.getWidth(), this.getHeight(),
                            e.getKey(), itemUsageRadius, itemUsageRadius))
                    .findFirst();
            weaponFound.ifPresent(entry -> {
                final Weapon newWeap = entry.getValue();
                final Point2D pos = entry.getKey();
                /*
                 * Drop the player's weapon on the map before picking up
                 * the newly found one.
                 */
                this.getInventory().getWeapon().ifPresentOrElse(ownedWeapon -> {
                    weaponsOnMap.put(pos, ownedWeapon);
                    this.publish(new WeaponPickUpEvent(this, newWeap.getWeaponType(),
                            ownedWeapon.getWeaponType(), pos));
                }, () -> {
                    weaponsOnMap.remove(pos);
                    this.publish(new WeaponPickUpEvent(this, newWeap.getWeaponType(), null, pos));
                });
                this.getInventory().add(newWeap, 1);
            });
        }
    }
}
