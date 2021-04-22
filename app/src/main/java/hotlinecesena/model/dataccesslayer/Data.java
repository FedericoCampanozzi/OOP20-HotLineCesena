package hotlinecesena.model.dataccesslayer;

/**
 * This interface represent a classic data that define
 * how read it and write it
 * @author Federico
 *
 */
public interface Data {

	/**
	 * how read it ?
	 * @throws Exception what kind of exception can erase
	 */
	void read() throws Exception;
	/**
	 * how write it ?
	 * @throws Exception what kind of exception can erase
	 */
	void write() throws Exception;
}
