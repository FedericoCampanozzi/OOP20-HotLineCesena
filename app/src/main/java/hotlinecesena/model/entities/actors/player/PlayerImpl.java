package hotlinecesena.model.entities.actors.player;

import java.util.Map;
import java.util.Objects;

import hotlinecesena.model.entities.actors.AbstractActor;
import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.events.MovementEvent;
import hotlinecesena.model.inventory.Inventory;
import hotlinecesena.utilities.MathUtils;
import javafx.geometry.Point2D;

/**
 *
 * Player implementation.
 *
 */
public final class PlayerImpl extends AbstractActor implements Player {

    private static final double ACTIVATION_RADIUS = 5.0;
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
            if (!this.hasCollided(newPos)) {
                this.setPosition(newPos);
                this.publish(new MovementEvent<>(this, newPos));
                this.setActorStatus(ActorStatus.MOVING);
            }
        }
    }

    private boolean hasCollided(final Point2D newPos) {
        return this.getGameMaster().getEnemy().getEnemies()
                .stream()
                .anyMatch(e -> MathUtils.isCollision(
                        newPos, this.getWidth(), this.getHeight(),
                        e.getPosition(), e.getWidth(), e.getHeight()))
                || this.getGameMaster().getPhysics().getObstacles()
                .stream()
                .anyMatch(o -> MathUtils.isCollision(
                        newPos, this.getWidth(), this.getHeight(),
                        new Point2D(o.getMinX(), o.getMinY()), o.getWidth(), o.getHeight()));
    }

    @Override
    public double getNoiseRadius() {
        final ActorStatus status = this.getActorStatus();
        return status == ActorStatus.ATTACKING ? this.getInventory().getWeapon().get().getNoise()
                : noiseLevels.getOrDefault(status, DEFAULT_NOISE_LEVEL);
    }

    @Override
    public void use() {
        //TODO
        if (!this.getInventory().isReloading()) {
            //this.publish(new PickUpEvent<Player, ItemsType>(this, ItemsType.MEDIKIT));
        }
    }

    /**
     * @implSpec
     * Updates the inventory and sets the {@link ActorStatus} to {@code IDLE}.
     */
    @Override
    public void update(final double timeElapsed) {
        this.setActorStatus(ActorStatus.IDLE);
        this.getInventory().update(timeElapsed);
    }
}
