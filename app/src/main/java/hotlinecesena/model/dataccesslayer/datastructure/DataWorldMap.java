package hotlinecesena.model.dataccesslayer.datastructure;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.io.FileUtils;

import hotlinecesena.controller.generator.BaseRoomsGeneratorFactory;
import hotlinecesena.controller.generator.WorldGeneratorBuilder;
import hotlinecesena.controller.generator.WorldGeneratorBuilderImpl;
import hotlinecesena.model.dataccesslayer.AbstractData;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import javafx.util.Pair;

public class DataWorldMap extends AbstractData {

	private Map<Pair<Integer, Integer>, Character> worldMap;
	private int xMin, xMax, yMin, yMax;
	
	public DataWorldMap() {
		final WorldGeneratorBuilder sgwb = new WorldGeneratorBuilderImpl(JSONDataAccessLayer.SEED)
				.addSomeBaseRoom(new BaseRoomsGeneratorFactory().generateQuadraticList(JSONDataAccessLayer.SEED, 5,5, 11, 11, 15, 20))
				.generateRooms(20, 30)
				.generateEnemy(0.05f, 4)
				.generatePlayer()
				.generateDecorations(0.01f)
				.finishes()
				.build();
		
		this.worldMap = sgwb.getMap();
		this.xMin = sgwb.getMinX();
		this.xMax = sgwb.getMaxX();
		this.yMin = sgwb.getMinY();
		this.yMax = sgwb.getMaxY();
	}
	
	@Override
	public void write() throws IOException {
		String debug = "SEED : " + JSONDataAccessLayer.SEED + " MIN:[" + this.getMinX() + "," + this.getMinY() + "] " + "MAX:["
				+ this.getMaxX() + "," + this.getMaxY() + "] \n";

		for (int i = this.getMinX(); i <= this.getMaxX(); i++) {
			for (int j = this.getMinY(); j <= this.getMaxY(); j++) {
				debug += this.getWorldMap().get(new Pair<>(i, j));
			}

			debug += "\n";
		}

		FileUtils.writeStringToFile(new File(JSONDataAccessLayer.FILE_FOLDER_PATH + "WorldMap.txt"), debug);
	}
	
	public Map<Pair<Integer, Integer>, Character> getWorldMap(){
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
