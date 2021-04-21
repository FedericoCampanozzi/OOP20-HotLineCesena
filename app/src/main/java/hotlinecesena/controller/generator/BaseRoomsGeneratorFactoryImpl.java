package hotlinecesena.controller.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.utilities.Utilities;

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
		final int nBaseRooms = Utilities.randomBetween(rnd, nBaseRoomsMin, nBaseRoomsMax);
		final List<Room> baseRooms = new ArrayList<>();
		for (int i = 0; i < nBaseRooms; i++) {
			baseRooms.add(new QuadraticRoom(
					Utilities.randomBetween(rnd, wMin, wMax), 
					Utilities.randomBetween(rnd, dMin, dMax)
			));
		}
		
		return baseRooms;
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
		final int nBaseRooms = Utilities.randomBetween(rnd, nBaseRoomsMin, nBaseRoomsMax);
		final List<Room> baseRooms = new ArrayList<>();
		for (int i = 0; i < nBaseRooms; i++) {
			baseRooms.add(new RectangularRoom(
					Utilities.randomBetween(rnd, wMin, wMax), 
					Utilities.randomBetween(rnd, hMin, hMax),
					Utilities.randomBetween(rnd, dMin, dMax)
			));
		}
		
		return baseRooms;
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
		final int nBaseRooms = Utilities.randomBetween(rnd, nBaseRoomsMin, nBaseRoomsMax);
		final List<Room> baseRooms = new ArrayList<>();
		for (int i = 0; i < nBaseRooms; i++) {
			baseRooms.add(new OctagonalRoom(
					Utilities.randomBetween(rnd, edgeMin, edgeMax),
					Utilities.randomBetween(rnd, dMin, dMax)
			));
		}
		
		return baseRooms;
	}
}
