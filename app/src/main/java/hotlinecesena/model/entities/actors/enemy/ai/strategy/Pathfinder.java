package hotlinecesena.model.entities.actors.enemy.ai.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;

import javafx.geometry.Point2D;

public class Pathfinder {
    
    protected static List<Point2D> findPath(Point2D start, Point2D end, Set<Point2D> map) {
        final PriorityQueue<Node> toVisit = new PriorityQueue<>();
        final Set<Node> visited = new HashSet<>();
        // TMP VALUES!
        final Node[][] nodeMap = new Node[1][1];
        Node current;
        
        for(int y=0; y < nodeMap.length; y++) {
            for(int x=0; x < nodeMap[0].length; x++) {
                int heuristic = Math.abs(x - (int) end.getX()) + Math.abs(y - (int) end.getY());
                Node node = new Node(0, heuristic, x, y);
                nodeMap[x][y] = node;
            }
        }
        
        Node startNode = nodeMap[(int) start.getX()][(int) start.getY()];
        Node endNode = nodeMap[(int) end.getX()][(int) end.getY()];
        
        if(startNode.equals(endNode)) {
            return noPathAviable(startNode);
        }
        
        toVisit.add(startNode);
        
        do {
            current = toVisit.poll();
            visited.add(current);
            
            if(current.equals(endNode)) {
                return getPath(current);
            }
            
            for(int y=current.y - 1; y < current.y + 2; y++) {
                for(int x=current.x - 1; x < current.x + 2; x++) {
                    if(map.contains(new Point2D(x,y))) {
                        Node neighbor = nodeMap[x][y];
                        
                        if(visited.contains(neighbor)) {
                            continue;
                        }
                        
                        int calculatedCost = neighbor.heuristic + neighbor.moveCost + neighbor.totalCost;
                        
                        if(calculatedCost < neighbor.totalCost || !toVisit.contains(neighbor)) {
                            neighbor.totalCost = calculatedCost;
                            neighbor.parent = Optional.of(current);
                            
                            if(!toVisit.contains(neighbor)) {
                                toVisit.add(neighbor);
                            }                                                        
                        }
                    }
                }
            }
            
            
        } while(!toVisit.isEmpty());
        
        return noPathAviable(startNode);
    }
    
    private static List<Point2D> noPathAviable(Node start) {
        List<Point2D> path = new ArrayList<>() {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;
        { add(start.getPosition()); }};
        return path;        
    }
    
    private static List<Point2D> getPath(Node current) {
        List<Point2D> path = new ArrayList<>();
        while(!current.parent.isEmpty()) {
            path.add(current.getPosition());
            current = current.parent.get();
        }
        
        Collections.reverse(path);
        return path;
    }

    private static class Node implements Comparable<Node> {

        private final int moveCost;
        private final int heuristic;
        private int totalCost;
        private Optional<Node> parent;
        private final int x;
        private final int y;
        
        public Node(int moveCost, int heuristic, int posX, int posY) {
            this.moveCost = moveCost;
            this.heuristic = heuristic;
            this.x = posX;
            this.y = posY;
            this.parent = Optional.empty();
        }
        
        @Override
        public int compareTo(Node next) {
            return Integer.compare(this.totalCost, next.totalCost);
        }
        
        public Point2D getPosition() {
            return new Point2D(x, y);
        }
        
    }

}
