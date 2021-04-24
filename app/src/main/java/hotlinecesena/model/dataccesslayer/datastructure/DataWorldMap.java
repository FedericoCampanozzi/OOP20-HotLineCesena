package hotlinecesena.model.dataccesslayer.datastructure;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.util.Pair;
import org.apache.commons.io.FileUtils;
import hotlinecesena.controller.generator.BaseRoomsGeneratorFactoryImpl;
import hotlinecesena.controller.generator.RectangularWorldGeneratorBuilder;
import hotlinecesena.controller.generator.WorldGeneratorBuilder;
import hotlinecesena.model.dataccesslayer.AbstractData;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.utilities.ConverterUtils;

/**
 * A class that store the main information of gameword, so the 
 * map and the min/max cords
 * @author Federico
 *
 */
public class DataWorldMap extends AbstractData {

	private Map<Pair<Integer, Integer>, SymbolsType> worldMap;
	private int xMin, xMax, yMin, yMax;
	private boolean keyObj;
	private WritableImage writtableMiniMap;
	private Pair<Integer, Integer> oldPlyPos;
	
	public DataWorldMap(DataJSONSettings settings) throws IOException {
		WorldGeneratorBuilder sgwb = new RectangularWorldGeneratorBuilder()
				.addSomeBaseRoom(new BaseRoomsGeneratorFactoryImpl().generateRectungolarRoomList(
						settings.getMinRoomWidth(), settings.getMaxRoomWidth(),
						settings.getMinRoomHeight(), settings.getMaxRoomHeight(),
						settings.getMinRoomDoor(), settings.getMaxRoomDoor(),
						settings.getMinBaseRoom(), settings.getMaxBaseRoom()
				))
				.generateRooms(settings.getMinRoom(), settings.getMaxRoom())
				.generatePlayer()
				.generateKeyObject()
				.generateEnemy(settings.getMinEnemyForRoom(), settings.getMaxEnemyForRoom())
				.generateItem(settings.getMinItemForRoom(), settings.getMaxItemForRoom())
				.generateWeapons(settings.getMinRoomWeapons(), settings.getMaxRoomWeapons())
				.generateObstacoles(settings.getMinObstaclesForRoom(), settings.getMaxObstaclesForRoom())
				.finishes()
				.build();
		
		this.worldMap = sgwb.getMap();
		this.xMin = sgwb.getMinX();
		this.xMax = sgwb.getMaxX();
		this.yMin = sgwb.getMinY();
		this.yMax = sgwb.getMaxY();
		this.keyObj = sgwb.isKeyObjectPresent();

		int width = (getMaxX() - getMinX() + 1);
		int height = (getMaxY() - getMinY() + 1);

		writtableMiniMap = new WritableImage(width, height);
		PixelWriter pw = writtableMiniMap.getPixelWriter();

		this.worldMap.entrySet().stream().forEach(itm -> {
			java.awt.Color color;
			int i = itm.getKey().getKey() - getMinX();
			int j = itm.getKey().getValue() - getMinY();
			
			if (i == 0 || i == width-1 || j == 0 || j == height-1) {
				color = java.awt.Color.BLACK;
			} else {
				if (itm.getValue().equals(SymbolsType.VOID) || itm.getValue().equals(SymbolsType.WALKABLE)
						|| itm.getValue().equals(SymbolsType.PLAYER) || itm.getValue().equals(SymbolsType.WALL)) {
					color = itm.getValue().getMiniMapColor();
				} else {
					color = SymbolsType.WALKABLE.getMiniMapColor();
				}
			}
			
			pw.setColor(i, j, ConverterUtils.convertColor(color));
		});
		oldPlyPos = new Pair<>(0, 0);
		write();
	}
	
	/**
	 * The current updated miniMap
	 * @return the minimap as a image
	 */
	public Image getImageVIewUpdated() {
		PixelWriter pw = writtableMiniMap.getPixelWriter();
		pw.setColor(oldPlyPos.getKey()- getMinX(), oldPlyPos.getValue()- getMinY(), ConverterUtils.convertColor(SymbolsType.WALKABLE.getMiniMapColor()));
		oldPlyPos = ConverterUtils.convertPoint2DToPair(JSONDataAccessLayer.getInstance().getPlayer().getPly().getPosition());
		pw.setColor(oldPlyPos.getKey()- getMinX(), oldPlyPos.getValue()- getMinY(), ConverterUtils.convertColor(SymbolsType.PLAYER.getMiniMapColor()));
		return writtableMiniMap;
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
	
	public boolean isKeyObjectPresent() {
		return this.keyObj;
	}
}
