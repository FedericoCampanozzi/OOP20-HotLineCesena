package hotlinecesena.controller.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import hotlinecesena.utilities.Utilities;
import javafx.util.Pair;

public abstract class AbstractWorldGeneratorBuilder implements WorldGeneratorBuilder {
	
	protected final static int MAX_POSSIBILITY = 10000;
	protected final static int MAP_PADDING = 2;
	protected Random rnd = new Random();
	protected int xMin, xMax, yMin, yMax;
	protected Character walkableSimbol = null, enemySimbol = null, playerSimbol = null, 
			doorSimbols = null, voidSimbols = null, wallSimbols = null;
	protected long seed;
	
	// Low level description
	protected Map<Pair<Integer, Integer>, Character> map = new HashMap<>();

	// High level description
	protected List<Room> baseRooms = new ArrayList<>();
	protected List<Room> rooms = new ArrayList<>();

	public AbstractWorldGeneratorBuilder(long seed) {
		rnd.setSeed(seed);
		this.seed = seed;
	}



	@Override
	public WorldGeneratorBuilder setSimbols(char walkableSimbol, char enemySimbol, char playerSimbol,
			char doorSimbols, char voidSimbols, char wallSimbols) {

		this.walkableSimbol = walkableSimbol;
		this.enemySimbol = enemySimbol;
		this.playerSimbol = playerSimbol;
		this.doorSimbols = doorSimbols;
		this.voidSimbols = voidSimbols;
		this.wallSimbols = wallSimbols;
		
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder generatePlayer() {
		this.checkMap();
		this.checkSimbols();
		Room r = this.rooms.get(rnd.nextInt(this.rooms.size()));
		r.getMap().put(r.getCenter(), playerSimbol);
		this.map.put(r.getCenter(), playerSimbol);
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder generateEnemy(float rate, int maxEnemyRoom) {
		this.checkMap();
		this.checkSimbols();
		for(Room r : this.rooms) {
			int enemyCount = 0;
			for(Entry<Pair<Integer,Integer>,Character> point : r.getMap().entrySet()) {
				if(enemyCount<maxEnemyRoom &&  point.getValue().equals(walkableSimbol) && rnd.nextFloat() < rate) {
					r.getMap().put(point.getKey(), enemySimbol);
					this.map.put(Utilities.sumPair(point.getKey(), r.getCenter()), enemySimbol);
					enemyCount++;
				}
			}
		}
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder generateDecorations() {
		this.checkMap();
		this.checkSimbols();
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder finalCheck() {
		this.checkMap();
		this.checkSimbols();
		
		this.xMin = xMin - MAP_PADDING;
		this.xMax = xMax + MAP_PADDING;
		this.yMax = yMax + MAP_PADDING;
		this.yMin = yMin - MAP_PADDING;
		
		for (int i = xMin; i <= xMax; i++) {
			for (int j = yMin; j <= yMax; j++) {

				if (this.map.get(new Pair<>(i, j)) == (null)) {
					this.map.put(new Pair<>(i, j), voidSimbols);
				}
				
				if (this.map.get(new Pair<>(i, j)) == doorSimbols && (
						this.map.get(new Pair<>(i + 1, j)) == (null) || this.map.get(new Pair<>(i + 1, j)) == voidSimbols || 
						this.map.get(new Pair<>(i - 1, j)) == (null) || this.map.get(new Pair<>(i - 1, j)) == voidSimbols || 
						this.map.get(new Pair<>(i, j + 1)) == (null) || this.map.get(new Pair<>(i, j + 1)) == voidSimbols || 
						this.map.get(new Pair<>(i, j - 1)) == (null) || this.map.get(new Pair<>(i, j - 1)) == voidSimbols)) {
					this.map.put(new Pair<>(i, j), wallSimbols);
				}
				
				if (	this.map.get(new Pair<>(i, j)).equals(doorSimbols) && 
						this.map.get(new Pair<>(i + 1, j)).equals(doorSimbols) && 
						this.map.get(new Pair<>(i + 2, j)).equals(walkableSimbol)){
					this.map.put(new Pair<>(i, j), walkableSimbol);
					this.map.put(new Pair<>(i + 1, j), walkableSimbol);
				}
				
				if (	this.map.get(new Pair<>(i, j)).equals(doorSimbols) && 
						this.map.get(new Pair<>(i, j + 1)).equals(doorSimbols) && 
						this.map.get(new Pair<>(i, j + 2)).equals(walkableSimbol)){
					this.map.put(new Pair<>(i, j), walkableSimbol);
					this.map.put(new Pair<>(i, j + 1), walkableSimbol);
				}
				
				if (	this.map.get(new Pair<>(i, j)).equals(doorSimbols) && 
						this.map.get(new Pair<>(i - 1, j)).equals(doorSimbols) && 
						this.map.get(new Pair<>(i - 2, j)).equals(walkableSimbol)){
					this.map.put(new Pair<>(i, j), walkableSimbol);
					this.map.put(new Pair<>(i - 1, j), walkableSimbol);
				}
				
				if (	this.map.get(new Pair<>(i, j)).equals(doorSimbols) && 
						this.map.get(new Pair<>(i, j - 1)).equals(doorSimbols) && 
						this.map.get(new Pair<>(i, j - 2)).equals(walkableSimbol)){
					this.map.put(new Pair<>(i, j), walkableSimbol);
					this.map.put(new Pair<>(i, j - 1), walkableSimbol);
				}
			}
		}

		for (int i = xMin; i <= xMax; i++) {
			for (int j = yMin; j <= yMax; j++) {
				if (this.map.get(new Pair<>(i, j)).equals(doorSimbols)) {
					this.map.put(new Pair<>(i, j), wallSimbols);
				}
			}
		}
		return this;
	}
	
	@Override
	public WorldGeneratorBuilder build() {
		return this;
	}
	
	@Override
	public Map<Pair<Integer, Integer>, Character> getMap(){
		this.checkMap();
		this.checkSimbols();
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
	
	protected void checkSimbols() {
		if(this.doorSimbols.equals(null)) {
			throw new IllegalStateException();
		}
	}
	
	protected void checkMap() {
		if(this.map.keySet().isEmpty()) {
			throw new IllegalStateException();
		}
	}
}
