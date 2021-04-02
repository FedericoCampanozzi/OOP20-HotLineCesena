package hotlinecesena.controller;

import static java.util.stream.Collectors.toSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.Map.Entry;

import javafx.util.Pair;

public class MissionController {
	
	private final GameLoopController glc = new GameLoopController();
	Map<String, Pair<Supplier<Boolean>,Boolean>> quests = new HashMap<>();
	
	public MissionController() {
		glc.addMethodToUpdate((d) -> this.update(d));
	}
	
	public void update(double d) {
		for(Entry<String, Pair<Supplier<Boolean>,Boolean>> m : this.quests.entrySet()){
			if(m.getValue().getKey().get() && !m.getValue().getValue()) {
				System.out.println("Mission Complete : " + m.getKey());
				this.completeQuest(m.getKey());
			}
		}
	}
	
	public void addQuest(String guiName, Supplier<Boolean> complete) {
		this.quests.put(guiName, new Pair<>(complete, false));
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
