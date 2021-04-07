package hotlinecesena.model.dataccesslayer.datastructure;

import java.io.IOException;
import javafx.util.Pair;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import hotlinecesena.model.dataccesslayer.AbstractData;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.entities.actors.player.PlayerFactoryImpl;
import hotlinecesena.utilities.Utilities;
import static java.util.stream.Collectors.*; 

public class DataPlayer  extends AbstractData {

	private Player ply;
	
	public DataPlayer(DataWorldMap world, DataJSONSettings settings) throws JsonGenerationException, JsonMappingException, IOException {
		Pair<Integer,Integer> pos = world.getWorldMap().entrySet().stream()
				.filter(itm -> itm.getValue().equals(SymbolsType.PLAYER))
				.map((itm)-> itm.getKey())
				.collect(toList()).get(0);
		ply = new PlayerFactoryImpl().createPlayer(Utilities.convertPairToPoint2D(pos, settings.getTileSize()),0);
	}

	public Player getPly() {
		return ply;
	}
}
