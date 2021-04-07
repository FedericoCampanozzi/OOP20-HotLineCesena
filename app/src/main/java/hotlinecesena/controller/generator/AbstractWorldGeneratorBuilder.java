package hotlinecesena.controller.generator;

import java.util.*;
import javafx.util.Pair;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.utilities.Utilities;
import static java.util.stream.Collectors.*;

public abstract class AbstractWorldGeneratorBuilder implements WorldGeneratorBuilder {
	
	protected final static int MAX_POSSIBILITY = 10_000;
	protected final static int MAP_PADDING = 3;
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
		Room r = this.rooms.get(rnd.nextInt(this.rooms.size()));
		r.getMap().put(r.getCenter(), SymbolsType.PLAYER);
		this.map.put(r.getCenter(), SymbolsType.PLAYER);
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder generateEnemy(int minInRoom, int maxInRoom) {
		generateTotalRandomness(SymbolsType.ENEMY, minInRoom, maxInRoom);
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder generateObstacoles(int minInRoom, int maxInRoom) {
		generateTotalRandomness(SymbolsType.OBSTACOLES, minInRoom, maxInRoom);
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder generateAmmo(int minInRoom, int maxInRoom) {
		generateTotalRandomness(SymbolsType.AMMO, minInRoom, maxInRoom);
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder generateMedikit(int minInRoom, int maxInRoom) {
		generateTotalRandomness(SymbolsType.MEDIKIT, minInRoom, maxInRoom);
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder generateWeapons(int minInRoom, int maxInRoom) {
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
		this.xMin = xMin - MAP_PADDING;
		this.xMax = xMax + MAP_PADDING;
		this.yMax = yMax + MAP_PADDING;
		this.yMin = yMin - MAP_PADDING;
		
		for (int i = xMin; i <= xMax; i++) {
			for (int j = yMin; j <= yMax; j++) {

				if (!this.map.containsKey(new Pair<>(i, j))) {
					this.map.put(new Pair<>(i, j), SymbolsType.VOID);
				}
				
				if (this.map.get(new Pair<>(i, j)) == SymbolsType.DOOR && (
						getOrNull(i + 1, j, SymbolsType.VOID) ||
						getOrNull(i - 1, j, SymbolsType.VOID) ||
						getOrNull(i, j + 1, SymbolsType.VOID) ||
						getOrNull(i, j - 1, SymbolsType.VOID))) {
					this.map.put(new Pair<>(i, j), SymbolsType.WALL);
				}
				
				if (this.map.get(new Pair<>(i, j)) == SymbolsType.OBSTACOLES && (
						getOrNull(i + 1, j, SymbolsType.WALL) ||
						getOrNull(i - 1, j, SymbolsType.WALL) ||
						getOrNull(i, j + 1, SymbolsType.WALL) ||
						getOrNull(i, j - 1, SymbolsType.WALL) ||
						getOrNull(i + 1, j + 1, SymbolsType.WALL) ||
						getOrNull(i - 1, j - 1, SymbolsType.WALL) ||
						getOrNull(i - 1, j + 1, SymbolsType.WALL) ||
						getOrNull(i + 1, j - 1, SymbolsType.WALL))) {
					this.map.put(new Pair<>(i, j), SymbolsType.WALKABLE);
				}
				
				// j+
				if (	this.map.get(new Pair<>(i, j)) == SymbolsType.DOOR &&
						getOrNull(i, j + 1, SymbolsType.DOOR) &&
						getOrNull(i, j + 2, SymbolsType.WALKABLE) ) {
					this.map.put(new Pair<>(i, j), SymbolsType.WALKABLE);
					this.map.put(new Pair<>(i, j + 1), SymbolsType.WALKABLE);
				}
				
				// j-
				if (	this.map.get(new Pair<>(i, j)) == SymbolsType.DOOR && 
						getOrNull(i, j - 1, SymbolsType.DOOR) &&
						getOrNull(i, j - 2, SymbolsType.WALKABLE) ) {
					this.map.put(new Pair<>(i, j), SymbolsType.WALKABLE);
					this.map.put(new Pair<>(i, j - 1), SymbolsType.WALKABLE);
				}
				
				// i+
				if (	this.map.get(new Pair<>(i, j)) == SymbolsType.DOOR && 
						getOrNull(i + 1, j, SymbolsType.DOOR)&&
						getOrNull(i + 2, j, SymbolsType.WALKABLE) ) {
					this.map.put(new Pair<>(i, j), SymbolsType.WALKABLE);
					this.map.put(new Pair<>(i + 1, j), SymbolsType.WALKABLE);
				}
				
				// i-
				if (	this.map.get(new Pair<>(i, j)) == SymbolsType.DOOR && 
						getOrNull(i - 1, j, SymbolsType.DOOR) &&
						getOrNull(i - 2, j, SymbolsType.WALKABLE) ) {
					this.map.put(new Pair<>(i, j), SymbolsType.WALKABLE);
					this.map.put(new Pair<>(i - 1, j), SymbolsType.WALKABLE);
				}
			}
		}

		for (int i = xMin; i <= xMax; i++) {
			for (int j = yMin; j <= yMax; j++) {
				if (this.map.get(new Pair<>(i, j)).equals(SymbolsType.DOOR)) {
					this.map.put(new Pair<>(i, j), SymbolsType.WALL);
				}
			}
		}
		
		return this;
	}
	
	private boolean getOrNull(int i, int j, SymbolsType type) {
		return !this.map.containsKey(new Pair<>(i,j)) || this.map.get(new Pair<>(i,j)).equals(type);
	}
	
	@Override
	public WorldGeneratorBuilder build() {
		return this;
	}
	@Override
	public Map<Pair<Integer, Integer>, SymbolsType> getMap(){
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