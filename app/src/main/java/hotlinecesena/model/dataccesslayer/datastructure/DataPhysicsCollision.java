package hotlinecesena.model.dataccesslayer.datastructure;

import java.io.IOException;
import java.util.List;
import javafx.geometry.BoundingBox;
import hotlinecesena.model.dataccesslayer.AbstractData;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.utilities.Utilities;
import static java.util.stream.Collectors.*;

public class DataPhysicsCollision extends AbstractData {
	
	private final List<BoundingBox> obstacles;
	
	public DataPhysicsCollision(DataWorldMap world, DataJSONSettings settings) throws IOException {
		obstacles = world.getWorldMap().entrySet().stream()
				.filter(itm -> itm.getValue().equals(SymbolsType.WALL) || itm.getValue().equals(SymbolsType.OBSTACOLES))
				.map(itm -> {
					javafx.geometry.Point2D pt = Utilities.convertPairToPoint2D(itm.getKey(), settings.getTileSize());
					return new BoundingBox(pt.getX(), pt.getY(), settings.getTileSize(), settings.getTileSize());
				}).collect(toList());
	}

	public List<BoundingBox> getObstacles() {
		return obstacles;
	}
}
