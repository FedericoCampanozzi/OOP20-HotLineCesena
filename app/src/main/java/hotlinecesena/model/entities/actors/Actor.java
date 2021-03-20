package hotlinecesena.model.entities.actors;

import hotlinecesena.model.entities.Entity;
import hotlinecesena.model.inventory.Inventory;
import javafx.geometry.Point2D;

public interface Actor extends Entity {

    void move(Point2D direction, Point2D velocity);

    void rotate(double angle);
    
    void attack();
    
    void reload();

    double getAngle();

    void takeDamage(double damage);
    
    double getCurrentHealth();
    
    Inventory getInventory();

    ActorState getState();
    
    void setState(ActorState s);
}
