package hotlinecesena.controller.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.utilities.Utilities;

public final class BaseRoomsGeneratorFactory {

	/**
	 * get a list of quadratic room
	 * @param wMin
	 * @param wMax
	 * @param dMin
	 * @param dMax
	 * @param nBaseRoomsMin
	 * @param nBaseRoomsMax
	 * @return
	 */
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
	 * get a list of rectangular room
	 * @param wMin
	 * @param wMax
	 * @param hMin
	 * @param hMax
	 * @param dMin
	 * @param dMax
	 * @param nBaseRoomsMin
	 * @param nBaseRoomsMax
	 * @return
	 */
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
	 * get a list of octagonal room
	 * @param edgeMin
	 * @param edgeMax
	 * @param dMin
	 * @param dMax
	 * @param nBaseRoomsMin
	 * @param nBaseRoomsMax
	 * @return
	 */
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
