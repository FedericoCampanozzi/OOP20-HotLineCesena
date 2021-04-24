package hotlinecesena.controller.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.base.Supplier;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.utilities.MathUtils;

/**
 * This factory provide to generate some list of rooms
 * @author Federico
 */
public final class BaseRoomsGeneratorFactoryImpl implements BaseRoomsGeneratorFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Room> generateQuadraticRoomList(
			final int wMin, final int wMax,
			final int dMin, final int dMax,
			final int nBaseRoomsMin, final int nBaseRoomsMax){
		
		Random rnd = new Random();
		rnd.setSeed(JSONDataAccessLayer.SEED);
		return this.generateGenericList(nBaseRoomsMin, nBaseRoomsMax, () -> new QuadraticRoom(
					MathUtils.randomBetween(rnd, wMin, wMax), 
					MathUtils.randomBetween(rnd, dMin, dMax)
				));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Room> generateRectungolarRoomList(
			final int wMin, final int wMax,
			final int hMin, final int hMax,
			final int dMin, final int dMax,
			final int nBaseRoomsMin, final int nBaseRoomsMax){
		
		Random rnd = new Random();
		rnd.setSeed(JSONDataAccessLayer.SEED);
		return this.generateGenericList(nBaseRoomsMin, nBaseRoomsMax, () -> new RectangularRoom(
					MathUtils.randomBetween(rnd, wMin, wMax), 
					MathUtils.randomBetween(rnd, hMin, hMax),
					MathUtils.randomBetween(rnd, dMin, dMax)
				));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Room> generateOctagonalRoomList(
			final int edgeMin, final int edgeMax,
			final int dMin, final int dMax,
			final int nBaseRoomsMin, final int nBaseRoomsMax){
		
		Random rnd = new Random();
		rnd.setSeed(JSONDataAccessLayer.SEED);
		return this.generateGenericList(nBaseRoomsMin, nBaseRoomsMax, () -> new OctagonalRoom(
					MathUtils.randomBetween(rnd, edgeMin, edgeMax),
					MathUtils.randomBetween(rnd, dMin, dMax)
				));
	}
	
	private <X> List<X> generateGenericList(final int nBaseRoomsMin, final int nBaseRoomsMax, final Supplier<X> add){
		
		Random rnd = new Random();
		rnd.setSeed(JSONDataAccessLayer.SEED);
		final int nBaseRooms = MathUtils.randomBetween(rnd, nBaseRoomsMin, nBaseRoomsMax);
		final List<X> baseRooms = new ArrayList<>();
		for (int i = 0; i < nBaseRooms; i++) {
			baseRooms.add(add.get());
		}
		
		return baseRooms;
	}
}
