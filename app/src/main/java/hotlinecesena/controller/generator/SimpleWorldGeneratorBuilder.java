package hotlinecesena.controller.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import hotlinecesena.utilities.Utilities;
import javafx.util.Pair;

public class SimpleWorldGeneratorBuilder extends AbstractWorldGeneratorBuilder {

	public SimpleWorldGeneratorBuilder(long seed) {
		super(seed);
	}
	

	public SimpleWorldGeneratorBuilder generateRooms(
			final int wMin, final int wMax,
			final int dMin, final int dMax,
			final int nRoomsMin, final int nRoomsMax, 
			final int nBaseRoomsMin, final int nBaseRoomsMax) {
		
		if ((wMin >= wMax) || (nRoomsMin >= nRoomsMax) || (nBaseRoomsMin >= nBaseRoomsMax) || (dMin >= dMax)) {
			new IllegalArgumentException();
		}
		this.checkSimbols();
		
		final int w = rnd.nextInt(wMax) + wMin;
		final int nRooms = rnd.nextInt(nRoomsMax) + nRoomsMin;
		final int nBaseRooms = rnd.nextInt(nBaseRoomsMax) + nBaseRoomsMin;
		final int d = rnd.nextInt(dMax) + dMin;
		
		xMin = Integer.MAX_VALUE;
		yMin = Integer.MAX_VALUE;
		xMax = Integer.MIN_VALUE;
		yMax = Integer.MIN_VALUE;

		for (int i = 0; i < nBaseRooms; i++) {
			baseRooms.add(new QuadraticRoom(this.seed, w, d, this.walkableSimbol, this.doorSimbols, this.wallSimbols));
		}

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

			if (pii.equals(dd) && room.getMap().get(positions).equals(this.doorSimbols)) {
				isNearDoor = true;
			}
		}

		return can && isNearDoor;
	}

	private void generateRoom(Pair<Integer, Integer> center, Room room) {

		for (Entry<Pair<Integer, Integer>, Character> p : room.getMap().entrySet()) {
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
			if (this.map.get(p).equals(this.doorSimbols)) {
				allDoors.add(p);
			}
		}
		return allDoors.get(rnd.nextInt(allDoors.size()));
	}

	@Override
	public WorldGeneratorBuilder generateRooms(Integer... args) {
		this.generateRooms(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
		return this;
	}
}
