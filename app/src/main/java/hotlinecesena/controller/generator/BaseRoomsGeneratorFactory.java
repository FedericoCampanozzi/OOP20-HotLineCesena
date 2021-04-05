package hotlinecesena.controller.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;

public final class BaseRoomsGeneratorFactory {

	public List<Room> generateQuadraticRoomList(
			final int wMin, final int wMax,
			final int dMin, final int dMax,
			final int nBaseRoomsMin, final int nBaseRoomsMax){
		Random rnd = new Random();
		rnd.setSeed(JSONDataAccessLayer.SEED);
		final int nBaseRooms = rnd.nextInt(nBaseRoomsMax) + nBaseRoomsMin;
		final List<Room> baseRooms = new ArrayList<>();
		for (int i = 0; i < nBaseRooms; i++) {
			baseRooms.add(new QuadraticRoom(rnd.nextInt(wMax) + wMin, rnd.nextInt(dMax) + dMin));
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
		final int nBaseRooms = rnd.nextInt(nBaseRoomsMax) + nBaseRoomsMin;
		final List<Room> baseRooms = new ArrayList<>();
		for (int i = 0; i < nBaseRooms; i++) {
			baseRooms.add(new RectangolarRoom(rnd.nextInt(wMax) + wMin, rnd.nextInt(hMax) + hMin, rnd.nextInt(dMax) + dMin));
		}
		
		return baseRooms;
	}
}
