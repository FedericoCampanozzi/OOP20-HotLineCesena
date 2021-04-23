package hotlinecesena.model.entities.actors.player;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nonnull;

import hotlinecesena.model.dataccesslayer.datastructure.DataPhysicsCollision.Obstacle;
import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.entities.items.ItemsType;
import hotlinecesena.model.entities.items.Weapon;
import hotlinecesena.model.inventory.Inventory;
import hotlinecesena.model.inventory.InventoryFactory;
import hotlinecesena.model.inventory.NaiveInventoryFactory;
import javafx.geometry.Point2D;

/**
 * Basic {@link PlayerFactory} implementation.
 */
public final class PlayerFactoryImpl implements PlayerFactory {

    private static final double WIDTH = 0.80;
    private static final double HEIGHT = 0.80;
    private static final double SPEED = 5;
    private static final double MAX_HEALTH = 100;
    private final InventoryFactory invFactory = new NaiveInventoryFactory();
    private final Map<ActorStatus, Double> noise = Map.of(
            ActorStatus.IDLE, 0.0,
            ActorStatus.MOVING, 8.0,
            ActorStatus.DEAD, 0.0
            );

    /**
     * @throws NullPointerException if the given {@code position}, {@code noise},
     * {@code obstacles}, {@code enemies}, {@code itemsOnMap} or {@code weaponsOnMap}
     * are null.
     */
    @Override
    public Player createPlayer(@Nonnull final Point2D position, final double angle,
            @Nonnull final Collection<Obstacle> obstacles, @Nonnull final Collection<Enemy> enemies,
            @Nonnull final Map<Point2D, ItemsType> itemsOnMap, @Nonnull final Map<Point2D, Weapon> weaponsOnMap) {
        return this.createWithCustomInventory(
                position,
                angle,
                invFactory.createWithPistolOnly(),
                obstacles,
                enemies,
                itemsOnMap,
                weaponsOnMap);
    }

    /**
     * @throws NullPointerException if the given {@code position}, {@code inventory},
     * {@code noise}, {@code obstacles}, {@code enemies}, {@code itemsOnMap} or {@code weaponsOnMap}
     * are null.
     */
    @Override
    public Player createWithCustomInventory(@Nonnull final Point2D position, final double angle,
            @Nonnull final Inventory inventory, @Nonnull final Collection<Obstacle> obstacles,
            @Nonnull final Collection<Enemy> enemies, @Nonnull final Map<Point2D, ItemsType> itemsOnMap,
            @Nonnull final Map<Point2D, Weapon> weaponsOnMap) {
        return new PlayerImpl(
                Objects.requireNonNull(position),
                angle,
                WIDTH,
                HEIGHT,
                SPEED,
                MAX_HEALTH,
                Objects.requireNonNull(inventory),
                noise,
                Objects.requireNonNull(obstacles),
                Objects.requireNonNull(enemies),
                Objects.requireNonNull(itemsOnMap),
                Objects.requireNonNull(weaponsOnMap));
    }
}
