package hotlinecesena.controller.generator;

import java.util.HashMap;
import java.util.Map;

import hotlinecesena.model.dataccesslayer.SymbolsType;
import javafx.util.Pair;

/**
 * A common implementation of rooms
 * @author Federico
 *
 */
public abstract class AbstractRoom implements Room {
	
	protected Map<Pair<Integer, Integer>, SymbolsType> map = new HashMap<>();
	protected Pair<Integer, Integer> center = new Pair<>(0, 0);
	
	public AbstractRoom() {
		
	}

	/**
	 * {@inheritDoc}
	 */
	public abstract void generate();
	/**
	 * {@inheritDoc}
	 */
	public abstract Room deepCopy();
	
	public AbstractRoom(Map<Pair<Integer, Integer>, SymbolsType> map, Pair<Integer, Integer> center) {
		this.map = map;
		this.center = center;
	}
	/**
	 * {@inheritDoc}
	 */
	public Pair<Integer, Integer> getCenter() {
		return center;
	}
	/**
	 * {@inheritDoc}
	 */
	public void setCenter(Pair<Integer, Integer> center) {
		this.center = center;
	}
	/**
	 * {@inheritDoc}
	 */
	public Map<Pair<Integer, Integer>, SymbolsType> getMap() {
		return map;
	}
}
