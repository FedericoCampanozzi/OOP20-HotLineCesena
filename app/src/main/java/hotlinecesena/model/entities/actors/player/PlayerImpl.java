package hotlinecesena.model.entities.actors.player;

import java.util.Map;
import java.util.Set;

import hotlinecesena.model.entities.actors.AbstractActor;
import hotlinecesena.model.entities.actors.ActorStateList;
import hotlinecesena.model.entities.actors.state.State;
import hotlinecesena.model.inventory.Inventory;
import javafx.geometry.Point2D;
import javafx.util.Pair;

public class PlayerImpl extends AbstractActor implements Player {

    private final Map<ActorStateList, Double> noiseLevels;
    private final Map<ActorStateList, State> stateMap = Map.of(
            ActorStateList.NORMAL, new PlayerNormalState(this),
            ActorStateList.RELOADING, new PlayerReloadingState(this),
            ActorStateList.DEAD, new PlayerDeadState(this)
            );

    public PlayerImpl(final Point2D pos, final double angle, final double speed,
            final double maxHealth, final Inventory inv, final Map<ActorStateList, Double> noise) {
        super(pos, angle, speed, maxHealth, inv);
        this.noiseLevels = noise;
    }

    @Override
    public double getNoiseRadius() {
        return this.noiseLevels.get(this.getState());
    }

    @Override
    public void pickUp() {
        //TODO Use DAL and collisions to determine which item, if there are any, will be picked up
        this.getInventory().add(null);
    }

    @Override
    public void use() {
        final var item = this.getInventory().getUsable();
        if (item.isPresent() && item.get().usage().isPresent()) {
            item.get().usage().get().accept(this);
        }
    }

    @Override
    public void dropUsable() {
        this.getInventory().dropUsable();
    }

    @Override
    public void dropEquipped() {
        this.getInventory().dropEquipped();
    }

    @Override
    public void handleCurrentState(Pair<Set<CommandType>, Point2D> commandsToHandle, double timeElapsed) {
        this.stateMap.get(this.getState()).handle(commandsToHandle, timeElapsed);
    }
}
