package hotlinecesena.model.entities.actors.enemy.ai;

import java.util.Set;

import javafx.geometry.Point2D;

public interface AI {

    public void setEnemyPos (Point2D pos);
    
    public Point2D getNextMove(Point2D target, boolean pursuit, Set<Point2D> map);
    
    public double getRotation(Point2D target, boolean pursuit);
    
    public boolean isInPursuit(Point2D target, double noise);
    
    public boolean isShooting(Point2D target);
    
    public Set<Point2D> getWallSet();
}
