package hotlinecesena.model.dataccesslayer;

/**
 * this is the simplest data type possible
 * the default class that allow a class override
 * only the method need
 * @author Federico
 *
 */
public class AbstractData implements Data {

	/**
	 * Default reader
	 */
	@Override
	public void read() throws Exception {
	}
	
	/**
	 * Default writer
	 */
	@Override
	public void write() throws Exception {
		
	}

}
