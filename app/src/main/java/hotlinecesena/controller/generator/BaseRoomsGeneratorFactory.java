package hotlinecesena.controller.generator;

import java.util.List;

/**
 * This factory provide to generate some list of rooms
 * @author Federico
 */
public interface BaseRoomsGeneratorFactory {

	/**
	 * Get a list of quadratic room
	 * @param wMin
	 * @param wMax
	 * @param dMin
	 * @param dMax
	 * @param nBaseRoomsMin
	 * @param nBaseRoomsMax
	 * @return
	 */
	List<Room> generateQuadraticRoomList(int wMin, int wMax, int dMin, int dMax, int nBaseRoomsMin, int nBaseRoomsMax);

	/**
	 * Get a list of rectangular room
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
	List<Room> generateRectungolarRoomList(int wMin, int wMax, int hMin, int hMax, int dMin, int dMax,
			int nBaseRoomsMin, int nBaseRoomsMax);

	/**
	 * Get a list of octagonal room
	 * @param edgeMin
	 * @param edgeMax
	 * @param dMin
	 * @param dMax
	 * @param nBaseRoomsMin
	 * @param nBaseRoomsMax
	 * @return
	 */
	List<Room> generateOctagonalRoomList(int edgeMin, int edgeMax, int dMin, int dMax, int nBaseRoomsMin,
			int nBaseRoomsMax);

}