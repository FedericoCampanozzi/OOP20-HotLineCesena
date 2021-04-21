package hotlinecesena.controller.generator;

import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import hotlinecesena.model.dataccesslayer.SymbolsType;

/**
 * This interface establishes how a wold builder have to be made
 * @author Federico
 */
public interface WorldGeneratorBuilder {
	/**
	 * Add single room in base room list
	 * @param r
	 * @return
	 */
	WorldGeneratorBuilder addSingleBaseRoom(Room r);
	/**
	 * Add a list room in base room list
	 * @param r
	 * @return
	 */
	WorldGeneratorBuilder addSomeBaseRoom(List<Room> list);
	/**
	 * Generate rooms list
	 * @param nRoomsMin
	 * @param nRoomsMax
	 * @return
	 */
	WorldGeneratorBuilder generateRooms(int nRoomsMin, int nRoomsMax);
	/**
	 * Put player symbols in gamemap
	 * @return
	 */
	WorldGeneratorBuilder generatePlayer();
	/**
	 * Put player symbols in gamemap
	 * @return
	 */
	WorldGeneratorBuilder generateKeyObject();
	/**
	 * Put some enemy symbols in gamemap
	 * @return
	 */
	WorldGeneratorBuilder generateEnemy(int minRoom, int maxRoom);
	/**
	 * Put some obstacles symbols in gamemap
	 * @return
	 */
	WorldGeneratorBuilder generateObstacoles(int minRoom, int maxRoom);
	/**
	 * Put some items symbols in gamemap
	 * @return
	 */
	WorldGeneratorBuilder generateItem(int minRoom, int maxRoom);
	/**
	 * Put some weapons symbols in gamemap
	 * @return
	 */
	WorldGeneratorBuilder generateWeapons(int minRoom, int maxRoom);
	/**
	 * Apply some effect of post-processing
	 * @return
	 */
	WorldGeneratorBuilder finishes();
	/**
	 * Return the final builder
	 * @return
	 */
	WorldGeneratorBuilder build();
	/**
	 * Get the low level map result
	 */
	Map<Pair<Integer, Integer>, SymbolsType> getMap();
	/**
	 * Get the minimuos x
	 */
	int getMinX();
	/**
	 * Get the maximous x
	 */
	int getMaxX();
	/**
	 * Get the minimuos y
	 */
	int getMinY();
	/**
	 * Get the maximous y
	 */
	int getMaxY();
	/**
	 * @return true if key object is present, false otherwise
	 */
	boolean isKeyObjectPresent();
}