package hotlinecesena.view.entities;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

/**
 * 
 * 
 *
 */
public interface Sprite {

    void updatePosition(Point2D entityPos);

    void updateRotation(double entityAngle);

    void updateImage(Image image);

    Point2D getSpritePosition();
}
