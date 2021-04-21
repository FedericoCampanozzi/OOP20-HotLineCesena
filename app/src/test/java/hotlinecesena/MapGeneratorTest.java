package hotlinecesena;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import hotlinecesena.controller.generator.BaseRoomsGeneratorFactoryImpl;
import hotlinecesena.controller.generator.OctagonalWorldGeneratorBuilder;
import hotlinecesena.controller.generator.QuadraticWorldGeneratorBuilder;
import hotlinecesena.controller.generator.RectangularWorldGeneratorBuilder;
import hotlinecesena.controller.generator.WorldGeneratorBuilder;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.datastructure.*;

public class MapGeneratorTest {
	private static final int PIXEL_SIZE = 10;
	private static final int N_IMAGE = 10;
	private final DataJSONSettings settings = JSONDataAccessLayer.getInstance().getSettings();
	
	@Test
	public void visualTestGenerator() {
		File outFolder = new File(JSONDataAccessLayer.FILE_FOLDER_PATH + "GeneratedMap");
		if (!outFolder.exists()){
			outFolder.mkdirs();
		}
		// clear directory
		for (File f : new File(outFolder.getAbsolutePath()).listFiles()) {
			f.delete();
		}
		
		for (int i = 0; i < N_IMAGE; i++) {
			JSONDataAccessLayer.generateNewSeed();
			generateQuadratic(i);
			generateRectangular(i);
			generateEsagonal(i);
		}

		System.out.println("Finish");
	}

	private void generateQuadratic(int fileIndex) {
		try {
			WorldGeneratorBuilder sgwb = new QuadraticWorldGeneratorBuilder()
					.addSomeBaseRoom(new BaseRoomsGeneratorFactoryImpl().generateQuadraticRoomList(
							settings.getMinRoomWidth(), settings.getMaxRoomWidth(),
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
			saveImageFile(sgwb, JSONDataAccessLayer.FILE_FOLDER_PATH + "GeneratedMap" + File.separator + "TestQ["
					+ JSONDataAccessLayer.SEED + "].png");
		} catch (Exception e) {
			System.out.println("FAIL TO GENERATE QUADRATIC");
		}
	}

	private void generateRectangular(int fileIndex) {
		try {
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
			saveImageFile(sgwb, JSONDataAccessLayer.FILE_FOLDER_PATH + "GeneratedMap" + File.separator + "TestR["
					+ JSONDataAccessLayer.SEED + "].png");
		} catch (Exception e) {
			System.out.println("FAIL TO GENERATE RECTANGULAR");
		}
	}

	private void generateEsagonal(int fileIndex) {
		try {
			WorldGeneratorBuilder sgwb = new OctagonalWorldGeneratorBuilder()
					.addSomeBaseRoom(new BaseRoomsGeneratorFactoryImpl().generateOctagonalRoomList(
							3, 5,
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
			saveImageFile(sgwb, JSONDataAccessLayer.FILE_FOLDER_PATH + "GeneratedMap" + File.separator + "TestE["
					+ JSONDataAccessLayer.SEED + "].png");
		} catch (Exception e) {
			System.out.println("FAIL TO GENERATE ESAGONAL");
		}
	}

	private void saveImageFile(WorldGeneratorBuilder builder, String path) throws IOException {
		int width = PIXEL_SIZE * (builder.getMaxX() - builder.getMinX() + 1);
		int height = PIXEL_SIZE * (builder.getMaxY() - builder.getMinY() + 1);

		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		builder.getMap().entrySet().stream()
		.forEach(itm->{
			g2d.setColor(itm.getValue().getTestColor());
			g2d.fillRect(	PIXEL_SIZE * (itm.getKey().getKey()- builder.getMinX()), 
							PIXEL_SIZE * (itm.getKey().getValue()- builder.getMinY()), 
							PIXEL_SIZE, 
							PIXEL_SIZE);
		});
		g2d.dispose();
		ImageIO.write(bufferedImage, "png", new File(path));
	}

	@Test
	/**
	 * I can create builder like i want :
	 * for example i can call first generateWeapons than generateEnemy or the other way around
	 */
	public void correctBuilderFlow() {
		new QuadraticWorldGeneratorBuilder()
				.addSomeBaseRoom(new BaseRoomsGeneratorFactoryImpl().generateQuadraticRoomList(
						8, 10,
						1, 2,
						3, 4
				))
				.generateRooms(1, 2)
				.generatePlayer()
				.generateEnemy(1, 2)
				.generateItem(1, 2)
				.generateWeapons(1, 2)
				.generateObstacoles(1, 2)
				.finishes()
				.build();
		
		new QuadraticWorldGeneratorBuilder()
		.addSomeBaseRoom(new BaseRoomsGeneratorFactoryImpl().generateQuadraticRoomList(
				8, 10,
				1, 2,
				3, 4
		))
		.generateRooms(1, 2)
		.generatePlayer()
		.generateWeapons(1, 2)
		.generateItem(1, 2)
		.generateEnemy(1, 2)
		.generateObstacoles(1, 2)
		.finishes()
		.build();
		
		
		new QuadraticWorldGeneratorBuilder()
		.addSomeBaseRoom(new BaseRoomsGeneratorFactoryImpl().generateQuadraticRoomList(
				8, 10,
				1, 2,
				3, 4
		))
		.generateRooms(1, 2)
		.generatePlayer()
		.generateObstacoles(1, 2)
		.generateEnemy(1, 2)
		.generateWeapons(1, 2)
		.finishes()
		.generateItem(1, 2)
		.build();
	}
	@Test
	/**
	 * I have to call first addSomeBaseRoom or addSingleRoom
	 * Than generateRooms and than others method
	 */
	public void wrongBuilderFlow() {
		   try{
			   new QuadraticWorldGeneratorBuilder()
			   .generatePlayer()
				.addSomeBaseRoom(new BaseRoomsGeneratorFactoryImpl().generateQuadraticRoomList(
						8, 10,
						1, 2,
						3, 4
				))
				.generateRooms(1, 2)
				.generateEnemy(1, 2)
				.generateItem(1, 2)
				.generateWeapons(1, 2)
				.generateObstacoles(1, 2)
				.finishes()
				.build();
		   }
		   catch(IllegalStateException exc){
		        assertTrue(true);
		   }
		   catch(Exception e){
		        fail("Wrong exception thrown, call first addSomeBaseRoom or addSingleRoom");
		   }
		   
		   try{
			   new QuadraticWorldGeneratorBuilder()
				.addSomeBaseRoom(new BaseRoomsGeneratorFactoryImpl().generateQuadraticRoomList(
						8, 10,
						1, 2,
						3, 4
				))
				.generatePlayer()
				.generateRooms(1, 2)
				.generateEnemy(1, 2)
				.generateItem(1, 2)
				.generateWeapons(1, 2)
				.generateObstacoles(1, 2)
				.finishes()
				.build();
		   }
		   catch(IllegalStateException exc){
		        assertTrue(true);
		   }
		   catch(Exception e){
		        fail("Wrong exception thrown, after call addSomeBaseRoom or addSingleRoom call generateRooms");
		   }
	}
}
