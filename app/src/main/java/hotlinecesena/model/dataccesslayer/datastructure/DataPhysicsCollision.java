package hotlinecesena.model.dataccesslayer.datastructure;

import hotlinecesena.model.dataccesslayer.AbstractData;
import javafx.geometry.BoundingBox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataPhysicsCollision extends AbstractData {
	
	private final List<BoundingBox> obstacles = new ArrayList<>();
	
	public DataPhysicsCollision(DataWorldMap world) throws IOException {
		
	}

	public List<BoundingBox> getObstacles() {
		return obstacles;
	}
}
