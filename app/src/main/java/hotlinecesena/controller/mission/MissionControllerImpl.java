package hotlinecesena.controller.mission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.util.Pair;
import java.util.Map.Entry;
import static java.util.stream.Collectors.*;

/**
 * A controller for mission {@code MissionController}
 * @author Federico
 */
public class MissionControllerImpl implements MissionController {
	
	private final Map<String, Pair<Supplier<Boolean>,Boolean>> quests = new HashMap<>();
	
	@Override
    public Consumer<Double> getUpdateMethod() {
        return delta -> {
            for(Entry<String, Pair<Supplier<Boolean>,Boolean>> m : this.quests.entrySet()){
                if(m.getValue().getKey().get() && !m.getValue().getValue()) {
                	this.completeQuest(m.getKey());
                }
            }
        };
    }
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addQuest(String guiName, Supplier<Boolean> complete) {
		this.quests.put(guiName, new Pair<>(complete, false));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void completeQuest(String name) {
		this.quests.put(name, new Pair<>(this.quests.get(name).getKey(),true));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Pair<String,Boolean>> getMissions(){
		return quests.entrySet().stream()
				.map(itm -> new Pair<>(itm.getKey(), itm.getValue().getValue()))
				.collect(toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> missionPending(){
		return quests.entrySet().stream()
				.filter((itm) -> !itm.getValue().getValue())
				.map((itm) -> itm.getKey())
				.collect(toList());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> missionComplete(){
		return quests.entrySet().stream()
				.filter((itm) -> itm.getValue().getValue())
				.map((itm) -> itm.getKey())
				.collect(toList());
	}
}
