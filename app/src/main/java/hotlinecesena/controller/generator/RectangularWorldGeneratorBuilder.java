package hotlinecesena.controller.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import javafx.util.Pair;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.utilities.MathUtils;

/**
 * This class provide to generate a map with {@link RectangularRoom} 
 * @author Federico
 */
public class RectangularWorldGeneratorBuilder extends AbstractWorldGeneratorBuilder {
	
	public RectangularWorldGeneratorBuilder() {
		super();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public WorldGeneratorBuilder generateRooms(int nRoomsMin, int nRoomsMax) {
		this.haveInitBaseRoom();
		rnd.setSeed(JSONDataAccessLayer.SEED);
		int nRooms = MathUtils.randomBetween(rnd, nRoomsMin, nRoomsMax);
		
		for (int l = 0; l < MAX_POSSIBILITY && this.rooms.size() < nRooms; l++) {

			RectangularRoom toPut = (RectangularRoom)this.baseRooms.get(rnd.nextInt(this.baseRooms.size())).deepCopy();

			if (this.rooms.isEmpty()) {
				generateRoom(new Pair<>(0, 0), toPut);
			} else {
				Pair<Integer, Integer> doorLink = getConnectionsLinking();
				int dirX = (rnd.nextInt(3) - 1) * ((toPut.getWidth() / 2) + 1);
				int dirY = 0;
				if (dirX == 0) {
					dirY = (rnd.nextInt(3) - 1) * ((toPut.getHeight() / 2) + 1);
				}
				Pair<Integer, Integer> dir = new Pair<>(dirY, dirX);
				Pair<Integer, Integer> center = MathUtils.sum(doorLink, dir);
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
			var pii = MathUtils.sum(center, positions);
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
			Pair<Integer, Integer> position = MathUtils.sum(center, p.getKey());

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
}
