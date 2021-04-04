package hotlinecesena.model.entities.actors.player;

import java.util.Map;
import java.util.Objects;

import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.inventory.NaiveInventoryImpl;
import javafx.geometry.Point2D;

public class PlayerFactoryImpl implements PlayerFactory {

    private static final double MAX_HEALTH = 100;
    private static final double SPEED = 450;
    private static final double STARTING_ANGLE = 270;
    private static final Point2D STARTING_POS = Point2D.ZERO;

    @Override
    public Player createPlayer() {
        return new PlayerImpl(
                STARTING_POS,
                STARTING_ANGLE,
                SPEED,
                MAX_HEALTH,
                new NaiveInventoryImpl(),
                Map.of(ActorStatus.NORMAL, 0.0,
                        ActorStatus.ATTACKING, 10.0,
                        ActorStatus.RELOADING, 3.0,
                        ActorStatus.DEAD, 0.0
                        )
                );
    }

    @Override
    public Player createPlayer(final Point2D position, final double angle) {
        return new PlayerImpl(
                Objects.requireNonNull(position),
                angle,
                SPEED,
                MAX_HEALTH,
                new NaiveInventoryImpl(),
                Map.of(ActorStatus.NORMAL, 0.0,
                        ActorStatus.ATTACKING, 10.0,
                        ActorStatus.RELOADING, 3.0,
                        ActorStatus.DEAD, 0.0
                        )
                );
    }
}
