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

public interface DataAccessLayer {

	DataWeapons getWeapons();

	DataBullet getBullets();

	DataItems getDataItems();

	DataPhysicsCollision getPhysics();

	DataJSONSettings getSettings();

	DataJSONRanking getRanking();

	DataJSONLanguages getLanguages();

	DataWorldMap getWorld();

	DataPlayer getPlayer();

	DataEnemy getEnemy();
}