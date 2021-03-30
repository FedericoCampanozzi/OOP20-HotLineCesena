package hotlinecesena.controller.mission;

import java.util.Map.Entry;

import hotlinecesena.controller.GameLoopController;
import javafx.util.Pair;

public class MissionController {
	
	private final MissionBuilder mission;
    private final GameLoopController glc = new GameLoopController();

	public MissionController() {
		mission = new MissionBuilder()
				.build();
		
		glc.addMethodToUpdate((d) -> this.update(d));
	}
	
	public void update(double d) {
		for(Entry<String, Pair<CheckMethod,Boolean>> m : mission.getQuest().entrySet()){
			if(m.getValue().getKey().check() && !m.getValue().getValue()) {
				System.out.println("Mission Complete : " + m.getKey());
				mission.completeQuest(m.getKey());
			}
		}
	}
}
