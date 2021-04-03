package hotlinecesena.model.entities.actors;

import javafx.geometry.Point2D;

public enum DirectionList implements Direction {

    NORTH(new Point2D(0, -1)),
    SOUTH(new Point2D(0, 1)),
    EAST(new Point2D(1, 0)),
    WEST(new Point2D(-1, 0)),
    NONE(Point2D.ZERO);

    private Point2D dir;

    DirectionList(final Point2D dir) {
        this.dir = dir;
    }

    @Override
    public Point2D get() {
        return dir;
    }
}
