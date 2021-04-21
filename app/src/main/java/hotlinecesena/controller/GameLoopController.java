package hotlinecesena.controller;

/**
 * This class class each frame a method defined by {@code Updatable}
 * @author Federico
 */
public interface GameLoopController {

	/**
	 * The Main Loop Method
	 */
	void loop();

	/**
	 * Add a class that need to be called each frame
	 * @param  The class that extend {@code Updatable}
	 */
	void addMethodToUpdate(Updatable m);

	/**
	 * Stop the loop
	 */
	void stop();

	/**
	 * Restart the loop
	 */
	void restart();

}