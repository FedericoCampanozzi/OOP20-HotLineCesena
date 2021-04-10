package hotlinecesena;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

import hotlinecesena.controller.generator.BaseRoomsGeneratorFactory;
import hotlinecesena.controller.generator.OctagonalWorldGeneratorBuilder;
import hotlinecesena.controller.generator.QuadraticWorldGeneratorBuilder;
import hotlinecesena.controller.generator.RectangularWorldGeneratorBuilder;
import hotlinecesena.controller.generator.WorldGeneratorBuilder;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import javafx.util.Pair;

public class MapGeneratorTest {

	private static final int N_IMAGE = 10;
	
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
			JSONDataAccessLayer.generateDebugSeed();
			generateQuadratic(i);
			generateRectangular(i);
			generateEsagonal(i);
		}

		System.out.println("Finish");
	}

	private static void generateQuadratic(int fileIndex) {
		try {
			WorldGeneratorBuilder sgwb = new QuadraticWorldGeneratorBuilder()
					.addSomeBaseRoom(new BaseRoomsGeneratorFactory().generateQuadraticRoomList(7, 13, 1, 5, 15, 25))
					.generateRooms(10, 20)
					.generatePlayer()
					.generateEnemy(1, 4)
					.generateItem(2, 5)
					.generateObstacoles(3, 8)
					.generateWeapons(0, 2)
					.finishes()
					.build();
			saveImageFile(sgwb, JSONDataAccessLayer.FILE_FOLDER_PATH + "GeneratedMap" + File.separator + "TestQ["
					+ JSONDataAccessLayer.SEED + "].png");
		} catch (Exception e) {
			System.out.println("FAIL TO GENERATE QUADRATIC");
		}
	}

	private static void generateRectangular(int fileIndex) {
		try {
			WorldGeneratorBuilder sgwb = new RectangularWorldGeneratorBuilder()
					.addSomeBaseRoom(new BaseRoomsGeneratorFactory().generateRectangolarRoomList(7, 13, 7, 13, 1, 5, 15, 25))
					.generateRooms(10, 20)
					.generatePlayer()
					.generateEnemy(1, 6)
					.generateItem(2, 5)
					.generateObstacoles(3, 8)
					.finishes()
					.build();
			saveImageFile(sgwb, JSONDataAccessLayer.FILE_FOLDER_PATH + "GeneratedMap" + File.separator + "TestR["
					+ JSONDataAccessLayer.SEED + "].png");
		} catch (Exception e) {
			System.out.println("FAIL TO GENERATE RECTANGULAR");
		}
	}

	private static void generateEsagonal(int fileIndex) {
		try {
			WorldGeneratorBuilder sgwb = new OctagonalWorldGeneratorBuilder()
					.addSomeBaseRoom(new BaseRoomsGeneratorFactory().generateOctagonalRoomList(3, 7, 4, 8, 15, 25))
					.generateRooms(10, 20)
					.generatePlayer()
					.generateEnemy(1, 6)
					.generateItem(2, 5)
					.generateObstacoles(3, 8)
					.finishes()
					.build();
			saveImageFile(sgwb, JSONDataAccessLayer.FILE_FOLDER_PATH + "GeneratedMap" + File.separator + "TestE["
					+ JSONDataAccessLayer.SEED + "].png");
		} catch (Exception e) {
			System.out.println("FAIL TO GENERATE ESAGONAL");
		}
	}

	private static void saveImageFile(WorldGeneratorBuilder builder, String path) throws IOException {
		int pixSize = 9;
		int width = pixSize * (builder.getMaxX() - builder.getMinX() + 1);
		int height = pixSize * (builder.getMaxY() - builder.getMinY() + 1);

		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bufferedImage.createGraphics();

		for (int i = builder.getMinX(); i <= builder.getMaxX(); i++) {
			for (int j = builder.getMinY(); j <= builder.getMaxY(); j++) {
				int tI = i - builder.getMinX();
				int tY = j - builder.getMinY();
				if (builder.getMap().containsKey(new Pair<>(i, j))) {
					g2d.setColor(builder.getMap().get(new Pair<>(i, j)).getColor());
					g2d.fillRect(pixSize * tI, pixSize * tY, pixSize, pixSize);
				} else {
					g2d.setColor(Color.BLACK);
					g2d.fillRect(pixSize * tI, pixSize * tY, pixSize, pixSize);
				}
			}
		}

		g2d.dispose();
		ImageIO.write(bufferedImage, "png", new File(path));

	}

}
