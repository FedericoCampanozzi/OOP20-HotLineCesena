package hotlinecesena.model.camera;

import hotlinecesena.model.entities.Entity;
import javafx.geometry.Point2D;

public interface Camera {

    void attachTo(Entity entity) throws NullPointerException;

    Point2D getCameraPosition();

    void update(double timeElapsed);
}
