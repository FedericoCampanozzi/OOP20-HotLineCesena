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
		
		public Room(final int d) {
			
			w = rnd.nextInt(10) + 5;
			h = rnd.nextInt(10) + 5;
			
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					if (i == 0 || j == 0 || i == w - 1 || j == h - 1) {
						map.put(new Pair<>(i, j), 'W');
					} else {
						map.put(new Pair<>(i, j), ' ');
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

			String debug = w + " X " + h + " -> " + this.connections.keySet().toString() + "\n";

			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					debug += map.get(new Pair<>(i, j));
				}
				debug += "\n";
			}

			System.out.println(debug);
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
	}
	
	private Random rnd = new Random();
	
	//Low level description
	private Map<Pair<Integer, Integer>, Character> map = new HashMap<>();
	
	//High level description
	private List<Room> baseRooms = new ArrayList<>();
	private List<Room> rooms = new ArrayList<>();
	private int xMin, xMax, yMin, yMax;
	
	private WorldGeneratorImpl(final Map<Pair<Integer, Integer>, Character> m) {
		this.map = m;
	}

	public WorldGeneratorImpl() {
		xMin = Integer.MAX_VALUE;
		yMin = Integer.MAX_VALUE;
		xMax = 0;
		yMin = 0;
		
		List<Room> freeRooms = new ArrayList<>();
		
		baseRooms.add(new Room(4));
		
		for (int i = 0; i < 15; i++) {
			baseRooms.add(new Room(rnd.nextInt(4) + 1));
		}
		
		
		this.rooms.add(this.baseRooms.get(0));
		
		for(int i = 0; i < 5; i++) {
			for(Room r : this.rooms) {
				if(r.connections.size() != 0) {
					freeRooms.add(r);
				}
			}
			
			for(Room r : freeRooms) {
				for(Room att : this.baseRooms) {
					List<DOOR> attPoss = new ArrayList<>();
					for(DOOR d : att.connections.keySet()) {
						if(r.connections.containsKey(DOOR.getInverse(d))) {
							attPoss.add(DOOR.getInverse(d));
						}
					}
					
					if(attPoss.size() != 0) {
						DOOR d =  attPoss.get(rnd.nextInt(attPoss.size()));
						
						generateRoom(r);
						
						if (d == DOOR.LEFT) {
							att.setRelation(new Pair<>(-att.getWidth() + 1, 0));
						} else if (d == DOOR.RIGHT) {
							att.setRelation(new Pair<>(r.getWidth() + 1, 0));
						} else if (d == DOOR.BOT) {
							att.setRelation(new Pair<>(0, -att.getHeight() + 1));
						} else if (d == DOOR.TOP) {
							att.setRelation(new Pair<>(0, r.getHeight() - 1));
						}
						
						generateRoom(att);
						
						break;
					}
				}
			}
		}
		
		System.out.println("Create");
	}
	
	private void generateRoom(Room r) {
		boolean can = true;
		
		for(Entry<Pair<Integer,Integer>, Character> p2 : r.map.entrySet()) {
			if(p2.getValue() == ' ' && this.map.containsKey(Utilities.sumPair(r.getRelation(), p2.getKey()))) {
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
				} else if(pii.getKey() > xMax) {
					xMax = pii.getKey();
				}
				
				if(pii.getValue() < yMin) {
					yMin = pii.getValue();
				} else if(pii.getValue() > yMax) {
					yMax = pii.getValue();
				}
			}
		}
	}
	
	public WorldGeneratorImpl build() {
		
		System.out.println("Build");
		
		//fill empty position with blank
		for (int i = yMin; i < yMax; i++) {
			for (int j = xMin; j < xMax; j++) {
				if(this.map.get(new Pair<>(i,j)) == null) {
					this.map.put(new Pair<>(i,j) , '_');
				}
			}
		}

		System.out.println("end build");
		
		return new WorldGeneratorImpl(this.map);
	}
	
	public Map<Pair<Integer, Integer>, Character> getMap() {
		return this.map;
	}
}