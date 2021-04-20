package hotlinecesena.controller.generator;

import java.util.Map;

import hotlinecesena.model.dataccesslayer.SymbolsType;
import javafx.util.Pair;

/**
 * Define a Room contract
 * @author Federico
 *
 */
public interface Room {
	
	/**
	 * define how generate a class [template method]
	 */
	public void generate();
	/**
	 * @return return the center of room
	 */
	public Pair<Integer, Integer> getCenter();
	/**
	 * set the center of room
	 */
	public void setCenter(Pair<Integer, Integer> center);
	/**
	 * @return get the low-level definitions
	 */
	public Map<Pair<Integer, Integer>, SymbolsType> getMap();
	/**
	 * @return a copy of this room in a new object [template method]
	 */
	public Room deepCopy();
}
