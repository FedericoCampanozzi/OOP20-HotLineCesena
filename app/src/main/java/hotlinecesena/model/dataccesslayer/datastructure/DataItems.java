package hotlinecesena.model.dataccesslayer.datastructure;

import java.util.Map;
import java.util.Random;
import javafx.geometry.Point2D;
import hotlinecesena.model.dataccesslayer.AbstractData;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.model.entities.items.ItemsType;
import hotlinecesena.utilities.ConverterUtils;
import static java.util.stream.Collectors.*;

/**
 * Class that provides to map for each positions his item
 * @author Federico
 *
 */
public class DataItems extends AbstractData {

	private final Map<Point2D, ItemsType> items;
	
	public DataItems(DataWorldMap world) {
		final Random rnd = new Random();
		rnd.setSeed(JSONDataAccessLayer.SEED);
		items = world.getWorldMap().entrySet().stream()
				.filter(itm -> itm.getValue().equals(SymbolsType.ITEM))
				.collect(toMap(
						itm -> ConverterUtils.convertPairToPoint2D(itm.getKey()), 
						itm -> ItemsType.values()[rnd.nextInt(ItemsType.values().length - 1)] 
				));
		if (world.isKeyObjectPresent()) {
			items.put(ConverterUtils.convertPairToPoint2D(world.getWorldMap().entrySet()
					.stream()
					.filter(itm -> itm.getValue().equals(SymbolsType.KEY_ITEM))
					.collect(toList()).get(0).getKey()),
					ItemsType.BRIEFCASE);
		}
	}
	
	public Map<Point2D, ItemsType> getItems() {
		return items;
	}
}
