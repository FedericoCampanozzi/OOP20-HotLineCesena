package hotlinecesena.controller.mission;

import java.util.List;
import java.util.function.Supplier;

import hotlinecesena.controller.Updatable;
import javafx.util.Pair;

public interface MissionController extends Updatable {
	
	/**
	 * add a new quest of current set of missions
	 * @param guiName
	 * @param complete
	 */
	void addQuest(String guiName, Supplier<Boolean> complete);

	/**
	 * notify that a mission is end
	 * @param name
	 */
	void completeQuest(String name);

	/**
	 * @return the list of mission and if it is completed or not
	 */
	List<Pair<String, Boolean>> getMissions();

	/**
	 * @return the list of names of remaining mission
	 */
	List<String> missionPending();

	/**
	 * @return the list of names of completed mission
	 */
	List<String> missionComplete();

}