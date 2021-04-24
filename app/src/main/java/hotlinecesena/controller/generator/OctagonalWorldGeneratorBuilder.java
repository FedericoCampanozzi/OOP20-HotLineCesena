package hotlinecesena.controller.generator;

import javafx.util.Pair;
import hotlinecesena.model.dataccesslayer.SymbolsType;

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
	public Pair<Integer, Integer> getDirections(Room room) {
		OctagonalRoom roomOct = (OctagonalRoom)room;
		final int w = (roomOct.getWidth() - 1) / 2;
		return new Pair<>((rnd.nextInt(3) - 1) * (w + 2), (rnd.nextInt(3) - 1) * (w + 2));
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
