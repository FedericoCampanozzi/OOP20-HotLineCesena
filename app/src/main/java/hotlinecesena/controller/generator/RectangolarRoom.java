package hotlinecesena.controller.generator;

import javafx.util.Pair;
import java.util.Map;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SymbolsType;

public class RectangolarRoom extends AbstractRoom {
	private  int w;
	private  int h;
	private  int d;
	
	private RectangolarRoom(Map<Pair<Integer, Integer>, SymbolsType> map, Pair<Integer, Integer> center, int width, int height) {
		super();
		this.map = map;
		this.w = width;
		this.h = height;
	}
	
	public RectangolarRoom(int width, int height, int nDoor) {
		super();
		
		if (width % 2 == 0) {
			width -= 1;
		}
		if (height % 2 == 0) {
			height -= 1;
		}
		
		this.h = height;
		this.w = width;
		this.d = nDoor;
		generate();
	}
	
	@Override
	public void generate() {
		final int width2 = (this.w - 1) / 2;
		final int height2 = (this.h - 1) / 2;
		Random rnd = new Random();
		rnd.setSeed(JSONDataAccessLayer.SEED);
		
		for (int y = -height2; y <= height2; y++) {
			for (int x = -width2; x <= width2; x++) {

				if (y == -height2 || x == -width2 || y == height2 || x == width2) {
					map.put(new Pair<>(y, x), SymbolsType.WALL);
				} else {
					map.put(new Pair<>(y, x), SymbolsType.WALKABLE);
				}
			}
		}
		
		Set<Pair<Integer, Integer>> connections = new HashSet<>();
		while (connections.size() < this.d) {
			final int x = rnd.nextInt(this.w) - width2;
			final int y = rnd.nextInt(this.h) - height2;
			Pair<Integer, Integer> cPos = new Pair<>(y, x);

			if ((!cPos.equals(new Pair<Integer, Integer>(-height2, -width2))
					&& !cPos.equals(new Pair<Integer, Integer>(height2, width2))
					&& !cPos.equals(new Pair<Integer, Integer>(-height2, width2))
					&& !cPos.equals(new Pair<Integer, Integer>(height2, -width2))) && !connections.contains(cPos)
					&& this.map.get(cPos).equals(SymbolsType.WALL)) {
				this.map.put(cPos, SymbolsType.DOOR);
				connections.add(cPos);
			}
		}
	}

	@Override
	public Room deepCopy() {
		return new RectangolarRoom(this.map, this.center, this.w, this.h);
	}
	
	public int getWidth() {
		return this.w;
	}
	
	public int getHeight() {
		return this.h;
	}
}
