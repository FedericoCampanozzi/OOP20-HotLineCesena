package hotlinecesena.controller.generator;

import javafx.util.Pair;

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
	public Pair<Integer, Integer> getDirections(Room room) {
		QuadraticRoom roomQuad = (QuadraticRoom)room;
		int dirX = (rnd.nextInt(3) - 1) * ((roomQuad.getWidth() / 2) + 1);
		int dirY = 0;
		if (dirX == 0) {
			dirY = (rnd.nextInt(3) - 1) * ((roomQuad.getWidth() / 2) + 1);
		}
		return new Pair<>(dirY, dirX);
	}
}
