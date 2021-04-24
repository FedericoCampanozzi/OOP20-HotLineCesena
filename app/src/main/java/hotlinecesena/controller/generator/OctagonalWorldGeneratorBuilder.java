package hotlinecesena.controller.generator;

import javafx.util.Pair;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.utilities.MathUtils;

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
		int nRooms = MathUtils.randomBetween(rnd, nRoomsMin, nRoomsMax);
		
		for (int l = 0; l < MAX_POSSIBILITY && this.rooms.size() < nRooms; l++) {

			OctagonalRoom toPut = (OctagonalRoom) this.baseRooms.get(rnd.nextInt(this.baseRooms.size())).deepCopy();

			if (this.rooms.isEmpty()) {
				generateRoom(new Pair<>(0, 0), toPut);
			} else {
				final int w = (toPut.getWidth() - 1) / 2;
				Pair<Integer, Integer> doorLink = getConnectionsLinking();
				Pair<Integer, Integer> dir = new Pair<>((rnd.nextInt(3) - 1) * (w + 2), (rnd.nextInt(3) - 1) * (w + 2));
				Pair<Integer, Integer> center = MathUtils.sum(doorLink, dir);
				if (canPutRoom(center, doorLink, dir, toPut)) {
					generateRoom(center, toPut);
					toPut.setCenter(center);
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
