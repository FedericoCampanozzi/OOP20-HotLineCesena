package hotlinecesena.model.entities.actors.enemy.ai.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;

import javafx.geometry.Point2D;

/**
 * Pathfinder is the actual class that returns a path from a node to another
 * which implements an algorithm called A* to function
 */
public class Pathfinder {

    /**
     * Return an ordered list from the starting position to the end position
     * by implementing a path finding algorithm. The algorithm once created
     * all of the possible nodes based on the game map dimension, from the
     * starting position traverses all of the neighbors nodes by updating their
     * respective {@code heuristic}s if a shorter path is found, till, if it's
     * possible, it reaches the end node.
     * @param start the starting position
     * @param end the position that has to be reached
     * @param map collections of all the points that are walkable by the enemy
     * @return a list of points that need to be traversed
     * to reach the desired end
     * @see Node
     */
    protected static List<Point2D> findPath(final Point2D start,
            final Point2D end, final Set<Point2D> map) {

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

        final Node startNode = nodeMap[(int) start.getX()][(int) start.getY()];
        final Node endNode = nodeMap[(int) end.getX()][(int) end.getY()];

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
                        final Node neighbor = nodeMap[x][y];

                        if(visited.contains(neighbor)) {
                            continue;
                        }

                        final int calculatedCost = neighbor.heuristic +
                                                    neighbor.moveCost + 
                                                    neighbor.totalCost;

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

    /**
     * Returns a list of points containing only the starting
     * position, due to the fact that there was no available
     * path to reach the end position from the start
     * @param start the starting node of the research
     * @return a list of points that need to be traversed
     * to reach the desired end
     */
    private static List<Point2D> noPathAviable(Node start) {
        List<Point2D> path = new ArrayList<>() {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;
        { add(start.getPosition()); }};
        return path;        
    }

    /**
     * Returns a list of points containing all the points traversed
     * to reach the end position
     * @param current the current node which coincide with
     * the last node if the search as been completed successfully
     * @return a list of points that need to be traversed
     * to reach the desired end
     */
    private static List<Point2D> getPath(Node current) {
        List<Point2D> path = new ArrayList<>();
        while(!current.parent.isEmpty()) {
            path.add(current.getPosition());
            current = current.parent.get();
        }

        Collections.reverse(path);
        return path;
    }

    /**
     * Each position of the game map is mapped as a {@code Node}.
     * A node is a combination of {@code Position2D}, {@code totalCost}
     * and dependency to another {@code Optional} node, which is
     * its ordered predecessor, needed to build the path from the
     * starting node to the end node once the algorithm has found a
     * path
     * @see Comparable
     * @see Optional
     */
    private static class Node implements Comparable<Node> {

        private final int moveCost;
        private final int heuristic;
        private int totalCost;
        private Optional<Node> parent;
        private final int x;
        private final int y;

        /**
         * Class constructor
         * @param moveCost the moveCost of this node
         * @param heuristic the heuristics of this node
         * @param posX the current x position of the node
         * @param posY the current y position of the node
         */
        public Node(final int moveCost, final int heuristic,
                final int posX, final int posY) {

            this.moveCost = moveCost;
            this.heuristic = heuristic;
            this.x = posX;
            this.y = posY;
            this.parent = Optional.empty();
        }

        /**
         * Compare this object {@code totalCost} with the
         * next {@code totalCost}, needed to create an 
         * ordered list of nodes
         * @param next the {@code Node} to be compared to
         */
        @Override
        public int compareTo(final Node next) {
            return Integer.compare(this.totalCost, next.totalCost);
        }

        /**
         * Gets the position of {@code Node} based on its {@code x}
         * and {@code y}
         * @return the position of this {@code Node}
         */
        public Point2D getPosition() {
            return new Point2D(x, y);
        }
    }
}
