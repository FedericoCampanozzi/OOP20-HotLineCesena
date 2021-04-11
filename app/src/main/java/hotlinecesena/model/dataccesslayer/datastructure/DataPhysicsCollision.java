package hotlinecesena.model.dataccesslayer.datastructure;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.List;

import hotlinecesena.model.dataccesslayer.AbstractData;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.model.entities.AbstractEntity;
import hotlinecesena.utilities.Utilities;
import javafx.geometry.Point2D;

public class DataPhysicsCollision extends AbstractData {

    private final List<Obstacle> obstacles;

    public DataPhysicsCollision(final DataWorldMap world, final DataJSONSettings settings) throws IOException {
        obstacles = world.getWorldMap().entrySet().stream()
                .filter(itm -> itm.getValue().equals(SymbolsType.WALL) || itm.getValue().equals(SymbolsType.OBSTACOLES))
                .map(itm -> {
                    final javafx.geometry.Point2D pt = Utilities.convertPairToPoint2D(itm.getKey());
                    return new Obstacle(pt, settings.getObstaclesEdge(), settings.getObstaclesEdge());
                }).collect(toList());
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public class Obstacle extends AbstractEntity {
        protected Obstacle(final Point2D position, final double width, final double height) {
            super(position, width, height);
        }
    }
}