package hotlinecesena.model.entities.actors.player;

import java.util.Map;
import hotlinecesena.model.entities.actors.AbstractActor;
import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.inventory.Inventory;
import javafx.geometry.Point2D;

public final class PlayerImpl extends AbstractActor implements Player {

    private final Map<ActorStatus, Double> noiseLevels;

    public PlayerImpl(final Point2D pos, final double angle, final double speed, final double maxHealth,
            final Inventory inv, final Map<ActorStatus, Double> noise) {
        super(pos, angle, speed, maxHealth, inv);
        this.noiseLevels = noise;
    }

    @Override
    public double getNoiseRadius() {
        return this.noiseLevels.get(this.getActorStatus()); //TODO Put noise in events instead?
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

    public void update(double timeElapsed) {
        this.getInventory().update(timeElapsed);
    }
}
