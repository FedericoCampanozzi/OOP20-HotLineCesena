package hotlinecesena.controller.generator;

import java.util.*;
import java.util.function.BiConsumer;
import javafx.util.Pair;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.utilities.MathUtils;
import static java.util.stream.Collectors.*;

/**
 * A common implementations of a generic worlWorldGeneratorBuilderd {@code WorldGeneratorBuilder}
 * @author Federico
 */
public abstract class AbstractWorldGeneratorBuilder implements WorldGeneratorBuilder {
	
	protected static final int ACCETABLE_MAP = 5;
	protected static final int MAX_POSSIBILITY = 10_000;
	protected static final int MAP_PADDING = 10;
	protected Random rnd = new Random();
	protected int xMin, xMax, yMin, yMax;
	protected int pRoomIndex;
	protected Optional<Integer> objRoomIndex = Optional.empty();
	
	// Low level description
	protected Map<Pair<Integer, Integer>, SymbolsType> map = new HashMap<>();

	// High level description
	protected List<Room> baseRooms = new ArrayList<>();
	protected List<Room> rooms = new ArrayList<>();
	/**
	 * {@inheritDoc}
	 */
	@Override
	public WorldGeneratorBuilder addSingleBaseRoom(Room r) {
		this.baseRooms.add(r);
		return this;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public WorldGeneratorBuilder addSomeBaseRoom(List<Room> list) {
		this.baseRooms.addAll(0, list);
		return this;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract WorldGeneratorBuilder generateRooms(int nRoomsMin, int nRoomsMax);
	/**
	 * {@inheritDoc}
	 */
	@Override
	public WorldGeneratorBuilder generatePlayer() {
		haveInitMapAndBaseRoom();		
		this.pRoomIndex = rnd.nextInt(this.rooms.size());
		Room r = this.rooms.get(this.pRoomIndex);
		r.getMap().put(r.getCenter(), SymbolsType.PLAYER);
		this.map.put(r.getCenter(), SymbolsType.PLAYER);
		return this;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public WorldGeneratorBuilder generateKeyObject() {
		haveInitMapAndBaseRoom();
		if(this.rooms.size() <= ACCETABLE_MAP) {
			return this;
		}
		this.objRoomIndex = Optional.of(rnd.nextInt(this.rooms.size()));
		while(this.objRoomIndex.get() == this.pRoomIndex) {
			this.objRoomIndex = Optional.of(rnd.nextInt(this.rooms.size()));
		}
		Room r = this.rooms.get(this.objRoomIndex.get());
		r.getMap().put(r.getCenter(), SymbolsType.KEY_ITEM);
		this.map.put(r.getCenter(), SymbolsType.KEY_ITEM);
		return this;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public WorldGeneratorBuilder generateEnemy(int minInRoom, int maxInRoom) {
		haveInitMapAndBaseRoom();
		return generateTotalRandomnessMany(SymbolsType.ENEMY, minInRoom, maxInRoom);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public WorldGeneratorBuilder generateObstacoles(int minInRoom, int maxInRoom) {
		haveInitMapAndBaseRoom();
		return generateTotalRandomnessMany(SymbolsType.OBSTACOLES, minInRoom, maxInRoom);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public WorldGeneratorBuilder generateItem(int minInRoom, int maxInRoom) {
		haveInitMapAndBaseRoom();
		return generateTotalRandomnessMany(SymbolsType.ITEM, minInRoom, maxInRoom);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public WorldGeneratorBuilder generateWeapons(int minInRoom, int maxInRoom) {
		haveInitMapAndBaseRoom();
		return generateTotalRandomnessMany(SymbolsType.WEAPONS, minInRoom, maxInRoom);
	}
	
	private WorldGeneratorBuilder generateTotalRandomnessMany(SymbolsType type, int minInRoom, int maxInRoom) {
		for (int rIndex = 0; rIndex < this.rooms.size(); rIndex++) {
			if (rIndex != this.pRoomIndex) {
				Room r = this.rooms.get(rIndex);
				int roomObj = MathUtils.randomBetween(rnd, minInRoom, maxInRoom);
				final List<Pair<Integer, Integer>> positions = r.getMap().entrySet().stream().map(itm -> itm.getKey())
						.collect(toList());
				for (int i = 0; i < roomObj; i++) {
					Pair<Integer, Integer> pii = positions.get(rnd.nextInt(positions.size()));
					pii = MathUtils.sum(pii, r.getCenter());
					if (this.map.containsKey(pii) && this.map.get(pii).equals(SymbolsType.WALKABLE)) {
						this.map.put(pii, type);
						r.getMap().put(MathUtils.subtract(pii, r.getCenter()), type);
					}
				}
			}
		}
		return this;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public WorldGeneratorBuilder finishes() {
		haveInitMapAndBaseRoom();
		this.xMin = xMin - MAP_PADDING;
		this.xMax = xMax + MAP_PADDING;
		this.yMax = yMax + MAP_PADDING;
		this.yMin = yMin - MAP_PADDING;
		
		//fill null positions
		this.applyCorrection((i,j) -> {
			if (!this.map.containsKey(new Pair<>(i, j))) {
				this.map.put(new Pair<>(i, j), SymbolsType.VOID);
			}
		});
		
		//check door that have near a void space
		this.applyCorrection((i, j) -> {
			if (this.get(i, j, SymbolsType.DOOR) && checkAdjacent4(i, j, SymbolsType.VOID)) {
				this.map.put(new Pair<>(i, j), SymbolsType.WALL);
			}
		});

		//check walkable corridor
		this.applyCorrection((i, j) -> {
			if ((this.get(i, j, SymbolsType.ENEMY)
					|| this.get(i, j, SymbolsType.OBSTACOLES)) && checkAdjacent8(i, j, SymbolsType.WALL)) {
				this.map.put(new Pair<>(i, j), SymbolsType.WALKABLE);
			}
		});
		
		//open corridor
		this.applyCorrection((i, j) -> {
			// j+
			this.checkSemiAxis(i, j, 0, 1, -1, 0);
			// j-
			this.checkSemiAxis(i, j, 0, -1, 1, 0);
			// i+
			this.checkSemiAxis(i, j, 1, 0, 0, -1);
			// i-
			this.checkSemiAxis(i, j, -1, 0, 0, 1);
		});
		
		
		//Delete remaining doors
		this.applyCorrection((i,j) -> {
			if (get(i, j, SymbolsType.DOOR)) {
				this.map.put(new Pair<>(i, j), SymbolsType.WALL);
			}
		});

		//check walkable that have near a void space
		this.applyCorrection((i, j) -> {
			if (this.get(i, j, SymbolsType.WALKABLE) && checkAdjacent8(i, j, SymbolsType.VOID)) {
				this.map.put(new Pair<>(i, j), SymbolsType.WALL);
			}
		});
		
		return this;
	}
	
	protected void checkSemiAxis(int i, int j, int dI, int dJ, int dInvI, int dInvJ) {
		if(get(i, j, SymbolsType.DOOR) && get(i + dI, j + dJ, SymbolsType.DOOR) && get(i + 2 * dI, j + 2 * dJ, SymbolsType.WALKABLE)) {
			if (dI == 1 || dI == -1) {
				this.map.put(new Pair<>(i, j + dInvJ), SymbolsType.WALKABLE);
				this.map.put(new Pair<>(i + dI + dInvI, j + dInvJ), SymbolsType.WALKABLE);
				this.map.put(new Pair<>(i, j - dInvJ), SymbolsType.WALKABLE);
				this.map.put(new Pair<>(i - dInvI + dI, j - dInvJ), SymbolsType.WALKABLE);
			} else {
				this.map.put(new Pair<>(i + dInvI, j), SymbolsType.WALKABLE);
				this.map.put(new Pair<>(i + dInvI, j + dInvJ + dJ), SymbolsType.WALKABLE);
				this.map.put(new Pair<>(i - dInvI, j), SymbolsType.WALKABLE);
				this.map.put(new Pair<>(i - dInvI, j - dInvJ + dJ), SymbolsType.WALKABLE);
			}
			this.map.put(new Pair<>(i, j), SymbolsType.WALKABLE);
			this.map.put(new Pair<>(i + dI, j + dJ), SymbolsType.WALKABLE);
		}
	}
	
	protected void applyCorrection(BiConsumer<Integer, Integer> correction) {
		for (int i = xMin; i <= xMax; i++) {
			for (int j = yMin; j <= yMax; j++) {
				correction.accept(i, j);
			}
		}
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

	/**
	 * check if generate have called 
	 */
	protected void haveInitMap() {
		if(this.rooms.isEmpty()) {
			throw new IllegalStateException();
		}
	}

	/**
	 * check if addSingleBaseRoom or addSomeBaseRoom have called
	 */
	protected void haveInitBaseRoom() {
		if(this.baseRooms.isEmpty()) {
			throw new IllegalStateException();
		}
	}
	/**
	 * {@inheritDoc}
	 */
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
	
	@Override
	public boolean isKeyObjectPresent() {
		return this.objRoomIndex.isPresent();
	}
}