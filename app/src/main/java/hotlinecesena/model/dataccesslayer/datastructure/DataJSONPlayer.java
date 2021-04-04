package hotlinecesena.model.dataccesslayer.datastructure;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import hotlinecesena.model.dataccesslayer.AbstractData;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.entities.actors.player.PlayerFactoryImpl;

public class DataJSONPlayer  extends AbstractData {

	private final Player ply;
	
	public DataJSONPlayer(DataWorldMap world) throws JsonGenerationException, JsonMappingException, IOException {
		
		ply = new PlayerFactoryImpl().createPlayer();
	}

	public Player getPly() {
		return ply;
	}
}
