package hotlinecesena.model.dataccesslayer.datastructure;

import java.util.Map;
import java.util.Random;

import javafx.geometry.Point2D;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.model.entities.items.Weapon;
import hotlinecesena.model.entities.items.WeaponImpl;
import hotlinecesena.model.entities.items.WeaponType;
import hotlinecesena.utilities.ConverterUtils;
import static java.util.stream.Collectors.*; 

/**
 * A class that provide to memorized each weapons positions
 * @author Federico
 */
public class DataWeapons {

	private final Map<Point2D, Weapon> weapons;
	
	public DataWeapons(final DataWorldMap world) {
		Random rnd = new Random();
		rnd.setSeed(JSONDataAccessLayer.SEED);
		this.weapons = world.getWorldMap().entrySet().stream()
				.filter(itm -> itm.getValue().equals(SymbolsType.WEAPONS))
				.collect(toMap(
						itm -> ConverterUtils.convertPairToPoint2D(itm.getKey()), 
						itm -> new WeaponImpl(WeaponType.values()[rnd.nextInt(WeaponType.values().length)])
				));
	}

	public Map<Point2D, Weapon> getWeapons() {
		return weapons;
	}
}
