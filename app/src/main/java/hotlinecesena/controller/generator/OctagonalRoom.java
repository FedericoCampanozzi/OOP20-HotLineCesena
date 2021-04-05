//package hotlinecesena.controller.generator;
//
//import javafx.util.Pair;
//import java.util.Map;
//import java.util.HashSet;
//import java.util.Random;
//import java.util.Set;
//
//import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
//import hotlinecesena.model.dataccesslayer.SIMBOLS_TYPE;
//
//public class OctagonalRoom extends AbstractRoom {
//	final private  int w;
//	final private  int d;
//	
//	private OctagonalRoom(Map<Pair<Integer, Integer>, SIMBOLS_TYPE> map, Pair<Integer, Integer> center, int edge, int nDoor) {
//		super();
//		this.map = map;
//		this.w = edge;
//		this.d = nDoor;
//	}
//	
//	public OctagonalRoom(int edge, int nDoor) {
//		super();
//		
//		if (edge % 2 == 0) {
//			edge -= 1;
//		}
//		
//		this.w = edge;
//		this.d = nDoor;
//		generate(JSONDataAccessLayer.SEED);
//	}
//	
//	@Override
//	public void generate(final long seed) {
//		
//		final int width = (this.w - 1) / 2;
//		Random rnd = new Random();
//		rnd.setSeed(seed);
//		
//		for (int y = -width; y <= width; y++) {
//			for (int x = -width; x <= width; x++) {
//
//				if (y == -width || x == -width || y == width || x == width) {
//					map.put(new Pair<>(y, x), SIMBOLS_TYPE.WALL);
//				} else {
//					map.put(new Pair<>(y, x), SIMBOLS_TYPE.WALKABLE);
//				}
//			}
//		}
//		Set<Pair<Integer, Integer>> connections = new HashSet<>();
//		while (connections.size() < this.d) {
//			final int x = rnd.nextInt(this.w) - width;
//			final int y = rnd.nextInt(this.w) - width;
//			Pair<Integer, Integer> cPos = new Pair<>(y, x);
//
//			if ((!cPos.equals(new Pair<Integer, Integer>(-width, -width))
//					&& !cPos.equals(new Pair<Integer, Integer>(width, width))
//					&& !cPos.equals(new Pair<Integer, Integer>(-width, width))
//					&& !cPos.equals(new Pair<Integer, Integer>(width, -width))) && !connections.contains(cPos)
//					&& this.map.get(cPos).equals(SIMBOLS_TYPE.WALL)) {
//				this.map.put(cPos, SIMBOLS_TYPE.DOOR);
//				connections.add(cPos);
//			}
//		}
//	}
//
//	@Override
//	public Room deepCopy() {
//		return new QuadraticRoom(this.map, this.center, this.w);
//	}
//	
//	public int getWidth() {
//		return this.w;
//	}
//}
