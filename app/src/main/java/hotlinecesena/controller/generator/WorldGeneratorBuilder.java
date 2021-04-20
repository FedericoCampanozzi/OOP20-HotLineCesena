package hotlinecesena.controller.generator;

import java.util.List;
import java.util.Map;

import hotlinecesena.model.dataccesslayer.SymbolsType;
import javafx.util.Pair;

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
	 * put player symbols in gamemap
	 * @return
	 */
	WorldGeneratorBuilder generatePlayer();
	/**
	 * put player symbols in gamemap
	 * @return
	 */
	WorldGeneratorBuilder generateKeyObject();
	/**
	 *  put some enemy symbols in gamemap
	 * @return
	 */
	WorldGeneratorBuilder generateEnemy(int minRoom, int maxRoom);
	/**
	 * put some obstacles symbols in gamemap
	 * @return
	 */
	WorldGeneratorBuilder generateObstacoles(int minRoom, int maxRoom);
	/**
	 * put some items symbols in gamemap
	 * @return
	 */
	WorldGeneratorBuilder generateItem(int minRoom, int maxRoom);
	/**
	 * put some weapons symbols in gamemap
	 * @return
	 */
	WorldGeneratorBuilder generateWeapons(int minRoom, int maxRoom);
	/**
	 * apply some effect of post-processing
	 * @return
	 */
	WorldGeneratorBuilder finishes();
	/**
	 * return the final builder
	 * @return
	 */
	WorldGeneratorBuilder build();
	/**
	 * get the result
	 * @return
	 */
	Map<Pair<Integer, Integer>, SymbolsType> getMap();

	int getMinX();

	int getMaxX();

	int getMinY();

	int getMaxY();
	
	boolean isKeyObjectPresent();
}