package hotlinecesena.model.entities.actors.enemy.ai.strategy;

import java.util.Random;
import java.util.Set;

import hotlinecesena.controller.physics.EnemyPhysics;
import hotlinecesena.model.entities.actors.DirectionList;
import javafx.geometry.Point2D;

/**
 * This instance of {@link MovementStrategy} makes the enemy move in a random
 * fashion, picking always a random value from the possible ones in {@link DirectionList}.
 */
public final class RandomMovement implements MovementStrategy {

    private DirectionList nextMove;

    @Override
    public Point2D move(final Point2D enemy, final Point2D player,
            final boolean pursuit, final Set<Point2D> map) {

        final int pick = new Random().nextInt(DirectionList.values().length - 1);
        this.nextMove = DirectionList.values()[pick];

        return EnemyPhysics.isMoveAllowed(enemy, this.nextMove.get(), map)
                ? this.nextMove.get() : DirectionList.NONE.get();
    }
}
