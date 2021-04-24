package hotlinecesena.model.dataccesslayer.datastructure;

import javafx.util.Pair;
import hotlinecesena.model.dataccesslayer.AbstractData;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.entities.actors.player.PlayerFactoryImpl;
import hotlinecesena.utilities.ConverterUtils;
import static java.util.stream.Collectors.*; 

/**
 * A class that maintains updated a instance of player {@code Player}
 * @author Federico
 */
public class DataPlayer extends AbstractData {

	private Player ply;
	
	public DataPlayer(DataWorldMap world, DataEnemy enemy, DataPhysicsCollision physics, DataItems item, DataWeapons weapons) {
		Pair<Integer,Integer> pos = world.getWorldMap().entrySet().stream()
				.filter(itm -> itm.getValue().equals(SymbolsType.PLAYER))
				.map((itm)-> itm.getKey())
				.collect(toList()).get(0);
		ply = new PlayerFactoryImpl().createPlayer(
				ConverterUtils.convertPairToPoint2D(pos),
				0,
				physics.getObstacles(),
				enemy.getEnemies(),
				item.getItems(),
				weapons.getWeapons()
				);
	}

	public Player getPly() {
		return ply;
	}
}
