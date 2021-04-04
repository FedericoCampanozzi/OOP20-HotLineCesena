package hotlinecesena.model.entities.actors;

import javafx.geometry.Point2D;

/**
 *
 * Models a movement direction to be used with movable entities.
 *
 */
public interface Direction {

    /**
     * @return a {@code Point2D} associated with this direction.
     */
    Point2D get();
}
