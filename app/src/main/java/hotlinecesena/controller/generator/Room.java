package hotlinecesena.controller.generator;

import java.util.Map;

import hotlinecesena.model.dataccesslayer.SymbolsType;
import javafx.util.Pair;

public interface Room {
	
	public void generate();
	public Pair<Integer, Integer> getCenter();
	public void setCenter(Pair<Integer, Integer> center);
	public Map<Pair<Integer, Integer>, SymbolsType> getMap();
	public Room deepCopy();
}
