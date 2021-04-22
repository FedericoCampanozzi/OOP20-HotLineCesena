package hotlinecesena.controller;

/**
 * This class update each frame others class that extends or implement {@code Updatable}
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