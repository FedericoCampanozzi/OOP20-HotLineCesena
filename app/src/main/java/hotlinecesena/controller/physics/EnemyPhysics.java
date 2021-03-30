package hotlinecesena.controller.physics;

import java.util.HashSet;
import java.util.Set;
import javafx.geometry.Point2D;

public class EnemyPhysics {
    
    public static boolean isMoveAllowed(Point2D current, Point2D next, Set<Point2D> map) {
        Set<Point2D> allowedMoveSet = new HashSet<>() {/**
             * 
             */
            private static final long serialVersionUID = 1L;

        {
           addAll(map); 
        }};
        
        //allowedMoveSet.removeAll(enemyList.stream().map(e -> e.getEnemyPosition()).collect(Collectors.toSet()));
        //allowedMoveSet.remove(player);
        
        return allowedMoveSet.contains(sum(current, next));
    }
    
    public static boolean isWallInBetween(Point2D current, Point2D target, Set<Point2D> walls) {
        int x =(int) current.getX();
        int y =(int) current.getY();
        int distanceX, distanceY;
        
        distanceX =(int) (current.getX() - target.getX());
        distanceY =(int) (current.getY() - target.getY());
        
        distanceY = distanceY == 0 ? 1 : distanceY;
        
        while((distanceX > 0 ? x > target.getX() : x < target.getX()) 
                || (distanceY > 0 ? y > target.getY() : y < target.getY())) {
            
            if(walls.contains(new Point2D(x,y))) {
                return true;
            }
            
            if(distanceX != 0) {
                x = distanceX > 0 ? x-1 : x+1;
            }
            if(distanceY != 0) {
                y = distanceY > 0 ? y-1 : y+1;
            }

        }

        return false;
    }
    
    public static Point2D sum(Point2D arg1, Point2D arg2) {
        return new Point2D(arg1.getX() + arg2.getX(), arg1.getY() + arg2.getY());
    }
    
    public static int distance(Point2D arg1, Point2D arg2) {
        return (int) (Math.abs(arg1.getX() - arg2.getX()) + Math.abs(arg1.getY() - arg2.getY()));
    }
}
