package hotlinecesena.model.entities.actors.player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import hotlinecesena.model.entities.Entity;
import hotlinecesena.model.entities.actors.AbstractActor;
import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.entities.items.ItemsType;
import hotlinecesena.model.events.MovementEvent;
import hotlinecesena.model.events.PickUpEvent;
import hotlinecesena.model.inventory.Inventory;
import hotlinecesena.utilities.MathUtils;
import javafx.geometry.Point2D;

/**
 *
 * Player implementation.
 *
 */
public final class PlayerImpl extends AbstractActor implements Player {

    private static final double ITEM_USAGE_RADIUS = 1.5;
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
    public PlayerImpl(final Point2D position, final double angle, final double width, final double height,
            final double speed, final double maxHealth, final Inventory inventory,
            final Map<ActorStatus, Double> noise) {
        super(position, angle, width, height, speed, maxHealth, inventory);
        noiseLevels = Objects.requireNonNull(noise);
    }

    /**
     *
     * @throws NullPointerException if the supplied direction is null.
     */
    @Override
    public void move(final Point2D direction) {
        Objects.requireNonNull(direction);
        if (!direction.equals(Point2D.ZERO) && this.isAlive()) {
            final Point2D oldPos = this.getPosition();
            final Point2D newPos = oldPos.add(direction.multiply(this.getSpeed()));
            if (!this.hasCollided(newPos, this.getGameMaster().getEnemy().getEnemies().stream()
                    .filter(e -> e.getActorStatus() != ActorStatus.DEAD)
                    .collect(Collectors.toUnmodifiableSet()))
                    && !this.hasCollided(newPos, this.getGameMaster().getPhysics().getObstacles())) {
                this.setPosition(newPos);
                this.publish(new MovementEvent<>(this, newPos));
                this.setActorStatus(ActorStatus.MOVING);
            }
        }
    }

    private boolean hasCollided(final Point2D newPos, final Collection<? extends Entity> entities) {
        return entities.stream()
                .anyMatch(e -> MathUtils.isCollision(
                        newPos, this.getWidth(), this.getHeight(),
                        e.getPosition(), e.getWidth(), e.getHeight()));
    }

    @Override
    public double getNoiseRadius() {
        final ActorStatus status = this.getActorStatus();
        return status == ActorStatus.ATTACKING ? this.getInventory().getWeapon().get().getNoise()
                : noiseLevels.getOrDefault(status, DEFAULT_NOISE_LEVEL);
    }

    @Override
    public void use() {
        if (!this.getInventory().isReloading()) {
            final var itemIterator = this.getGameMaster().getDataItems().getItems().entrySet().iterator();
            final Set<Point2D> toBeRemoved = new HashSet<>();
            itemIterator.forEachRemaining(entry -> {
                final Point2D itemPos = entry.getKey();
                final ItemsType item = entry.getValue();
                if (MathUtils.isCollision(this.getPosition(), this.getWidth(), this.getHeight(),
                        itemPos, ITEM_USAGE_RADIUS, ITEM_USAGE_RADIUS)) {
                    item.usage().accept(this);
                    toBeRemoved.add(itemPos);
                    this.publish(new PickUpEvent<Player, ItemsType>(this, item));
                }
            });
            toBeRemoved.forEach(p -> this.getGameMaster().getDataItems().getItems().remove(p));
        }
    }
}
