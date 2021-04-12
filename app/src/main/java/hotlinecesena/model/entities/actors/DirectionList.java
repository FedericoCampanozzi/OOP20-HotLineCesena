package hotlinecesena.model.entities.actors;

import javafx.geometry.Point2D;

/**
 *
 * Collection of movement directions.
 *
 */
public enum DirectionList implements Direction {

    /**
     * North direction.
     */
    NORTH(new Point2D(0, -1)),

    /**
     * South direction.
     */
    SOUTH(new Point2D(0, 1)),

    /**
     * East direction.
     */
    EAST(new Point2D(1, 0)),

    /**
     * West direction.
     */
    WEST(new Point2D(-1, 0)),

    /**
     * No direction.
     */
    NONE(Point2D.ZERO);

    private Point2D dir;

    /**
     *
     * @param dir a {@code Point2D} to be associated with a constant.
     */
    DirectionList(final Point2D dir) {
        this.dir = dir;
    }

    @Override
    public Point2D get() {
        return dir;
    }
}
