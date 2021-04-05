package hotlinecesena.controller.generator;

import java.util.Map;

import hotlinecesena.model.dataccesslayer.SIMBOLS_TYPE;
import javafx.util.Pair;

public interface Room {
	
	public void generate();
	public Pair<Integer, Integer> getCenter();
	public void setCenter(Pair<Integer, Integer> center);
	public Map<Pair<Integer, Integer>, SIMBOLS_TYPE> getMap();
	public Room deepCopy();
}
