package hotlinecesena.controller.generator;

import java.util.*;
import java.util.Map.Entry;
import hotlinecesena.utilities.Utilities;
import javafx.util.Pair;

public class WorldGeneratorImpl {

	private class Room {

		private Map<Pair<Integer, Integer>, Character> map = new HashMap<>();
		private Pair<Integer, Integer> center = new Pair<>(0, 0);
		private int width;
		private int serial = 0;

		public Pair<Integer, Integer> getCenter() {
			return center;
		}

		public void setCenter(Pair<Integer, Integer> center) {
			this.center = center;
		}

		public Room(int width, Map<Pair<Integer, Integer>, Character> map, int serial) {
			super();
			this.width = width;
			this.map = map;
			this.serial = serial;
		}

		public Room(int width, int cSize, int serial) {
			if (width % 2 == 0) {
				width++;
			}

			this.serial = serial;
			this.width = width;
			width = (width - 1) / 2;

			for (int y = -width; y <= width; y++) {
				for (int x = -width; x <= width; x++) {

					if (y == -width || x == -width || y == width || x == width) {
						map.put(new Pair<>(y, x), 'W');
					} else {
						map.put(new Pair<>(y, x), '.');
					}
				}
			}
			Set<Pair<Integer, Integer>> connections = new HashSet<>();
			while (connections.size() < cSize) {
				final int x = rnd.nextInt(this.width) - width;
				final int y = rnd.nextInt(this.width) - width;
				Pair<Integer, Integer> cPos = new Pair<>(y, x);

				if ((!cPos.equals(new Pair<Integer, Integer>(-width, -width))
						&& !cPos.equals(new Pair<Integer, Integer>(width, width))
						&& !cPos.equals(new Pair<Integer, Integer>(-width, width))
						&& !cPos.equals(new Pair<Integer, Integer>(width, -width))) && !connections.contains(cPos)
						&& this.map.get(cPos).equals('W')) {
					this.map.put(cPos, 'D');
					connections.add(cPos);
				}
			}
			
			//System.out.println(this.width + " x " + this.width + " [" + this.serial + "] -> " + this.connections.size() + "\n");
		}
		
		@Override
		public String toString() {
			return "Room [width=" + this.width + " serial=" + this.serial + "]";
		}

		public int getWidth() {
			return width;
		}

		public Map<Pair<Integer, Integer>, Character> getMap() {
			return map;
		}

		
		public Room copy() {
			return new Room(this.width, this.map, this.serial);
		}
	}

	private final static int MAX_POSSIBILITY = 10000;
	private final static int MAP_PADDING = 2;

	private Random rnd = new Random();

	// Low level description
	private Map<Pair<Integer, Integer>, Character> map = new HashMap<>();

	// High level description
	private List<Room> baseRooms = new ArrayList<>();
	private List<Room> rooms = new ArrayList<>();

	// Debug
	private List<Integer> serials = new ArrayList<>();

	private int xMin, xMax, yMin, yMax;
	private int seed;
	
	public int getMinX() {
		return this.xMin;
	}

	public int getMinY() {
		return this.yMin;
	}

	public int getMaxX() {
		return this.xMax;
	}

	public int getMaxY() {
		return this.yMax;
	}

	public WorldGeneratorImpl(final int seed, final int wMin, final int hMin, final int wMax, final int hMax,
			final int nRooms, final int nBaseRooms) {

		if ((wMin >= wMax) || (hMin >= hMax)) {
			new IllegalArgumentException("La stanza - quindi la mappa non puo' essere creata");
		}
		
		this.seed = seed;
		rnd.setSeed(this.seed);

		while (this.serials.size() < nRooms + nBaseRooms) {
			int serial = rnd.nextInt();
			if (!this.serials.contains(serial)) {
				this.serials.add(serial);
			}
		}
		xMin = Integer.MAX_VALUE;
		yMin = Integer.MAX_VALUE;
		xMax = Integer.MIN_VALUE;
		yMax = Integer.MIN_VALUE;

		for (int i = 0; i < nBaseRooms; i++) {
			baseRooms.add(new Room(rnd.nextInt(wMax) + wMin, 3, this.serials.get(i)));
		}

		for (int l = 0; l < MAX_POSSIBILITY && this.rooms.size() < nRooms; l++) {

			Room toPut = this.baseRooms.get(rnd.nextInt(this.baseRooms.size())).copy();

			if (this.rooms.isEmpty()) {
				generateRoom(new Pair<>(0, 0), toPut);
			} else {
				Pair<Integer, Integer> doorLink = getConnectionsLinking();
				int dirX = (rnd.nextInt(3) - 1) * ((toPut.getWidth() / 2) + 1);
				int dirY = 0;
				if(dirX == 0) {
					dirY = (rnd.nextInt(3) - 1) * ((toPut.getWidth() / 2) + 1);
				}
				Pair<Integer, Integer> dir = new Pair<>(dirY, dirX);
				Pair<Integer, Integer> center = Utilities.sumPair(doorLink, dir);
				if (canPutRoom(center, doorLink, dir, toPut)) {
					generateRoom(center, toPut);
					toPut.setCenter(center);
				}
			}
		}
		
		/*
		System.out.println();

		for (Room r : this.rooms) {
			System.out.println(r.toString());
		}
		*/
	}
	
	private Pair<Integer, Integer> getConnectionsLinking() {
		List<Pair<Integer, Integer>> allDoors = new ArrayList<>();
		for (Pair<Integer, Integer> p : this.map.keySet()) {
			if (this.map.get(p).equals('D')) {
				allDoors.add(p);
			}
		}
		return allDoors.get(rnd.nextInt(allDoors.size()));
	}

	private boolean canPutRoom(Pair<Integer, Integer> center, Pair<Integer, Integer> doorLink, Pair<Integer, Integer> dir, Room room) {
		boolean can = true;
		boolean isNearDoor = false;
		Pair<Integer, Integer> dd = new Pair<>(doorLink.getKey() + (int)Math.signum(dir.getKey()), doorLink.getValue() + (int)Math.signum(dir.getValue()));
		
		for (Pair<Integer, Integer> positions : room.getMap().keySet()) {
			var pii = Utilities.sumPair(center, positions);
			if (this.map.containsKey(pii)) {
				can = false;
				break;
			}
			
			if(pii.equals(dd) && room.getMap().get(positions).equals('D')) {
				isNearDoor = true;
			}
		}
		
		
		return can && isNearDoor;
	}

	private void generateRoom(Pair<Integer, Integer> center, Room room) {

		for (Entry<Pair<Integer, Integer>, Character> p : room.getMap().entrySet()) {
			Pair<Integer, Integer> position = Utilities.sumPair(center, p.getKey());
			
			this.map.put(position, p.getValue());
			
			if (position.getKey() < xMin) {
				xMin = position.getKey();
			}
			if (position.getKey() > xMax) {
				xMax = position.getKey();
			}

			if (position.getValue() < yMin) {
				yMin = position.getValue();
			}
			if (position.getValue() > yMax) {
				yMax = position.getValue();
			}
		}
		
		this.rooms.add(room);
	}

	public WorldGeneratorImpl addPlayer() {
		Room r = this.rooms.get(rnd.nextInt(this.rooms.size()));
		r.getMap().put(r.getCenter(), 'P');
		this.map.put(r.getCenter(), 'P');
		return this;
	}

	public WorldGeneratorImpl addEnemy(float rate, int maxEnemyRoom) {
		for(Room r : this.rooms) {
			int enemyCount = 0;
			for(Entry<Pair<Integer,Integer>,Character> point : r.getMap().entrySet()) {
				if(enemyCount<maxEnemyRoom &&  point.getValue().equals('.') && rnd.nextFloat() < rate) {
					r.getMap().put(point.getKey(), 'E');
					this.map.put(Utilities.sumPair(point.getKey(), r.getCenter()), 'E');
					enemyCount++;
				}
			}
		}
		return this;
	}

	public WorldGeneratorImpl build() {

		this.xMin = xMin - MAP_PADDING;
		this.xMax = xMax + MAP_PADDING;
		this.yMax = yMax + MAP_PADDING;
		this.yMin = yMin - MAP_PADDING;
		
		for (int i = xMin; i <= xMax; i++) {
			for (int j = yMin; j <= yMax; j++) {

				if (this.map.get(new Pair<>(i, j)) == null) {
					this.map.put(new Pair<>(i, j), '_');
				}
				
				if (this.map.get(new Pair<>(i, j)) == 'D' && (
						this.map.get(new Pair<>(i + 1, j)) == null || this.map.get(new Pair<>(i + 1, j)) == '_' || 
						this.map.get(new Pair<>(i - 1, j)) == null || this.map.get(new Pair<>(i - 1, j)) == '_' || 
						this.map.get(new Pair<>(i, j + 1)) == null || this.map.get(new Pair<>(i, j + 1)) == '_' || 
						this.map.get(new Pair<>(i, j - 1)) == null || this.map.get(new Pair<>(i, j - 1)) == '_')) {
					this.map.put(new Pair<>(i, j), 'W');
				}
				
				if (this.map.get(new Pair<>(i, j)).equals('D') && this.map.get(new Pair<>(i + 1, j)).equals('D') && this.map.get(new Pair<>(i + 2, j)).equals('.')){
					this.map.put(new Pair<>(i, j), '.');
					this.map.put(new Pair<>(i + 1, j), '.');
				}
				
				if (this.map.get(new Pair<>(i, j)).equals('D') && this.map.get(new Pair<>(i, j + 1)).equals('D') && this.map.get(new Pair<>(i, j + 2)).equals('.')){
					this.map.put(new Pair<>(i, j), '.');
					this.map.put(new Pair<>(i, j + 1), '.');
				}
				
				if (this.map.get(new Pair<>(i, j)).equals('D') && this.map.get(new Pair<>(i - 1, j)).equals('D') && this.map.get(new Pair<>(i - 2, j)).equals('.')){
					this.map.put(new Pair<>(i, j), '.');
					this.map.put(new Pair<>(i - 1, j), '.');
				}
				
				if (this.map.get(new Pair<>(i, j)).equals('D') && this.map.get(new Pair<>(i, j - 1)).equals('D') && this.map.get(new Pair<>(i, j - 2)).equals('.')){
					this.map.put(new Pair<>(i, j), '.');
					this.map.put(new Pair<>(i, j - 1), '.');
				}
			}
		}

		for (int i = xMin; i <= xMax; i++) {
			for (int j = yMin; j <= yMax; j++) {
				if (this.map.get(new Pair<>(i, j)) == 'D') {
					this.map.put(new Pair<>(i, j), 'W');
				}
			}
		}
		return this;
	}
	
	public Map<Pair<Integer, Integer>, Character> getMap() {
		return this.map;
	}

	public int getSeed() {
		return this.seed;
	}
}