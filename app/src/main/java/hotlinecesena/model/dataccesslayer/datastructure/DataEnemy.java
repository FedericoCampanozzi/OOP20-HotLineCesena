package hotlinecesena.model.dataccesslayer.datastructure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javafx.geometry.Point2D;
import javafx.util.Pair;
import hotlinecesena.model.dataccesslayer.AbstractData;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.entities.actors.enemy.EnemyFactoryImpl;
import hotlinecesena.model.entities.actors.enemy.EnemyType;
import hotlinecesena.utilities.Utilities;
import static java.util.stream.Collectors.*;

public class DataEnemy extends AbstractData {
	
	private final List<Enemy> enemies = new ArrayList<>();
	
	public DataEnemy(DataWorldMap world, DataJSONSettings settings) {
		Random rnd = new Random();
		rnd.setSeed(JSONDataAccessLayer.SEED);
		EnemyFactoryImpl eFact = new EnemyFactoryImpl();
		Set<Point2D> walkable = world.getWorldMap().entrySet().stream()
				.filter(itm -> itm.getValue().equals(SymbolsType.WALKABLE))
				.map((itm)-> Utilities.convertPairToPoint2D(itm.getKey(), settings.getTileSize()))
				.collect(toSet());
		Set<Point2D> wall = world.getWorldMap().entrySet().stream()
				.filter(itm -> itm.getValue().equals(SymbolsType.WALL))
				.map((itm)-> Utilities.convertPairToPoint2D(itm.getKey(), settings.getTileSize()))
				.collect(toSet());

		for (Pair<Integer, Integer> pii : world.getWorldMap().keySet()) {
			if (world.getWorldMap().get(pii).equals(SymbolsType.ENEMY)) {
				Point2D pos = Utilities.convertPairToPoint2D(pii, settings.getTileSize());
				EnemyType et = EnemyType.values()[rnd.nextInt(EnemyType.values().length)];
				enemies.add(eFact.getEnemy(pos, et, walkable, wall));
			}
		}
	}
	
	public List<Enemy> getEnemies() {
		return enemies;
	}
	
	public int getDeathEnemy() {
		return this.enemies.stream()
				.filter(itm -> itm.getActorStatus().equals(ActorStatus.DEAD))
				.collect(toSet()).size();
	}
}
