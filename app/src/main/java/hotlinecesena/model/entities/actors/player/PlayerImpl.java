package hotlinecesena.model.entities.actors.player;

import java.util.Map;
import java.util.Objects;

import hotlinecesena.model.entities.actors.AbstractActor;
import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.inventory.Inventory;
import javafx.geometry.Point2D;

public final class PlayerImpl extends AbstractActor implements Player {

    private final Map<ActorStatus, Double> noiseLevels;

    /**
     *
     * @param pos
     * @param angle
     * @param speed
     * @param maxHealth
     * @param inv
     * @param noise
     * @throws NullPointerException if {@code pos}, {@code inv} or {@code noise} are null.
     */
    public PlayerImpl(final Point2D pos, final double angle, final double speed, final double maxHealth,
            final Inventory inv, final Map<ActorStatus, Double> noise) {
        super(pos, angle, speed, maxHealth, inv);
        noiseLevels = Objects.requireNonNull(noise);
    }

    @Override
    public double getNoiseRadius() {
        return noiseLevels.get(this.getActorStatus()); //TODO Put noise in events instead?
    }

    @Override
    public void pickUp() {
        //TODO Use DAL and collisions to determine which item will be picked up
        this.getInventory().add(null);
    }

    @Override
    public void use() {
        // TODO
    }

    @Override
    public void update(final double timeElapsed) {
        this.getInventory().update(timeElapsed);
    }
}
