package hotlinecesena.controller.mission;

import java.util.*;
import javafx.util.Pair;
import static java.util.stream.Collectors.*;

public class MissionBuilder {

	Map<String, Pair<CheckMethod,Boolean>> quests = new HashMap<>();
	
	public MissionBuilder() {
		
	}
	
	public MissionBuilder addQuest(String guiName, CheckMethod complete) {
		quests.put(guiName, new Pair<>(complete, false));
		return this;
	}
	
	public MissionBuilder build() {
		return this;
	}
	
	public Map<String, Pair<CheckMethod,Boolean>> getQuest(){
		return this.quests;
	}
	
	public void completeQuest(String name) {
		this.quests.put(name, new Pair<>(this.quests.get(name).getKey(),true));
	}
	
	public Set<String> missionPending(){
		return quests.entrySet().stream()
				.filter((itm) -> !itm.getValue().getValue())
				.map((itm) -> itm.getKey())
				.collect(toSet());
	}
	
	public Set<String> missionComplete(){
		return quests.entrySet().stream()
				.filter((itm) -> itm.getValue().getValue())
				.map((itm) -> itm.getKey())
				.collect(toSet());
	}
}
