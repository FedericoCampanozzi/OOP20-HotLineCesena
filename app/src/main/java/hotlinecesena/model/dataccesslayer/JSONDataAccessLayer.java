package hotlinecesena.model.dataccesslayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import org.apache.commons.io.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import hotlinecesena.model.dataccesslayer.datastructure.*;

/**
 * End implementation of DataAccessLayer.
 * this class is a composite class of all datastructure classes
 * @author Federico
 *
 */
public class JSONDataAccessLayer implements DataAccessLayer {
	/**
	 * Default separator for current operative system
	 */
	public static String SYS_SEP = File.separator;
	/**
	 * Default separator for jar
	 */
	public static String JAR_SEP = "/";
	/**
	 * Our path where file are saved
	 */
	public static final String FILE_FOLDER_PATH = System.getProperty("user.home") + SYS_SEP + ".HotlineCesena" + SYS_SEP + "File" + SYS_SEP;
	/**
	 * Current seed, that need to be shared in all parts of program
	 */
	public static long SEED = 0;
	private static DataAccessLayer singleton = null;
	private DataJSONSettings settings;
	private DataJSONRanking ranking;
	private DataJSONLanguages languages;
	private DataWorldMap world;
	private DataPlayer player;
	private DataEnemy enemy;
	private DataPhysicsCollision physics;
	private DataItems items;
	private DataBullet bullets;
	private DataWeapons weapons;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DataWeapons getWeapons() {
		return weapons;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DataBullet getBullets() {
		return bullets;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DataItems getDataItems() {
		return items;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DataPhysicsCollision getPhysics() {
		return physics;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DataJSONSettings getSettings() {
		return settings;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DataJSONRanking getRanking() {
		return ranking;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public DataJSONLanguages getLanguages() {
		return languages;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DataWorldMap getWorld() {
		return world;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DataPlayer getPlayer() {
		return player;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DataEnemy getEnemy() {
		return enemy;
	}

	private JSONDataAccessLayer() {
		try {

			checkDirExistence(System.getProperty("user.home") + SYS_SEP + ".HotlineCesena");
			checkDirExistence(System.getProperty("user.home") + SYS_SEP + ".HotlineCesena" + SYS_SEP + "File");
			checkFileExistence("File" + JAR_SEP + "settings.json", JSONDataAccessLayer.FILE_FOLDER_PATH + "settings.json");
			checkFileExistence("File" + JAR_SEP + "ranking.json", JSONDataAccessLayer.FILE_FOLDER_PATH + "ranking.json");
			checkFileExistence("File" + JAR_SEP + "languages.json", JSONDataAccessLayer.FILE_FOLDER_PATH + "languages.json");
			ObjectMapper mapper = new ObjectMapper();
			this.settings = mapper.readValue(new File(JSONDataAccessLayer.FILE_FOLDER_PATH + "settings.json"), DataJSONSettings.class);
			SEED = settings.getNiceseeds().get(new Random().nextInt(settings.getNiceseeds().size()));
			this.ranking = mapper.readValue(new File(JSONDataAccessLayer.FILE_FOLDER_PATH + "ranking.json"), DataJSONRanking.class);
			this.languages = mapper.readValue(new File(JSONDataAccessLayer.FILE_FOLDER_PATH + "languages.json"), DataJSONLanguages.class);
			this.world = new DataWorldMap(this.settings);
			this.enemy = new DataEnemy(this.world);
			this.items = new DataItems(this.world);
			this.bullets = new DataBullet();
			this.physics = new DataPhysicsCollision(this.world, this.settings);
			this.weapons = new DataWeapons(this.world);
			this.player = new DataPlayer(this.world, this.enemy, this.physics, this.items, this.weapons);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void checkDirExistence(String path) {
		File dir = new File(path);
		if (!(dir.exists())) {
			dir.mkdir();
		}
	}

	private void checkFileExistence(String jarPath, String systemPath) {
		File file = new File(systemPath);

		if (!(file.exists())) {
			final ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream stream = loader.getResourceAsStream(jarPath);
			try {
				OutputStream outputStream = new FileOutputStream(file);
				file.createNewFile();
				IOUtils.copy(stream, outputStream);
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the current instance of JSONDataAccessLayer
	 */
	public static DataAccessLayer getInstance() {
		if (singleton == null) {
			singleton = new JSONDataAccessLayer();
		}
		return singleton;
	}

	/**
	 * change current seed, this method is
	 * utility method
	 */
	public static void generateNewSeed() {
		SEED = new Random().nextLong();
	}

	/**
	 * Reload instance, that means regenerate a new map and
	 * his data associated
	 */
	public static void newInstance() {
		SEED = new Random().nextLong();
		singleton = new JSONDataAccessLayer();
	}
}
