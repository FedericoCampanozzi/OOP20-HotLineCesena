package hotlinecesena.controller.mission;

import java.util.List;
import java.util.function.Supplier;
import javafx.util.Pair;
import hotlinecesena.controller.Updatable;

/**
 * This interface need to be update so extends {@code Updatable}, moreover define a
 * controller for all missions in game
 * @author Federico
 *
 */
public interface MissionController extends Updatable {
	
	/**
	 * Add a new quest of current set of missions
	 * @param guiName the string that how missions appears on monitor
	 * @param complete a lambda {@code Supplier} that determinate how mission can be ended 
	 */
	void addQuest(String guiName, Supplier<Boolean> complete);

	/**
	 * Notify that a mission is terminated
	 * @param The name of mission
	 */
	void completeQuest(String name);

	/**
	 * @return The list of mission and if it is completed or not
	 */
	List<Pair<String, Boolean>> getMissions();

	/**
	 * @return The list of names of remaining mission
	 */
	List<String> missionPending();

	/**
	 * @return The list of names of completed mission
	 */
	List<String> missionComplete();

}