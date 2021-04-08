package hotlinecesena.model.dataccesslayer;

import java.io.File;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;

import hotlinecesena.model.dataccesslayer.datastructure.*;

public class JSONDataAccessLayer {
	
	public static final String FILE_FOLDER_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator +
			"main" + File.separator +"resources" + File.separator + "File" + File.separator;
	public static long SEED = 0;
	private static JSONDataAccessLayer singleton = null;
	
	private DataJSONSettings settings;
	private DataJSONRanking ranking;
	private DataJSONLanguages languages;
	private DataWorldMap world;
	private DataPlayer player;
	private DataEnemy enemy;
	private DataGUIPath guiPath;
	private DataPhysicsCollision physics;
	private DataItems items;
	private DataBullet bullets;
	
	public DataBullet getBullets() {
		return bullets;
	}
	
	public DataItems getDataItems() {
		return items;
	}
	
	public DataPhysicsCollision getPhysics() {
		return physics;
	}
	
	public DataJSONSettings getSettings() {
		return settings;
	}

	public DataJSONRanking getRanking() {
		return ranking;
	}

	public DataJSONLanguages getLanguages() {
		return languages;
	}
	
	public DataWorldMap getWorld() {
		return world;
	}

	public DataPlayer getPlayer() {
		return player;
	}

	public DataEnemy getEnemy() {
		return enemy;
	}

	public DataGUIPath getGuiPath() {
		return guiPath;
	}
	
	private JSONDataAccessLayer() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			this.settings = mapper.readValue(new File(JSONDataAccessLayer.FILE_FOLDER_PATH + "settings.json"), DataJSONSettings.class);
			SEED = settings.getNiceseeds().get(new Random().nextInt(settings.getNiceseeds().size()));
			this.ranking = mapper.readValue(new File(JSONDataAccessLayer.FILE_FOLDER_PATH + "ranking.json"), DataJSONRanking.class);
			this.languages = mapper.readValue(new File(JSONDataAccessLayer.FILE_FOLDER_PATH + "languages.json"), DataJSONLanguages.class);
			this.world = new DataWorldMap();
			this.guiPath = new DataGUIPath();
			this.player = new DataPlayer(this.world, this.settings);
			this.enemy = new DataEnemy(this.world, this.settings);
			this.items = new DataItems(this.world);
			this.bullets = new DataBullet();
			this.physics = new DataPhysicsCollision(this.world, this.settings);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static JSONDataAccessLayer getInstance() {
		if(singleton == null) {
			singleton = new JSONDataAccessLayer();
		}
		return singleton;
	}
	
	public static void generateDebugSeed() {
		SEED = new Random().nextLong();
	}
}
