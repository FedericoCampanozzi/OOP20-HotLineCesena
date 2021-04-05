package hotlinecesena.model.entities.actors.enemy.ai.strategy;

import java.util.Set;

import hotlinecesena.model.entities.actors.DirectionList;
import javafx.geometry.Point2D;

/**
 * This instance of {@link MovementStrategy} prevents the enemy implementing it
 * from moving, making him remain stationary
 */
public class Idle implements MovementStrategy{

    @Override
    public Point2D move(final Point2D enemy, final Point2D player,
            final boolean pursuit, final Set<Point2D> map) {

        return DirectionList.NONE.get();
    }
}
