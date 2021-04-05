package hotlinecesena.model.dataccesslayer;

import java.io.File;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;

import hotlinecesena.model.dataccesslayer.datastructure.*;

public class JSONDataAccessLayer {
	
	public static final String FILE_FOLDER_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator +
			"main" + File.separator +"resources" + File.separator + "File" + File.separator;
	public static long SEED;
	private static JSONDataAccessLayer singleton = null;
	
	private DataJSONSettings settings;
	private DataJSONRanking ranking;
	private DataJSONLanguages languages;
	private DataWorldMap world;
	private DataJSONPlayer player;
	private DataJSONEnemy enemy;
	private DataGUIPath guiPath;
	
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

	public DataJSONPlayer getPlayer() {
		return player;
	}

	public DataJSONEnemy getEnemy() {
		return enemy;
	}

	public DataGUIPath getGuiPath() {
		return guiPath;
	}
	
	private JSONDataAccessLayer() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			this.settings = mapper.readValue(new File(JSONDataAccessLayer.FILE_FOLDER_PATH + "settings.json"), DataJSONSettings.class);
			this.ranking = mapper.readValue(new File(JSONDataAccessLayer.FILE_FOLDER_PATH + "ranking.json"), DataJSONRanking.class);
			this.languages = mapper.readValue(new File(JSONDataAccessLayer.FILE_FOLDER_PATH + "languages.json"), DataJSONLanguages.class);
			this.world = new DataWorldMap();
			this.player = new DataJSONPlayer(this.world, this.settings);
			this.guiPath = new DataGUIPath();
			this.enemy = new DataJSONEnemy(this.world, this.settings);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static JSONDataAccessLayer getInstance() {
		if(singleton == null) {
			SEED = new Random().nextLong();
			//SEED = 12254;
			singleton = new JSONDataAccessLayer();
		}
		return singleton;
	}
}
