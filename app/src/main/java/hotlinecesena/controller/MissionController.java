package hotlinecesena.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import javafx.util.Pair;
import java.util.Map.Entry;
import static java.util.stream.Collectors.*;

public class MissionController {
	
	private final Map<String, Pair<Supplier<Boolean>,Boolean>> quests = new HashMap<>();
	
	public void update(double d) {
        for(Entry<String, Pair<Supplier<Boolean>,Boolean>> m : this.quests.entrySet()){
        	if(m.getValue().getKey().get() && !m.getValue().getValue()) {
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
	
	public List<Pair<String,Boolean>> getMissions(){
		return quests.entrySet().stream()
				.map(itm -> new Pair<>(itm.getKey(), itm.getValue().getValue()))
				.collect(toList());
	}
	
	public List<String> missionPending(){
		return quests.entrySet().stream()
				.filter((itm) -> !itm.getValue().getValue())
				.map((itm) -> itm.getKey())
				.collect(toList());
	}
	
	public List<String> missionComplete(){
		return quests.entrySet().stream()
				.filter((itm) -> itm.getValue().getValue())
				.map((itm) -> itm.getKey())
				.collect(toList());
	}
}
