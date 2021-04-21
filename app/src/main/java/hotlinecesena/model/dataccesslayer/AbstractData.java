package hotlinecesena.model.dataccesslayer;

/**
 * This is the simplest data type possible
 * the default class that allow a class override
 * only the method he need
 * @author Federico
 *
 */
public class AbstractData implements Data {

	/**
	 * Default empty reader
	 */
	@Override
	public void read() throws Exception {
	}
	
	/**
	 * Default empty writer
	 */
	@Override
	public void write() throws Exception {
		
	}

}
