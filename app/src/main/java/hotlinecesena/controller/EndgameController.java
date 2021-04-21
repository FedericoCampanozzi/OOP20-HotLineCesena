package hotlinecesena.controller;

import java.util.function.Consumer;

public interface EndgameController extends Updatable {

	/**
	 * If user complete all the missions, it's a victory.
	 * Else if the player dies, it's a defeat.
	 */
	Consumer<Double> getUpdateMethod();

}