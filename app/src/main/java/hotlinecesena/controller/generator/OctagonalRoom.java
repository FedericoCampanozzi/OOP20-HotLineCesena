package hotlinecesena.controller.generator;

import javafx.util.Pair;
import java.util.*;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.utilities.Utilities;

public class OctagonalRoom extends AbstractRoom {
	final private  int width;
	private  int edge;
	private  int nDoor;
	
	private OctagonalRoom(Map<Pair<Integer, Integer>, SymbolsType> map, Pair<Integer, Integer> center, int width) {
		super();
		this.center = center;
		this.map = map;
		this.width = width;
	}
	
	public OctagonalRoom(int edge, int nDoor) {
		super();
		if (edge % 2 == 0) {
			edge -= 1;
		}
		this.width = (3 * (edge-1)) + 1;
		this.edge = edge;
		this.nDoor = nDoor;
		generate();
	}
	
	@Override
	public void generate() {
		final int edge2 = (edge-1)/2;
		final int width2 = (width-1)/2;
		final Random rnd = new Random();
		final List<Pair<Integer, Integer>> walls = new ArrayList<>();
		final List<Pair<Integer, Integer>> dirs = new ArrayList<>();
		Pair<Integer, Integer> start = new Pair<>(0, 0);
		 
		rnd.setSeed(JSONDataAccessLayer.SEED);
		dirs.add(new Pair<>(0, 1));
		dirs.add(new Pair<>(-1, 1));
		dirs.add(new Pair<>(-1, 0));
		dirs.add(new Pair<>(-1, -1));
		dirs.add(new Pair<>(0, -1));
		dirs.add(new Pair<>(1, -1));
		dirs.add(new Pair<>(1, 0));
		dirs.add(new Pair<>(1, 1));

		for (Pair<Integer, Integer> dir : dirs) {
			for (int i = 1; i < edge; i++) {
				start = Utilities.sumPair(start, dir);
				walls.add(start);
			}
		}
		
		start = new Pair<>(-edge -edge2 + 1 , edge2);
		
		for(int i = 0;i < walls.size();i++)
		{
			walls.set(i, Utilities.subPair(start, walls.get(i)));
			this.map.put(walls.get(i), SymbolsType.WALL);
		}
		
		for(int i = 0;i < this.nDoor;)
		{
			Pair<Integer, Integer> door = walls.get(rnd.nextInt(walls.size()));
			
			if(		(walls.contains(new Pair<>(door.getKey(), door.getValue() + 1)) && 
							walls.contains(new Pair<>(door.getKey(), door.getValue() - 1)) ) ||
					(walls.contains(new Pair<>(door.getKey() + 1, door.getValue())) && 
							walls.contains(new Pair<>(door.getKey() - 1, door.getValue())) ) ||
					(walls.contains(new Pair<>(door.getKey() - 1, door.getValue() + 1)) && 
							walls.contains(new Pair<>(door.getKey() + 1, door.getValue() - 1)) ) ||
					(walls.contains(new Pair<>(door.getKey() + 1, door.getValue() + 1)) && 
							walls.contains(new Pair<>(door.getKey() - 1, door.getValue() - 1)) )
					) {
				this.map.put(door, SymbolsType.DOOR);
				i++;
			}
		}
		
		for (int i = -width2 + 1; i <= width2 - 1; i++) {
			for (int j = -width2 + 1; j <= width2 - 1; j++) {

				if (Utilities.distance(new Pair<>(i, j), center) < (((double) this.width - 2) / 2.0d)) {
					this.map.put(new Pair<>(i, j), SymbolsType.WALKABLE);	
				}
			}
		}
	}

	@Override
	public Room deepCopy() {
		return new OctagonalRoom(this.map, this.center, this.edge);
	}
	
	public int getWidth() {
		return this.width;
	}
}
