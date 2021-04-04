package hotlinecesena.model.dataccesslayer.datastructure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import hotlinecesena.model.dataccesslayer.AbstractData;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SIMBOLS_TYPE;
import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.entities.actors.enemy.EnemyFactoryImpl;
import hotlinecesena.model.entities.actors.enemy.EnemyType;
import javafx.geometry.Point2D;
import javafx.util.Pair;

public class DataJSONEnemy extends AbstractData {
	
	private final List<Enemy> enemies = new ArrayList<>();
	
	public DataJSONEnemy(DataWorldMap world) {
		Random rnd = new Random();
		rnd.setSeed(JSONDataAccessLayer.SEED);
		EnemyFactoryImpl eFact = new EnemyFactoryImpl();
		Set<Point2D> walkable = new HashSet<>();
		Set<Point2D> wall = new HashSet<>();

		for (Pair<Integer, Integer> pii : world.getWorldMap().keySet()) {
			if (world.getWorldMap().get(pii).equals(SIMBOLS_TYPE.ENEMY.getDecotification())) {
				Point2D pos = null;
				EnemyType et = EnemyType.values()[rnd.nextInt(EnemyType.values().length)];
				enemies.add(eFact.getEnemy(pos, et, walkable, wall));
			}
		}
	}
	
	public List<Enemy> getEnemies() {
		return enemies;
	}
}
