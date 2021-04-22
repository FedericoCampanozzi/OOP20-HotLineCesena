package hotlinecesena.controller.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import javafx.util.Pair;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.utilities.Utilities;

/**
 * This class provide to generate a map with {@link OctagonalRoom} 
 * @author Federico
 */
public class OctagonalWorldGeneratorBuilder extends AbstractWorldGeneratorBuilder {
	
	public OctagonalWorldGeneratorBuilder() {
		super();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public WorldGeneratorBuilder generateRooms(int nRoomsMin, int nRoomsMax) {
		this.haveInitBaseRoom();
		rnd.setSeed(JSONDataAccessLayer.SEED);
		int nRooms = Utilities.randomBetween(rnd, nRoomsMin, nRoomsMax);
		
		for (int l = 0; l < MAX_POSSIBILITY && this.rooms.size() < nRooms; l++) {

			OctagonalRoom toPut = (OctagonalRoom) this.baseRooms.get(rnd.nextInt(this.baseRooms.size())).deepCopy();

			if (this.rooms.isEmpty()) {
				generateRoom(new Pair<>(0, 0), toPut);
			} else {
				final int w = (toPut.getWidth() - 1) / 2;
				Pair<Integer, Integer> doorLink = getConnectionsLinking();
				Pair<Integer, Integer> dir = new Pair<>((rnd.nextInt(3) - 1) * (w + 2), (rnd.nextInt(3) - 1) * (w + 2));
				Pair<Integer, Integer> center = Utilities.sumPair(doorLink, dir);
				if (canPutRoom(center, doorLink, dir, toPut)) {
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

			if (pii.equals(dd) && room.getMap().get(positions).equals(SymbolsType.DOOR)) {
				isNearDoor = true;
			}
		}

		return can && isNearDoor;
	}

	private void generateRoom(Pair<Integer, Integer> center, Room room) {

		for (Entry<Pair<Integer, Integer>, SymbolsType> p : room.getMap().entrySet()) {
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
			if (this.map.get(p).equals(SymbolsType.DOOR)) {
				allDoors.add(p);
			}
		}
		return allDoors.get(rnd.nextInt(allDoors.size()));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public WorldGeneratorBuilder finishes() {
		this.haveInitMapAndBaseRoom();
		this.xMin = xMin - MAP_PADDING;
		this.xMax = xMax + MAP_PADDING;
		this.yMax = yMax + MAP_PADDING;
		this.yMin = yMin - MAP_PADDING;

		for (int i = xMin; i <= xMax; i++) {
			for (int j = yMin; j <= yMax; j++) {

				if (!this.map.containsKey(new Pair<>(i, j))) {
					this.map.put(new Pair<>(i, j), SymbolsType.VOID);
				}
			}
		}

		for (int i = xMin; i <= xMax; i++) {
			for (int j = yMin; j <= yMax; j++) {

				if (this.map.get(new Pair<>(i, j)) == SymbolsType.OBSTACOLES
						&& this.checkAdjacent8(i, j, SymbolsType.WALL)) {
					this.map.put(new Pair<>(i, j), SymbolsType.WALKABLE);
				}
			}
		}

		for (int i = xMin; i <= xMax; i++) {
			for (int j = yMin; j <= yMax; j++) {
				if (this.map.get(new Pair<>(i, j)).equals(SymbolsType.DOOR)
						&& !this.checkAdjacent8(i, j, SymbolsType.VOID)) {
					this.map.put(new Pair<>(i, j), SymbolsType.WALL);
				}
			}
		}

		return this;
	}
}
