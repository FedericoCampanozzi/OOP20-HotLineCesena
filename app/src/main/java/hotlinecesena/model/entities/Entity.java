package hotlinecesena.model.entities;

import hotlinecesena.model.events.Publisher;
import javafx.geometry.Point2D;

/**
 *
 * Represents a basic game entity characterized by a
 * position, a width and a height.
 *
 */
public interface Entity extends Publisher {

    /**
     * Gets this entity's current coordinates on the game map.
     *
     * @return this entity's coordinates.
     */
    Point2D getPosition();

    /**
     * Gets this entity's width.
     * @return this entity's width.
     */
    double getWidth();

    /**
     * Gets this entity's height.
     * @return this entity's height.
     */
    double getHeight();
}
