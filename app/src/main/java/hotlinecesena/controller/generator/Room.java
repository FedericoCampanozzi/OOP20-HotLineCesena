package hotlinecesena.controller.generator;

import java.util.Map;
import javafx.util.Pair;
import hotlinecesena.model.dataccesslayer.SymbolsType;

/**
 * Define a room contract
 * @author Federico
 */
public interface Room {
	
	/**
	 * Define how generate a class
	 */
	public void generate();
	/**
	 * @return The center of room
	 */
	public Pair<Integer, Integer> getCenter();
	/**
	 * Set the center of room
	 */
	public void setCenter(Pair<Integer, Integer> center);
	/**
	 * @return Get the low-level definitions
	 */
	public Map<Pair<Integer, Integer>, SymbolsType> getMap();
	/**
	 * @return A copy of this room in a new object
	 */
	public Room deepCopy();
}
