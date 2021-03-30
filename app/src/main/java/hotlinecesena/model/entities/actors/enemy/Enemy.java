package hotlinecesena.model.entities.actors.enemy;

import java.util.Set;

import hotlinecesena.model.entities.actors.enemy.ai.AI;
import javafx.geometry.Point2D;

public interface Enemy {

    public AI getAI();
    
    public Set<Point2D> getWalkable();
    
    public boolean isChasingTarget();

}
