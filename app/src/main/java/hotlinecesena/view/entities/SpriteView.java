package hotlinecesena.view.entities;

import javafx.geometry.Point2D;

/**
 * 
 * 
 *
 */
public interface SpriteView {

    void update(Point2D entityPos, double entityAngle);
    
    Point2D getSpritePosition();
}
