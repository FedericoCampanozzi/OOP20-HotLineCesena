package hotlinecesena.controller.generator;

import java.util.*;
import java.util.Map.Entry;

import static java.util.stream.Collectors.*;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SIMBOLS_TYPE;
import hotlinecesena.utilities.Utilities;
import javafx.util.Pair;

public class WorldGeneratorBuilderImpl implements WorldGeneratorBuilder {
	
	private final static int MAX_POSSIBILITY = 10000;
	private final static int MAP_PADDING = 2;
	private Random rnd = new Random();
	private int xMin, xMax, yMin, yMax;
	
	// Low level description
	private Map<Pair<Integer, Integer>, SIMBOLS_TYPE> map = new HashMap<>();

	// High level description
	private List<Room> baseRooms = new ArrayList<>();
	private List<Room> rooms = new ArrayList<>();
	
	@Override
	public WorldGeneratorBuilder addSingleBaseRoom(Room r) {
		this.baseRooms.add(r);
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder addSomeBaseRoom(List<Room> list) {
		this.baseRooms.addAll(0, list);
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder generateRooms(int nRoomsMin, int nRoomsMax) {
		rnd.setSeed(JSONDataAccessLayer.SEED);
		int nRooms = rnd.nextInt(nRoomsMax-nRoomsMin)+nRoomsMin;
		for (int l = 0; l < MAX_POSSIBILITY && this.rooms.size() < nRooms; l++) {

			QuadraticRoom toPut = (QuadraticRoom)this.baseRooms.get(rnd.nextInt(this.baseRooms.size())).deepCopy();

			if (this.rooms.isEmpty()) {
				generateRoom(new Pair<>(0, 0), toPut);
			} else {
				Pair<Integer, Integer> doorLink = getConnectionsLinking();
				int dirX = (rnd.nextInt(3) - 1) * ((toPut.getWidth() / 2) + 1);
				int dirY = 0;
				if (dirX == 0) {
					dirY = (rnd.nextInt(3) - 1) * ((toPut.getWidth() / 2) + 1);
				}
				Pair<Integer, Integer> dir = new Pair<>(dirY, dirX);
				Pair<Integer, Integer> center = Utilities.sumPair(doorLink, dir);
				if (canPutRoom(center, doorLink, dir, toPut)) {
					//Non e' safe
					//center = Utilities.sumPair(center, new Pair<>(-(int)Math.signum(dirY), -(int)Math.signum(dirX)));
					generateRoom(center, toPut);
					toPut.setCenter(center);
				}
			}
		}

		return this;
	}
	
	private boolean canPutRoom(Pair<Integer, Integer> center, Pair<Integer, Integer> doorLink,
			Pair<Integer, Integer> dir, Room room) {
		boolean can = true;
		boolean isNearDoor = false;
		Pair<Integer, Integer> dd = new Pair<>(doorLink.getKey() + (int) Math.signum(dir.getKey()),
				doorLink.getValue() + (int) Math.signum(dir.getValue()));

		for (Pair<Integer, Integer> positions : room.getMap().keySet()) {
			var pii = Utilities.sumPair(center, positions);
			if (this.map.containsKey(pii)) {
				can = false;
				break;
			}

			if (pii.equals(dd) && room.getMap().get(positions).equals(SIMBOLS_TYPE.DOOR)) {
				isNearDoor = true;
			}
		}

		return can && isNearDoor;
	}

	private void generateRoom(Pair<Integer, Integer> center, Room room) {

		for (Entry<Pair<Integer, Integer>, SIMBOLS_TYPE> p : room.getMap().entrySet()) {
			Pair<Integer, Integer> position = Utilities.sumPair(center, p.getKey());

			this.map.put(position, p.getValue());

			if (position.getKey() < this.xMin) {
				this.xMin = position.getKey();
			}
			if (position.getKey() > this.xMax) {
				this.xMax = position.getKey();
			}

			if (position.getValue() < this.yMin) {
				this.yMin = position.getValue();
			}
			if (position.getValue() > this.yMax) {
				this.yMax = position.getValue();
			}
		}

		this.rooms.add(room);
	}

	private Pair<Integer, Integer> getConnectionsLinking() {
		List<Pair<Integer, Integer>> allDoors = new ArrayList<>();
		for (Pair<Integer, Integer> p : this.map.keySet()) {
			if (this.map.get(p).equals(SIMBOLS_TYPE.DOOR)) {
				allDoors.add(p);
			}
		}
		return allDoors.get(rnd.nextInt(allDoors.size()));
	}

	@Override
	public WorldGeneratorBuilder generatePlayer() {
		Room r = this.rooms.get(rnd.nextInt(this.rooms.size()));
		r.getMap().put(r.getCenter(), SIMBOLS_TYPE.PLAYER);
		this.map.put(r.getCenter(), SIMBOLS_TYPE.PLAYER);
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder generateEnemy(int minRoom, int maxRoom) {
		generateTotalRandomness(SIMBOLS_TYPE.ENEMY, minRoom, maxRoom);
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder generateObstacoles(int minRoom, int maxRoom) {
		generateTotalRandomness(SIMBOLS_TYPE.OBSTACOLES, minRoom, maxRoom);
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder generateAmmo(int minRoom, int maxRoom) {
		generateTotalRandomness(SIMBOLS_TYPE.AMMO, minRoom, maxRoom);
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder generateMedikit(int minRoom, int maxRoom) {
		generateTotalRandomness(SIMBOLS_TYPE.MEDIKIT, minRoom, maxRoom);
		return this;
	}
	
	private WorldGeneratorBuilder generateTotalRandomness(SIMBOLS_TYPE type, int minRoom, int maxRoom) {
		for(Room r : this.rooms) {
			
			int roomObj = rnd.nextInt(maxRoom - minRoom) + minRoom;
			final List<Pair<Integer,Integer>> positions = r.getMap().entrySet().stream().map(itm->itm.getKey()).collect(toList());
			
			for(int i=0; i < roomObj; i++) {
				Pair<Integer,Integer> pii = positions.get(rnd.nextInt(positions.size()));
				pii = Utilities.sumPair(pii, r.getCenter());
				if(this.map.get(pii).equals(SIMBOLS_TYPE.WALKABLE)) {
					this.map.put(pii, type);
					r.getMap().put(Utilities.subPair(pii, r.getCenter()), type);
				}
			}
		}
		
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder finishes() {
		this.xMin = xMin - MAP_PADDING;
		this.xMax = xMax + MAP_PADDING;
		this.yMax = yMax + MAP_PADDING;
		this.yMin = yMin - MAP_PADDING;
		
		for (int i = xMin; i <= xMax; i++) {
			for (int j = yMin; j <= yMax; j++) {

				if (!this.map.containsKey(new Pair<>(i, j))) {
					this.map.put(new Pair<>(i, j), SIMBOLS_TYPE.VOID);
				}
				
				if (this.map.get(new Pair<>(i, j)) == SIMBOLS_TYPE.DOOR && (
						getOrNull(i + 1, j, SIMBOLS_TYPE.VOID) ||
						getOrNull(i - 1, j, SIMBOLS_TYPE.VOID) ||
						getOrNull(i, j + 1, SIMBOLS_TYPE.VOID) ||
						getOrNull(i, j - 1, SIMBOLS_TYPE.VOID))) {
					this.map.put(new Pair<>(i, j), SIMBOLS_TYPE.WALL);
				}
				
				if (this.map.get(new Pair<>(i, j)) == SIMBOLS_TYPE.OBSTACOLES && (
						getOrNull(i + 1, j, SIMBOLS_TYPE.WALL) ||
						getOrNull(i - 1, j, SIMBOLS_TYPE.WALL) ||
						getOrNull(i, j + 1, SIMBOLS_TYPE.WALL) ||
						getOrNull(i, j - 1, SIMBOLS_TYPE.WALL) ||
						getOrNull(i + 1, j + 1, SIMBOLS_TYPE.WALL) ||
						getOrNull(i - 1, j - 1, SIMBOLS_TYPE.WALL) ||
						getOrNull(i - 1, j + 1, SIMBOLS_TYPE.WALL) ||
						getOrNull(i + 1, j - 1, SIMBOLS_TYPE.WALL))) {
					this.map.put(new Pair<>(i, j), SIMBOLS_TYPE.WALKABLE);
				}
				
				// j +
				if (	this.map.get(new Pair<>(i, j)) == SIMBOLS_TYPE.DOOR &&
						getOrNull(i, j + 1, SIMBOLS_TYPE.DOOR) &&
						getOrNull(i, j + 2, SIMBOLS_TYPE.WALKABLE) ) {
					this.map.put(new Pair<>(i, j), SIMBOLS_TYPE.WALKABLE);
					this.map.put(new Pair<>(i, j + 1), SIMBOLS_TYPE.WALKABLE);
				}
				
				// j -
				if (	this.map.get(new Pair<>(i, j)) == SIMBOLS_TYPE.DOOR && 
						getOrNull(i, j - 1, SIMBOLS_TYPE.DOOR) &&
						getOrNull(i, j - 2, SIMBOLS_TYPE.WALKABLE) ) {
					this.map.put(new Pair<>(i, j), SIMBOLS_TYPE.WALKABLE);
					this.map.put(new Pair<>(i, j - 1), SIMBOLS_TYPE.WALKABLE);
				}
				
				// i +
				if (	this.map.get(new Pair<>(i, j)) == SIMBOLS_TYPE.DOOR && 
						getOrNull(i + 1, j, SIMBOLS_TYPE.DOOR)&&
						getOrNull(i + 2, j, SIMBOLS_TYPE.WALKABLE) ) {
					this.map.put(new Pair<>(i, j), SIMBOLS_TYPE.WALKABLE);
					this.map.put(new Pair<>(i + 1, j), SIMBOLS_TYPE.WALKABLE);
				}
				
				// i -
				if (	this.map.get(new Pair<>(i, j)) == SIMBOLS_TYPE.DOOR && 
						getOrNull(i - 1, j, SIMBOLS_TYPE.DOOR) &&
						getOrNull(i - 2, j, SIMBOLS_TYPE.WALKABLE) ) {
					this.map.put(new Pair<>(i, j), SIMBOLS_TYPE.WALKABLE);
					this.map.put(new Pair<>(i - 1, j), SIMBOLS_TYPE.WALKABLE);
				}
			}
		}

		for (int i = xMin; i <= xMax; i++) {
			for (int j = yMin; j <= yMax; j++) {
				if (this.map.get(new Pair<>(i, j)).equals(SIMBOLS_TYPE.DOOR)) {
					this.map.put(new Pair<>(i, j), SIMBOLS_TYPE.WALL);
				}
			}
		}
		
		return this;
	}
	
	private boolean getOrNull(int i, int j, SIMBOLS_TYPE type) {
		return !this.map.containsKey(new Pair<>(i,j)) || this.map.get(new Pair<>(i,j)).equals(type);
	}
	
	@Override
	public WorldGeneratorBuilder build() {
		return this;
	}
	@Override
	public Map<Pair<Integer, Integer>, SIMBOLS_TYPE> getMap(){
		return this.map;
	}
	
	@Override
	public int getMinX() {
		return this.xMin;
	}
	
	@Override
	public int getMaxX() {
		return this.xMax;
	}
	
	@Override
	public int getMinY() {
		return this.yMin;
	}
	
	@Override
	public int getMaxY() {
		return this.yMax;
	}
}