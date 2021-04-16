package hotlinecesena.model.dataccesslayer.datastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javafx.geometry.Point2D;
import javafx.util.Pair;
import hotlinecesena.model.dataccesslayer.AbstractData;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.entities.actors.enemy.EnemyFactoryImpl;
import hotlinecesena.model.entities.actors.enemy.EnemyType;
import hotlinecesena.utilities.Utilities;
import static java.util.stream.Collectors.*;

public class DataEnemy extends AbstractData {
	private final List<Enemy> enemies = new ArrayList<>();
	public DataEnemy(DataWorldMap world) {
		Random rnd = new Random();
		rnd.setSeed(JSONDataAccessLayer.SEED);
		EnemyFactoryImpl eFact = new EnemyFactoryImpl();
		Set<Point2D> walkable = world.getWorldMap().entrySet().stream()
				.filter(itm -> 
						!itm.getValue().equals(SymbolsType.WALL) && 
						!itm.getValue().equals(SymbolsType.OBSTACOLES) &&
						!itm.getValue().equals(SymbolsType.VOID))
				.map((itm)-> Utilities.convertPairToPoint2D(itm.getKey()))
				.collect(toSet());
		Set<Point2D> wall = world.getWorldMap().entrySet().stream()
				.filter(itm -> itm.getValue().equals(SymbolsType.WALL))
				.map((itm)-> Utilities.convertPairToPoint2D(itm.getKey()))
				.collect(toSet());
		for (Pair<Integer, Integer> pii : world.getWorldMap().keySet()) {
			if (world.getWorldMap().get(pii).equals(SymbolsType.ENEMY)) {
				Point2D pos = Utilities.convertPairToPoint2D(pii);
				EnemyType et = EnemyType.values()[rnd.nextInt(EnemyType.values().length)];
				enemies.add(eFact.getEnemy(pos, et, walkable, wall));
			}
		}
	}
	
	public List<Enemy> getEnemies() {
		return enemies;
	}
}
