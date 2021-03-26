package hotlinecesena.controller.generator;

import java.util.*;
import java.util.Map.Entry;

import hotlinecesena.utilities.Utilities;
import javafx.util.Pair;

public class WorldGeneratorImpl {

	private enum DOOR {
		TOP(-1), BOT(1), LEFT(2), RIGHT(-2);

		private final int id;

		private DOOR(final int id) {
			this.id = id;
		}
		
		private int getId() {
			return this.id;
		}
		
		public static DOOR fromId (final int id) {
			for(DOOR d : values()) {
				if(id == d.getId()) {
					return d;
				}
			}
			
			return null;
		}
		
		public static DOOR getInverse(DOOR d) {
			return DOOR.fromId(-d.getId());
		}
	}

	private class Room {
		private Map<Pair<Integer, Integer>, Character> map = new HashMap<>();
		private Map<DOOR,Pair<Integer, Integer>> connections = new HashMap<>();
		private final int w;
		private final int h;
		private Pair<Integer, Integer> relation = new Pair<>(0, 0);
		
		public Room(final int w, final int h, final int d) {
			
			this.w = w;
			this.h = h;
			
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					if (i == 0 || j == 0 || i == w - 1 || j == h - 1) {
						map.put(new Pair<>(i, j), 'W');
					} else {
						map.put(new Pair<>(i, j), '.');
					}
				}
			}

			while (connections.size() < d) {

				DOOR create = DOOR.values()[rnd.nextInt(DOOR.values().length)];
				Pair<Integer, Integer> pii = new Pair<>(0,0);
				
				if (create == DOOR.LEFT) {
					pii = new Pair<>(w / 2, 0);
				} else if (create == DOOR.RIGHT) {
					pii = new Pair<>(w / 2, h - 1);
				} else if (create == DOOR.BOT) {
					pii = new Pair<>(w - 1, h / 2);
				} else if (create == DOOR.TOP) {
					pii = new Pair<>(0, h / 2);
				}

				if (!this.connections.containsKey(create)) {
					this.map.put(pii, 'D');
					this.connections.put(create, pii);
				}
			}
			/*
			String debug = w + " X " + h + " -> " + this.connections.keySet().toString() + "\n";

			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					debug += map.get(new Pair<>(i, j));
				}
				debug += "\n";
			}

			System.out.println(debug);
			*/
		}
		
		public Room(final int wMin, final int hMin, final int dMin,
				final int wMax, final int hMax, final int dMax) {
			this(rnd.nextInt(wMax) + wMin, rnd.nextInt(hMax) + hMin, rnd.nextInt(dMax) + dMin);
		}
		
		public int getWidth() {
			return this.w;
		}
		
		public int getHeight() {
			return this.h;
		}
		
		public Pair<Integer, Integer> getRelation() {
			return relation;
		}

		public void setRelation(Pair<Integer, Integer> relation) {
			this.relation = Utilities.sumPair(this.relation, relation);
		}

		@Override
		public String toString() {
			return "Room [w=" + w + ",h=" + h + "," + this.connections.keySet().toString() + "]";
		}
		
		public String toStringLow() {
			return "Room [w=" + w + ",h=" + h + "," + this.connections.size() + "]";
		}
	}
	
	private class RoomLinking {
		private int fstIndex;
		private int scdIndex;
		private DOOR fstDoor;
		private DOOR scdDoor;
		
		public RoomLinking(int fstIndex, int scdIndex, DOOR fstDoor, DOOR scdDoor) {
			this.fstIndex = fstIndex;
			this.scdIndex = scdIndex;
			this.fstDoor = fstDoor;
			this.scdDoor = scdDoor;
		}

		public Room getFirstRoom(List<Room> list) {
			return list.get(fstIndex);
		}

		public Room getSecondRoom(List<Room> list) {
			return list.get(scdIndex);
		}

		public DOOR getFirstDoor() {
			return this.fstDoor;
		}

		public DOOR getSecondDoor() {
			return this.scdDoor;
		}
		
	}
	
	private final static int MAX_POSSIBILITY = 100;
	private final static int MAP_PADDING = 2;

	private Random rnd = new Random();
	
	//Low level description
	private Map<Pair<Integer, Integer>, Character> map = new HashMap<>();
	
	//High level description
	private List<Room> baseRooms = new ArrayList<>();
	private List<Room> rooms = new ArrayList<>();
	private int xMin, xMax, yMin, yMax;

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

	private WorldGeneratorImpl(final int xMin, final int yMin, final int yMax,
			final int xMax, final Map<Pair<Integer, Integer>, Character> m) {
		this.map = m;
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}

	public WorldGeneratorImpl(final int wMin, final int hMin, final int dMin,
			final int wMax, final int hMax, final int dMax,
			final int nRooms, final int nBaseRooms ) {
		
		if((wMin >= wMax) || (hMin >= hMax) || (dMin >= dMax) || dMin<=1 || dMax>=4) {
			new IllegalArgumentException("La stanza - quindi la mappa non puo' essere creata");
		}
		
		xMin = Integer.MAX_VALUE;
		yMin = Integer.MAX_VALUE;
		xMax = Integer.MIN_VALUE;
		yMax = Integer.MIN_VALUE;
		
		final List<Room> freeRooms = new ArrayList<>();
		final List<RoomLinking> possibleRoomsLinking = new ArrayList<>();

		baseRooms.add(new Room(5,5,4));
		
		for (int i = 0; i < nBaseRooms; i++) {
			baseRooms.add(new Room(wMin, hMin, dMin, wMax, hMax, dMax));
		}
		
		this.rooms.add(this.baseRooms.get(0));
		
		for (int p = 0; p < MAX_POSSIBILITY; p++) {
			freeRooms.clear();
			possibleRoomsLinking.clear();
			
			if(this.rooms.size() >= nRooms) {
				break;
			}
			
			for (Room room : this.rooms) {
				if (room.connections.size() != 0) {
					freeRooms.add(room);
				}
			}
			
			for (int freeRoomIndex = 0; freeRoomIndex < freeRooms.size(); freeRoomIndex++) {
				for (int baseRoomIndex = 0; baseRoomIndex < this.baseRooms.size(); baseRoomIndex++) {

					for (DOOR d : this.baseRooms.get(baseRoomIndex).connections.keySet()) {

						if (freeRooms.get(freeRoomIndex).connections.containsKey(DOOR.getInverse(d))) {
							possibleRoomsLinking.add(new RoomLinking(freeRoomIndex, baseRoomIndex, DOOR.getInverse(d), d));
						}

						if (possibleRoomsLinking.size() == MAX_POSSIBILITY) {
							break;
						}
					}
				}
			}
			
			if(possibleRoomsLinking.size() != 0) {
				RoomLinking link = possibleRoomsLinking.get(rnd.nextInt(possibleRoomsLinking.size()));
				
				generateRoom(link.getFirstRoom(freeRooms));
				if (link.getFirstDoor() == DOOR.LEFT) {
					link.getSecondRoom(this.baseRooms).setRelation(new Pair<>(-link.getSecondRoom(this.baseRooms).getWidth(), 0));
				} else if (link.getFirstDoor()  == DOOR.RIGHT) {
					link.getSecondRoom(this.baseRooms).setRelation(new Pair<>(link.getSecondRoom(this.baseRooms).getWidth() + link.getFirstRoom(freeRooms).getWidth(), 0));
				} else if (link.getFirstDoor() == DOOR.BOT) {
					link.getSecondRoom(this.baseRooms).setRelation(new Pair<>(0, -link.getSecondRoom(this.baseRooms).getHeight()));
				} else if (link.getFirstDoor() == DOOR.TOP) {
					link.getSecondRoom(this.baseRooms).setRelation(new Pair<>(0, link.getFirstRoom(freeRooms).getHeight() + link.getFirstRoom(freeRooms).getHeight()));
				}
				generateRoom(link.getSecondRoom(this.baseRooms));
			}
			
			Collections.shuffle(this.rooms);
			Collections.shuffle(this.baseRooms);
		}

		//System.out.println("Create");
	}
	
	private void generateRoom(Room r) {
		boolean can = true;
		
		for(Entry<Pair<Integer,Integer>, Character> p2 : r.map.entrySet()) {
			if(this.map.containsKey(Utilities.sumPair(r.getRelation(), p2.getKey()))) {
				can = false;
				break;
			}
		}
		
		if(can) {
			for(Entry<Pair<Integer,Integer>, Character> p2 : r.map.entrySet()) {
				Pair<Integer,Integer> pii = Utilities.sumPair(r.getRelation(), p2.getKey());
				this.map.put(pii, p2.getValue());
				
				if(pii.getKey() < xMin) {
					xMin = pii.getKey();
				}
				if(pii.getKey() > xMax) {
					xMax = pii.getKey();
				}
				
				if(pii.getValue() < yMin) {
					yMin = pii.getValue();
				} 
				if(pii.getValue() > yMax) {
					yMax = pii.getValue();
				}
			}
			rooms.add(r);
		}
		
	}
	
	public WorldGeneratorImpl build() {
		
		//System.out.println("Build : [" + xMin + "," + yMin  + "] [" + xMax + ","  + yMax + "]");
		this.xMin = xMin - MAP_PADDING;
		this.xMax = xMax + MAP_PADDING;
		this.yMax = yMax + MAP_PADDING;
		this.yMin = yMin - MAP_PADDING;
		
		//fill empty position with blank and correct door with do link
		for (int i = xMin; i <= xMax; i++) {
			for (int j = yMin; j <= yMax; j++) {
				
				if (this.map.get(new Pair<>(i, j)) == null) {
					this.map.put(new Pair<>(i, j), ' ');
				}
				
				if (this.map.get(new Pair<>(i, j)) == 'D' && (
						this.map.get(new Pair<>(i + 1, j)) == null || this.map.get(new Pair<>(i + 1, j)) == ' ' ||
						this.map.get(new Pair<>(i - 1, j)) == null || this.map.get(new Pair<>(i - 1, j)) == ' ' ||
						this.map.get(new Pair<>(i, j + 1)) == null ||this.map.get(new Pair<>(i, j + 1)) == ' ' ||
						this.map.get(new Pair<>(i, j - 1)) == null || this.map.get(new Pair<>(i, j - 1)) == ' ')) {
					this.map.put(new Pair<>(i, j), '+');
				}
				
			}
		}
		
		//System.out.println("end build");

		return new WorldGeneratorImpl(this.xMin, this.yMin, this.yMax, this.xMax, this.map);
	}
	
	public Map<Pair<Integer, Integer>, Character> getMap() {
		return this.map;
	}
}