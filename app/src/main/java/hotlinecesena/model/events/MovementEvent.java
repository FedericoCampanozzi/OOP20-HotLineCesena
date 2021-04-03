package hotlinecesena.model.events;

import hotlinecesena.model.entities.Entity;
import javafx.geometry.Point2D;

public class MovementEvent extends AbstractEvent {

    private final Point2D newPos;
    private final double noiseRadius;

    public MovementEvent(final Entity source, final Point2D newPos, final double noiseRadius) {
        super(source);
        this.newPos = newPos;
        this.noiseRadius = noiseRadius;
    }

    public Point2D getPosition() {
        return this.newPos;
    }

    public double getNoiseRadius() {
        return this.noiseRadius;
    }
}
