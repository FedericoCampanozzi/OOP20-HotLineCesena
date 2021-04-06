package hotlinecesena.controller.physics;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import javafx.geometry.Point2D;

public class EnemyPhysics {
    
    public static boolean isMoveAllowed(final Point2D current, final Point2D next, 
            final Set<Point2D> map) {

        final Set<Point2D> allowedMoveSet = new HashSet<>() {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

        {
           addAll(map); 
        }};

        allowedMoveSet.removeAll(JSONDataAccessLayer.getInstance()
                .getEnemy()
                .getEnemies()
                .stream()
                    .filter(e -> !e.getPosition().equals(current))
                    .map(e -> e.getPosition())
                    .collect(Collectors.toSet()));
        allowedMoveSet.remove(JSONDataAccessLayer.getInstance()
                .getPlayer()
                .getPly()
                    .getPosition());

        return allowedMoveSet.contains(sum(current, next));
    }

    public static boolean isWallInBetween(final Point2D current, final Point2D target,
            final Set<Point2D> walls) {

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

    private static Point2D sum(final Point2D arg1, final Point2D arg2) {
        return new Point2D((int)arg1.getX() + (int)arg2.getX(),
                (int)arg1.getY() + (int)arg2.getY());
    }

    public static int distance(final Point2D arg1, final Point2D arg2) {
        return (int) (Math.abs(arg1.getX() - arg2.getX()) +
                Math.abs(arg1.getY() - arg2.getY()));
    }
}
