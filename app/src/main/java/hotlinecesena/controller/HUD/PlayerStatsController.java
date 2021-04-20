package hotlinecesena.controller.HUD;

import java.util.function.Consumer;

import hotlinecesena.controller.Updatable;
import hotlinecesena.controller.mission.MissionController;
import hotlinecesena.view.WorldView;
import hotlinecesena.view.HUD.PlayerStatsView;
import javafx.scene.input.KeyCode;

public class PlayerStatsController implements Updatable{
	
	private final WorldView worldView;
	private final PlayerStatsView playerStatsView;

	public PlayerStatsController(WorldView worldView, MissionController missionController) {
		this.worldView = worldView;
		this.playerStatsView = new PlayerStatsView(worldView, missionController);
	}

	@Override
	public Consumer<Double> getUpdateMethod() {
		return deltaTime -> {
			playerStatsView.updateLifeStats();
			playerStatsView.updateGunStats();
			updateMissionsView();
			playerStatsView.updateMiniMapView();
		};
	}
	
	public PlayerStatsView getPlayerStatsView() {
		return playerStatsView;
	}
	
	private void updateMissionsView() {
		worldView.getStackPane().setOnKeyPressed(e -> {
		    if (e.getCode() == KeyCode.M) {
		    	playerStatsView.showNextMission(true);
		    }
		    if (e.getCode() == KeyCode.N) {
		    	playerStatsView.showNextMission(true);
		    }
		});
	}
}
