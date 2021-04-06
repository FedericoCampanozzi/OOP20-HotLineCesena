package hotlinecesena.controller.generator;

import java.util.HashMap;
import java.util.Map;

import hotlinecesena.model.dataccesslayer.SimbolsType;
import javafx.util.Pair;

public abstract class AbstractRoom implements Room {
	
	protected Map<Pair<Integer, Integer>, SimbolsType> map = new HashMap<>();
	protected Pair<Integer, Integer> center = new Pair<>(0, 0);
	
	public AbstractRoom() {
		
	}

	public AbstractRoom(Map<Pair<Integer, Integer>, SimbolsType> map, Pair<Integer, Integer> center) {
		this.map = map;
		this.center = center;
	}

	public Pair<Integer, Integer> getCenter() {
		return center;
	}
	
	public void setCenter(Pair<Integer, Integer> center) {
		this.center = center;
	}
	
	public Map<Pair<Integer, Integer>, SimbolsType> getMap() {
		return map;
	}
}
