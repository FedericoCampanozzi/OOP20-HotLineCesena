package hotlinecesena.model.entities.actors.enemy;

import java.util.Set;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.actors.enemy.ai.AI;
import javafx.geometry.Point2D;

public interface Enemy extends Actor {

    public AI getAI();
    
    public void setIsInPursuit(boolean pursuit);
    
    public Set<Point2D> getWalkable();
    
    public boolean isChasingTarget();

}
