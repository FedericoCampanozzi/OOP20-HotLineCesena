package hotlinecesena.controller.generator;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javafx.util.Pair;

public class QuadraticRoom extends AbstractRoom {
	final private  int w;
	final private  int d;
	
	private QuadraticRoom(final long seed, Map<Pair<Integer, Integer>, Character> map, Pair<Integer, Integer> center, int w, int d) {
		super();
		this.map = map;
		this.seed = seed;
		this.w = w;
		this.d = d;
	}
	
	public QuadraticRoom(final long seed, int w, int d, final char walkableSimbol, final char doorSimbols, final char wallSimbols) {
		super();
		
		if(w % 2 == 0) {
			w += 1;
		}
		
		this.w = w;
		this.d = d;
		generate(seed, walkableSimbol, doorSimbols, wallSimbols);
	}
	
	@Override
	public void generate(final long seed, final char walkableSimbol, final char doorSimbols, final char wallSimbols) {
		
		final int width = (this.w - 1) / 2;
		Random rnd = new Random();
		rnd.setSeed(seed);
		
		for (int y = -width; y <= width; y++) {
			for (int x = -width; x <= width; x++) {

				if (y == -width || x == -width || y == width || x == width) {
					map.put(new Pair<>(y, x), wallSimbols);
				} else {
					map.put(new Pair<>(y, x), walkableSimbol);
				}
			}
		}
		Set<Pair<Integer, Integer>> connections = new HashSet<>();
		while (connections.size() < this.d) {
			final int x = rnd.nextInt(this.w) - width;
			final int y = rnd.nextInt(this.w) - width;
			Pair<Integer, Integer> cPos = new Pair<>(y, x);

			if ((!cPos.equals(new Pair<Integer, Integer>(-width, -width))
					&& !cPos.equals(new Pair<Integer, Integer>(width, width))
					&& !cPos.equals(new Pair<Integer, Integer>(-width, width))
					&& !cPos.equals(new Pair<Integer, Integer>(width, -width))) && !connections.contains(cPos)
					&& this.map.get(cPos).equals(wallSimbols)) {
				this.map.put(cPos, doorSimbols);
				connections.add(cPos);
			}
		}
	}

	@Override
	public Room deepCopy() {
		return new QuadraticRoom(this.seed, this.map, this.center, this.w, this.d);
	}
	
	public int getWidth() {
		return this.w;
	}
}
