package hotlinecesena.controller.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.utilities.Utilities;

public final class BaseRoomsGeneratorFactory {

	public List<Room> generateQuadraticRoomList(
			final int wMin, final int wMax,
			final int dMin, final int dMax,
			final int nBaseRoomsMin, final int nBaseRoomsMax){
		Random rnd = new Random();
		rnd.setSeed(JSONDataAccessLayer.SEED);
		final int nBaseRooms = Utilities.RandomBetween(rnd, nBaseRoomsMin, nBaseRoomsMax);
		final List<Room> baseRooms = new ArrayList<>();
		for (int i = 0; i < nBaseRooms; i++) {
			baseRooms.add(new QuadraticRoom(
					Utilities.RandomBetween(rnd, wMin, wMax), 
					Utilities.RandomBetween(rnd, dMin, dMax)
			));
		}
		
		return baseRooms;
	}

	public List<Room> generateRectangolarRoomList(
			final int wMin, final int wMax,
			final int hMin, final int hMax,
			final int dMin, final int dMax,
			final int nBaseRoomsMin, final int nBaseRoomsMax){
		Random rnd = new Random();
		rnd.setSeed(JSONDataAccessLayer.SEED);
		final int nBaseRooms = Utilities.RandomBetween(rnd, nBaseRoomsMin, nBaseRoomsMax);
		final List<Room> baseRooms = new ArrayList<>();
		for (int i = 0; i < nBaseRooms; i++) {
			baseRooms.add(new RectangolarRoom(
					Utilities.RandomBetween(rnd, wMin, wMax), 
					Utilities.RandomBetween(rnd, hMin, hMax),
					Utilities.RandomBetween(rnd, dMin, dMax)
			));
		}
		
		return baseRooms;
	}
	
	public List<Room> generateOctagonalRoomList(
			final int edgeMin, final int edgeMax,
			final int dMin, final int dMax,
			final int nBaseRoomsMin, final int nBaseRoomsMax){
		Random rnd = new Random();
		rnd.setSeed(JSONDataAccessLayer.SEED);
		final int nBaseRooms = Utilities.RandomBetween(rnd, nBaseRoomsMin, nBaseRoomsMax);
		final List<Room> baseRooms = new ArrayList<>();
		for (int i = 0; i < nBaseRooms; i++) {
			baseRooms.add(new OctagonalRoom(
					Utilities.RandomBetween(rnd, edgeMin, edgeMax),
					Utilities.RandomBetween(rnd, dMin, dMax)
			));
		}
		
		return baseRooms;
	}
}
