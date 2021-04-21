package hotlinecesena.model.dataccesslayer;

import hotlinecesena.model.dataccesslayer.datastructure.DataBullet;
import hotlinecesena.model.dataccesslayer.datastructure.DataEnemy;
import hotlinecesena.model.dataccesslayer.datastructure.DataItems;
import hotlinecesena.model.dataccesslayer.datastructure.DataJSONLanguages;
import hotlinecesena.model.dataccesslayer.datastructure.DataJSONRanking;
import hotlinecesena.model.dataccesslayer.datastructure.DataJSONSettings;
import hotlinecesena.model.dataccesslayer.datastructure.DataPhysicsCollision;
import hotlinecesena.model.dataccesslayer.datastructure.DataPlayer;
import hotlinecesena.model.dataccesslayer.datastructure.DataWeapons;
import hotlinecesena.model.dataccesslayer.datastructure.DataWorldMap;

/**
 * This interface represent what kind of data need to saved
 * for run application correctly
 * @author Federico
 */
public interface DataAccessLayer {

	/**
	 * @return a weapons data class
	 */
	DataWeapons getWeapons();
	/**
	 * @return a bullet data class
	 */
	DataBullet getBullets();
	/**
	 * @return a weapons data class
	 */
	DataItems getDataItems();
	/**
	 * @return a physics collision data class
	 */
	DataPhysicsCollision getPhysics();
	/**
	 * @return a settings data class
	 */
	DataJSONSettings getSettings();
	/**
	 * @return a ranking data class
	 */
	DataJSONRanking getRanking();
	/**
	 * @return a language data class
	 */
	DataJSONLanguages getLanguages();
	/**
	 * @return a world data class
	 */
	DataWorldMap getWorld();
	/**
	 * @return a player data class
	 */
	DataPlayer getPlayer();
	/**
	 * @return a enemy data class
	 */
	DataEnemy getEnemy();
}