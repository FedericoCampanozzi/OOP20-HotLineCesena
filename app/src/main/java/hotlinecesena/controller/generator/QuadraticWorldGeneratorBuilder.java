package hotlinecesena.controller.generator;

import javafx.util.Pair;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.utilities.MathUtils;

/**
 * This class provide to generate a map with {@link QuadraticRoom} 
 * @author Federico
 */
public class QuadraticWorldGeneratorBuilder extends AbstractWorldGeneratorBuilder {
	
	public QuadraticWorldGeneratorBuilder() {
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
				Pair<Integer, Integer> center = MathUtils.sum(doorLink, dir);
				if (canPutRoom(center, doorLink, dir, toPut)) {
					generateRoom(center, toPut);
					toPut.setCenter(center);
				}
			}
		}
		return this;
	}
}
