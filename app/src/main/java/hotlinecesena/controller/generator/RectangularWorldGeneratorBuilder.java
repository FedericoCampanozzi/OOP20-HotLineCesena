package hotlinecesena.controller.generator;

import javafx.util.Pair;

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
	public Pair<Integer, Integer> getDirections(Room room) {
		RectangularRoom roomRect = (RectangularRoom)room;
		int dirX = (rnd.nextInt(3) - 1) * ((roomRect.getWidth() / 2) + 1);
		int dirY = 0;
		if (dirX == 0) {
			dirY = (rnd.nextInt(3) - 1) * ((roomRect.getHeight() / 2) + 1);
		}
		return new Pair<>(dirY, dirX);
	}
}
