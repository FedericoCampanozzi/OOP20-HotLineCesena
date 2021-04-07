package hotlinecesena.model.dataccesslayer.datastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hotlinecesena.model.dataccesslayer.AbstractData;
import hotlinecesena.model.entities.items.Item;
import javafx.geometry.Point2D;

public class DataItems extends AbstractData {

	private final Map<Point2D, Item> items = new HashMap<>();
	
	public DataItems(DataWorldMap world) {
		
	}
}
