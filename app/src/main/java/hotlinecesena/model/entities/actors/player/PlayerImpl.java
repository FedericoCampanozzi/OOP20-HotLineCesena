package hotlinecesena.model.entities.actors.player;

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
 *
 * Player implementation.
 *
 */
public final class PlayerImpl extends AbstractActor implements Player {

    private static final double ITEM_USAGE_RADIUS = 1.0;
    private static final double DEFAULT_NOISE_LEVEL = 0.0;
    private final Map<ActorStatus, Double> noiseLevels;

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
     * @throws NullPointerException if the given {@code position}, {@code inventory} or {@code noise} are null.
     */
    public PlayerImpl(@Nonnull final Point2D position, final double angle, final double width,
            final double height, final double speed, final double maxHealth,
            @Nonnull final Inventory inventory, @Nonnull final Map<ActorStatus, Double> noise) {
        super(position, angle, width, height, speed, maxHealth, inventory);
        noiseLevels = Objects.requireNonNull(noise);
    }

    /**
     *
     * @throws NullPointerException if the supplied direction is null.
     */
    @Override
    public void move(@Nonnull final Point2D direction) {
        Objects.requireNonNull(direction);
        if (!direction.equals(Point2D.ZERO) && this.isAlive()) {
            final Point2D oldPos = this.getPosition();
            final Point2D newPos = oldPos.add(direction.multiply(this.getSpeed()));
            final Stream<Enemy> enemies = this.getGameMaster().getEnemy().getEnemies()
                    .stream()
                    .filter(e -> e.getActorStatus() != ActorStatus.DEAD);
            final Stream<Obstacle> obstacles = this.getGameMaster().getPhysics().getObstacles().stream();

            if (!(this.hasCollided(newPos, enemies) || this.hasCollided(newPos, obstacles))) {
                this.setPosition(newPos);
                this.publish(new MovementEvent<>(this, newPos));
                this.setActorStatus(ActorStatus.MOVING);
            }
        }
    }

    /**
     * Convenience method to check for collisions on a given stream of entities.
     * @param newPos
     * @param stream
     * @return
     */
    private boolean hasCollided(final Point2D newPos, final Stream<? extends Entity> stream) {
        return stream.anyMatch(e -> this.isCollidingWith(newPos, e));
    }

    @Override
    public double getNoiseRadius() {
        final ActorStatus status = this.getActorStatus();
        return status == ActorStatus.ATTACKING ? this.getInventory().getWeapon().get().getNoise()
                : noiseLevels.getOrDefault(status, DEFAULT_NOISE_LEVEL);
    }

    @Override
    public void use() {
        this.useItem();
        this.pickUpWeapon();
    }

    /**
     * Uses nearby items, if there are any.
     */
    private void useItem() {
        final Map<Point2D, ItemsType> itemsOnMap = this.getGameMaster().getDataItems().getItems();
        final Set<Point2D> toBeRemoved = new HashSet<>();
        itemsOnMap.forEach((itemPos, item) -> {
            if (MathUtils.isCollision(this.getPosition(), this.getWidth(), this.getHeight(),
                    itemPos, ITEM_USAGE_RADIUS, ITEM_USAGE_RADIUS)) {
                item.usage().accept(this);
                toBeRemoved.add(itemPos);
                this.publish(new ItemPickUpEvent<>(this, item, itemPos));
            }
        });
        toBeRemoved.forEach(itemsOnMap::remove);
    }

    /**
     * Picks up a nearby weapon, if present.
     */
    private void pickUpWeapon() {
        if (!this.getInventory().isReloading()) {
            final Map<Point2D, Weapon> weaponsOnMap = this.getGameMaster().getWeapons().getWeapons();
            final Optional<Entry<Point2D, Weapon>> weaponFound = weaponsOnMap.entrySet()
                    .stream()
                    .filter(e -> MathUtils.isCollision(this.getPosition(), this.getWidth(), this.getHeight(),
                            e.getKey(), ITEM_USAGE_RADIUS, ITEM_USAGE_RADIUS))
                    .findFirst();
            weaponFound.ifPresent(entry -> {
                final Weapon w = entry.getValue();
                final Point2D pos = entry.getKey();
                /*
                 * If the player already owns the same kind of weapon, ignore it.
                 */
                if (this.getInventory().getQuantityOf(w) != 1) {
                    this.getInventory().add(w, 1);
                    weaponsOnMap.remove(pos);
                    this.publish(new WeaponPickUpEvent<>(this, w.getWeaponType(), pos));
                }
            });
        }
    }
}
