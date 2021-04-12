package hotlinecesena.controller.generator;

import java.util.*;
import javafx.util.Pair;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.utilities.Utilities;
import static java.util.stream.Collectors.*;

public abstract class AbstractWorldGeneratorBuilder implements WorldGeneratorBuilder {
	
	protected final static int MAX_POSSIBILITY = 10_000;
	protected final static int MAP_PADDING = 10;
	protected Random rnd = new Random();
	protected int xMin, xMax, yMin, yMax;
	
	// Low level description
	protected Map<Pair<Integer, Integer>, SymbolsType> map = new HashMap<>();

	// High level description
	protected List<Room> baseRooms = new ArrayList<>();
	protected List<Room> rooms = new ArrayList<>();
	
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
	public abstract WorldGeneratorBuilder generateRooms(int nRoomsMin, int nRoomsMax);

	@Override
	public WorldGeneratorBuilder generatePlayer() {
		haveInitMapAndBaseRoom();
		Room r = this.rooms.get(rnd.nextInt(this.rooms.size()));
		r.getMap().put(r.getCenter(), SymbolsType.PLAYER);
		this.map.put(r.getCenter(), SymbolsType.PLAYER);
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder generateEnemy(int minInRoom, int maxInRoom) {
		haveInitMapAndBaseRoom();
		generateTotalRandomness(SymbolsType.ENEMY, minInRoom, maxInRoom);
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder generateObstacoles(int minInRoom, int maxInRoom) {
		haveInitMapAndBaseRoom();
		generateTotalRandomness(SymbolsType.OBSTACOLES, minInRoom, maxInRoom);
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder generateItem(int minInRoom, int maxInRoom) {
		haveInitMapAndBaseRoom();
		generateTotalRandomness(SymbolsType.ITEM, minInRoom, maxInRoom);
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder generateWeapons(int minInRoom, int maxInRoom) {
		haveInitMapAndBaseRoom();
		generateTotalRandomness(SymbolsType.WEAPONS, minInRoom, maxInRoom);
		return this;
	}
	
	private WorldGeneratorBuilder generateTotalRandomness(SymbolsType type, int minInRoom, int maxInRoom) {
		for(Room r : this.rooms) {
			
			int roomObj = Utilities.RandomBetween(rnd, minInRoom, maxInRoom);
			final List<Pair<Integer,Integer>> positions = r.getMap().entrySet().stream().map(itm->itm.getKey()).collect(toList());
			
			for(int i=0; i < roomObj; i++) {
				Pair<Integer,Integer> pii = positions.get(rnd.nextInt(positions.size()));
				pii = Utilities.sumPair(pii, r.getCenter());
				if(this.map.get(pii).equals(SymbolsType.WALKABLE)) {
					this.map.put(pii, type);
					r.getMap().put(Utilities.subPair(pii, r.getCenter()), type);
				}
			}
		}
		
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder finishes() {
		haveInitMapAndBaseRoom();
		this.xMin = xMin - MAP_PADDING;
		this.xMax = xMax + MAP_PADDING;
		this.yMax = yMax + MAP_PADDING;
		this.yMin = yMin - MAP_PADDING;
		
		//fill null positions
		for (int i = xMin; i <= xMax; i++) {
			for (int j = yMin; j <= yMax; j++) {

				if (!this.map.containsKey(new Pair<>(i, j))) {
					this.map.put(new Pair<>(i, j), SymbolsType.VOID);
				}
			}
		}
		
		//adjust DOOR and OBSTACOLES spawn
		for (int i = xMin; i <= xMax; i++) {
			for (int j = yMin; j <= yMax; j++) {

				if (this.map.get(new Pair<>(i, j)) == SymbolsType.DOOR && checkAdjacent4(i, j, SymbolsType.VOID)) {
					this.map.put(new Pair<>(i, j), SymbolsType.WALL);
				}

				if (this.map.get(new Pair<>(i, j)) == SymbolsType.OBSTACOLES
						&& checkAdjacent8(i, j, SymbolsType.WALL)) {
					this.map.put(new Pair<>(i, j), SymbolsType.WALKABLE);
				}
				
				// j+
				if (	this.map.get(new Pair<>(i, j)) == SymbolsType.DOOR &&
						get(i, j + 1, SymbolsType.DOOR) &&
						get(i, j + 2, SymbolsType.WALKABLE) ) {
					this.map.put(new Pair<>(i, j), SymbolsType.WALKABLE);
					this.map.put(new Pair<>(i, j + 1), SymbolsType.WALKABLE);
				}
				
				// j-
				if (	this.map.get(new Pair<>(i, j)) == SymbolsType.DOOR && 
						get(i, j - 1, SymbolsType.DOOR) &&
						get(i, j - 2, SymbolsType.WALKABLE) ) {
					this.map.put(new Pair<>(i, j), SymbolsType.WALKABLE);
					this.map.put(new Pair<>(i, j - 1), SymbolsType.WALKABLE);
				}
				
				// i+
				if (	this.map.get(new Pair<>(i, j)) == SymbolsType.DOOR && 
						get(i + 1, j, SymbolsType.DOOR)&&
						get(i + 2, j, SymbolsType.WALKABLE) ) {
					this.map.put(new Pair<>(i, j), SymbolsType.WALKABLE);
					this.map.put(new Pair<>(i + 1, j), SymbolsType.WALKABLE);
				}
				
				// i-
				if (	this.map.get(new Pair<>(i, j)) == SymbolsType.DOOR && 
						get(i - 1, j, SymbolsType.DOOR) &&
						get(i - 2, j, SymbolsType.WALKABLE) ) {
					this.map.put(new Pair<>(i, j), SymbolsType.WALKABLE);
					this.map.put(new Pair<>(i - 1, j), SymbolsType.WALKABLE);
				}
			}
		}

		for (int i = xMin; i <= xMax; i++) {
			for (int j = yMin; j <= yMax; j++) {
				if (get(i, j, SymbolsType.DOOR)) {
					this.map.put(new Pair<>(i, j), SymbolsType.WALL);
				}
			}
		}
		
		return this;
	}
	
	
	protected boolean checkAdjacent4(int i, int j, SymbolsType type) {
		return get(i + 1, j, type) || get(i - 1, j, type) || get(i, j + 1, type) || get(i, j - 1, type);
	}
	
	protected boolean checkAdjacent8(int i, int j, SymbolsType type) {
		return checkAdjacent4(i, j, type) || get(i + 1, j + 1, type) || get(i - 1, j - 1, type)
				|| get(i - 1, j + 1, type) || get(i + 1, j - 1, type);
	}
	
	protected boolean get(int i, int j, SymbolsType type) {
		return this.map.get(new Pair<>(i,j)).equals(type);
	}
	
	protected void haveInitMapAndBaseRoom() {
		this.haveInitMap();
		this.haveInitBaseRoom();
	}

	//check if generate have called
	protected void haveInitMap() {
		if(this.rooms.isEmpty()) {
			throw new IllegalStateException();
		}
	}

	//check if addSingleBaseRoom or addSomeBaseRoom have called
	protected void haveInitBaseRoom() {
		if(this.baseRooms.isEmpty()) {
			throw new IllegalStateException();
		}
	}
	
	@Override
	public WorldGeneratorBuilder build() {
		haveInitMapAndBaseRoom();
		return this;
	}
	@Override
	public Map<Pair<Integer, Integer>, SymbolsType> getMap(){
		haveInitMapAndBaseRoom();
		return this.map;
	}
	
	@Override
	public int getMinX() {
		haveInitMapAndBaseRoom();
		return this.xMin;
	}
	
	@Override
	public int getMaxX() {
		haveInitMapAndBaseRoom();
		return this.xMax;
	}
	
	@Override
	public int getMinY() {
		haveInitMapAndBaseRoom();
		return this.yMin;
	}
	
	@Override
	public int getMaxY() {
		haveInitMapAndBaseRoom();
		return this.yMax;
	}
}