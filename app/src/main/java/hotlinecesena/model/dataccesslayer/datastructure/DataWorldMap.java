package hotlinecesena.model.dataccesslayer.datastructure;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.io.FileUtils;

import hotlinecesena.controller.generator.BaseRoomsGeneratorFactory;
import hotlinecesena.controller.generator.RectangularWorldGeneratorBuilder;
import hotlinecesena.controller.generator.WorldGeneratorBuilder;
import hotlinecesena.model.dataccesslayer.AbstractData;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import javafx.util.Pair;

public class DataWorldMap extends AbstractData {

	private Map<Pair<Integer, Integer>, SymbolsType> worldMap;
	private int xMin, xMax, yMin, yMax;
	
	public DataWorldMap() throws IOException {
		WorldGeneratorBuilder sgwb = new RectangularWorldGeneratorBuilder()
				.addSomeBaseRoom(new BaseRoomsGeneratorFactory().generateRectangolarRoomList(7, 13, 7, 13, 1, 5, 15, 25))
				.generateRooms(10, 20)
				.generatePlayer()
				.generateAmmo(0, 2)
				.generateEnemy(1, 6)
				.generateMedikit(1, 3)
				.generateObstacoles(3, 8)
				.finishes()
				.build();
		
		this.worldMap = sgwb.getMap();
		this.xMin = sgwb.getMinX();
		this.xMax = sgwb.getMaxX();
		this.yMin = sgwb.getMinY();
		this.yMax = sgwb.getMaxY();
		
		write();
	}
	
	@Override
	public void write() throws IOException {
		String debug = "SEED : " + JSONDataAccessLayer.SEED + " MIN:[" + this.getMinX() + "," + this.getMinY() + "] " + "MAX:["
				+ this.getMaxX() + "," + this.getMaxY() + "] \n";

		for (int i = this.getMinX(); i <= this.getMaxX(); i++) {
			for (int j = this.getMinY(); j <= this.getMaxY(); j++) {
				debug += this.getWorldMap().get(new Pair<>(i, j)).getDecotification();
			}

			debug += "\n";
		}

		FileUtils.writeStringToFile(new File(JSONDataAccessLayer.FILE_FOLDER_PATH + "WorldMap.txt"), debug);
	}
	
	public Map<Pair<Integer, Integer>, SymbolsType> getWorldMap(){
		return this.worldMap;
	}

	public int getMinX() {
		return this.xMin;
	}

	public int getMaxX() {
		return this.xMax;
	}

	public int getMinY() {
		return this.yMin;
	}

	public int getMaxY() {
		return this.yMax;
	}
}
