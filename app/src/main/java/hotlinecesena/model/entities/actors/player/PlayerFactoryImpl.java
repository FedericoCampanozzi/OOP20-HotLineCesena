package hotlinecesena.model.entities.actors.player;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Nonnull;

import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.entities.items.CollectibleType;
import hotlinecesena.model.entities.items.WeaponImpl;
import hotlinecesena.model.entities.items.WeaponType;
import hotlinecesena.model.inventory.Inventory;
import hotlinecesena.model.inventory.NaiveInventoryImpl;
import javafx.geometry.Point2D;

/*
 * Basic {@link PlayerFactory} implementation.
 */
public final class PlayerFactoryImpl implements PlayerFactory {

    private static final Point2D STARTING_POS = Point2D.ZERO;
    private static final double STARTING_ANGLE = 270;
    private static final double WIDTH = 0.80; //TODO To be tested
    private static final double HEIGHT = 0.80;
    private static final double SPEED = 5;
    private static final double MAX_HEALTH = 100;
    private final Inventory inv = new NaiveInventoryImpl(
            new WeaponImpl(WeaponType.PISTOL),
            Map.of(CollectibleType.PISTOL_AMMO, 30));
    private final Map<ActorStatus, Double> noise = Map.of(
            ActorStatus.IDLE, 0.0,
            ActorStatus.MOVING, 8.0,
            ActorStatus.DEAD, 0.0
            );

    @Override
    public Player createPlayer() {
        return new PlayerImpl(
                STARTING_POS,
                STARTING_ANGLE,
                WIDTH,
                HEIGHT,
                SPEED,
                MAX_HEALTH,
                inv,
                noise
                );
    }

    @Override
    public Player createPlayer(@Nonnull final Point2D position, final double angle) {
        return new PlayerImpl(
                Objects.requireNonNull(position),
                angle,
                WIDTH,
                HEIGHT,
                SPEED,
                MAX_HEALTH,
                inv,
                noise
                );
    }
}
