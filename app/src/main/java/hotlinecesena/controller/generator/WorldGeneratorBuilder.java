package hotlinecesena.controller.generator;

import java.util.List;
import java.util.Map;

import hotlinecesena.model.dataccesslayer.SymbolsType;
import javafx.util.Pair;

public interface WorldGeneratorBuilder {
	
	WorldGeneratorBuilder addSingleBaseRoom(Room r);
	
	WorldGeneratorBuilder addSomeBaseRoom(List<Room> list);
	
	WorldGeneratorBuilder generateRooms(int nRoomsMin, int nRoomsMax);
	
	WorldGeneratorBuilder generatePlayer();
	
	WorldGeneratorBuilder generateKeyObject();
	
	WorldGeneratorBuilder generateEnemy(int minRoom, int maxRoom);

	WorldGeneratorBuilder generateObstacoles(int minRoom, int maxRoom);

	WorldGeneratorBuilder generateItem(int minRoom, int maxRoom);
	
	WorldGeneratorBuilder generateWeapons(int minRoom, int maxRoom);

	public WorldGeneratorBuilder finishes();

	WorldGeneratorBuilder build();

	Map<Pair<Integer, Integer>, SymbolsType> getMap();

	int getMinX();

	int getMaxX();

	int getMinY();

	int getMaxY();
}