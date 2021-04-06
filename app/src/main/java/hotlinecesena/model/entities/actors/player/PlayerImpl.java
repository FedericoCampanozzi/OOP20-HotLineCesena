package hotlinecesena.model.entities.actors.player;

import java.util.Map;
import java.util.Objects;

import hotlinecesena.model.entities.actors.AbstractActor;
import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.inventory.Inventory;
import javafx.geometry.Point2D;

public final class PlayerImpl extends AbstractActor implements Player {

    private static final double ACTIVATION_RADIUS = 5.0;
    private static final double DEFAULT_NOISE_LEVEL = 0.0;
    private final Map<ActorStatus, Double> noiseLevels;

    /**
     *
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

    @Override
    public void update(final double timeElapsed) {
        this.setActorStatus(ActorStatus.IDLE);
        this.getInventory().update(timeElapsed);
    }
}
