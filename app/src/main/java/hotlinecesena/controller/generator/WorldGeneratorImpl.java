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
		
		public DOOR getFirstDoor() {
			return this.fstDoor;
		}

		public DOOR getSecondDoor() {
			return this.scdDoor;
		}

		@Override
		public String toString() {
			return "RoomLinking [fstIndex=" + fstIndex + ", scdIndex=" + scdIndex + ", fstDoor=" + fstDoor
					+ ", scdDoor=" + scdDoor + "]";
		}
	}
	
	private final static int MAX_POSSIBILITY = 5;
	private final static int MAP_PADDING = 2;

	private Random rnd = new Random();
	
	//Low level description
	private Map<Pair<Integer, Integer>, Character> map = new HashMap<>();
	
	//High level description
	private List<Room> baseRooms = new ArrayList<>();
	private List<Room> rooms = new ArrayList<>();
	final List<RoomLinking> linking = new ArrayList<>();
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
	
	public WorldGeneratorImpl(final int seed, final int wMin, final int hMin,
			final int wMax, final int hMax,
			final int nRooms, final int nBaseRooms ) {
		
		if((wMin >= wMax) || (hMin >= hMax)) {
			new IllegalArgumentException("La stanza - quindi la mappa non puo' essere creata");
		}
		
		rnd.setSeed(seed);
		
		xMin = Integer.MAX_VALUE;
		yMin = Integer.MAX_VALUE;
		xMax = Integer.MIN_VALUE;
		yMax = Integer.MIN_VALUE;
		
		final List<Room> openRooms = new ArrayList<>();
		final List<RoomLinking> possibleRoomsLinking = new ArrayList<>();

		baseRooms.add(new Room(5,5,4));
		
		for (int i = 0; i < nBaseRooms; i++) {
			baseRooms.add(new Room(wMin, hMin, 1, wMax, hMax, 4));
			//baseRooms.add(new Room(3,7,4));
		}
		
		generateRoom(this.baseRooms.get(0));
		
		for (int p = 0; p < 500; p++) {
			openRooms.clear();
			possibleRoomsLinking.clear();
			
			if(this.rooms.size() >= nRooms) {
				break;
			}
			
			for (Room room : this.rooms) {
				if (room.connections.size() != 0) {
					openRooms.add(room);
				}
			}
			
			for (int openRoomIndex = 0; openRoomIndex < openRooms.size(); openRoomIndex++) {
				for (int baseRoomIndex = 0; baseRoomIndex < this.baseRooms.size(); baseRoomIndex++) {

					for (DOOR d : this.baseRooms.get(baseRoomIndex).connections.keySet()) {

						if (openRoomIndex != baseRoomIndex &&
								openRooms.get(openRoomIndex).connections.containsKey(DOOR.getInverse(d))) {
							possibleRoomsLinking.add(new RoomLinking(openRoomIndex, baseRoomIndex, DOOR.getInverse(d), d));
						}
						/*
						if (possibleRoomsLinking.size() == MAX_POSSIBILITY) {
							break;
						}*/
					}
				}
			}
			
			System.out.println("FREE : " + openRooms.size());
			System.out.println("ROOMS : " + rooms.size());
			System.out.println("POSS : " + possibleRoomsLinking.size());
			
			
			if(possibleRoomsLinking.size() != 0) {
				RoomLinking link = possibleRoomsLinking.get(rnd.nextInt(possibleRoomsLinking.size()));
				this.linking.add(link);
				Room FirstRoom = openRooms.get(link.fstIndex);
				Room SecondRoom = this.baseRooms.get(link.scdIndex);
				
				///*
				Pair<Integer,Integer> D1POS = FirstRoom.connections.get(link.fstDoor);
				Pair<Integer,Integer> D2POS = SecondRoom.connections.get(link.scdDoor);
				
				if (link.getFirstDoor() == DOOR.LEFT) {
					//SecondRoom.setRelation(new Pair<>(D1POS.getKey() - D2POS.getKey(), -SecondRoom.getHeight()));
					SecondRoom.setRelation(new Pair<>(-SecondRoom.getWidth(), D1POS.getValue() - D2POS.getValue()));
				} else if (link.getFirstDoor()  == DOOR.RIGHT) {
					//SecondRoom.setRelation(new Pair<>(D1POS.getKey() - D2POS.getKey(), FirstRoom.getHeight()));
					SecondRoom.setRelation(new Pair<>(FirstRoom.getWidth(), D1POS.getValue() - D2POS.getValue()));
				} else if (link.getFirstDoor() == DOOR.BOT) {
					//SecondRoom.setRelation(new Pair<>(-SecondRoom.getWidth(), D1POS.getValue() - D2POS.getValue()));
					SecondRoom.setRelation(new Pair<>(D1POS.getKey() - D2POS.getKey(), -SecondRoom.getHeight()));
				} else if (link.getFirstDoor() == DOOR.TOP) {
					//SecondRoom.setRelation(new Pair<>(FirstRoom.getWidth(), D1POS.getValue() - D2POS.getValue()));
					SecondRoom.setRelation(new Pair<>(D1POS.getKey() - D2POS.getKey(), FirstRoom.getHeight()));
				}
				
				if(canGenerate(SecondRoom)) {
					generateRoom(SecondRoom);
					FirstRoom.connections.remove(link.getFirstDoor());					
				}
				//*/
			}
			
			//Collections.shuffle(this.rooms);
			//Collections.shuffle(this.baseRooms);
		}
		
		for(RoomLinking rl : this.linking) {
			System.out.println(rl.toString());
		}
	}
	
	private boolean canGenerate(Room r) {
		boolean can = true;

		for (Entry<Pair<Integer, Integer>, Character> p2 : r.map.entrySet()) {
			if (this.map.containsKey(Utilities.sumPair(r.getRelation(), p2.getKey()))) {
				can = false;
				break;
			}
		}
		
		return can;
	}

	private void generateRoom(Room r) {
		for (Entry<Pair<Integer, Integer>, Character> p2 : r.map.entrySet()) {
			Pair<Integer, Integer> pii = Utilities.sumPair(r.getRelation(), p2.getKey());
			this.map.put(pii, p2.getValue());

			if (pii.getKey() < xMin) {
				xMin = pii.getKey();
			}
			if (pii.getKey() > xMax) {
				xMax = pii.getKey();
			}

			if (pii.getValue() < yMin) {
				yMin = pii.getValue();
			}
			if (pii.getValue() > yMax) {
				yMax = pii.getValue();
			}
		}
		this.rooms.add(r);
	}
	
	public WorldGeneratorImpl build() {
		
		this.xMin = xMin - MAP_PADDING;
		this.xMax = xMax + MAP_PADDING;
		this.yMax = yMax + MAP_PADDING;
		this.yMin = yMin - MAP_PADDING;
		
		//fill empty position with blank and correct door with no link
		for (int i = xMin; i <= xMax; i++) {
			for (int j = yMin; j <= yMax; j++) {
				
				if (this.map.get(new Pair<>(i, j)) == null) {
					this.map.put(new Pair<>(i, j), '\\');
				}
				
				if (this.map.get(new Pair<>(i, j)) == 'D' && (
						this.map.get(new Pair<>(i + 1, j)) == null || this.map.get(new Pair<>(i + 1, j)) == '\\' ||
						this.map.get(new Pair<>(i - 1, j)) == null || this.map.get(new Pair<>(i - 1, j)) == '\\' ||
						this.map.get(new Pair<>(i, j + 1)) == null ||this.map.get(new Pair<>(i, j + 1)) == '\\' ||
						this.map.get(new Pair<>(i, j - 1)) == null || this.map.get(new Pair<>(i, j - 1)) == '\\')) {
					this.map.put(new Pair<>(i, j), '+');
				}
				
			}
		}
		
		return this;
	}
	
	public Map<Pair<Integer, Integer>, Character> getMap() {
		return this.map;
	}
}