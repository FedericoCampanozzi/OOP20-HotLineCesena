package hotlinecesena.controller.hud;

import java.util.function.Consumer;

import hotlinecesena.controller.mission.MissionController;
import hotlinecesena.view.WorldView;
import hotlinecesena.view.hud.PlayerStatsView;
import hotlinecesena.view.hud.PlayerStatsViewImpl;
import javafx.scene.input.KeyCode;

/**
 * Controller of the {@code PlayerStatsView}.
 */
public class PlayerStatsControllerImpl implements PlayerStatsController{
	
	private final WorldView worldView;
	private final PlayerStatsView playerStatsView;

	/**
	 * Class constructor.
	 * @param worldView
	 * @param missionController
	 */
	public PlayerStatsControllerImpl(WorldView worldView, MissionController missionController) {
		this.worldView = worldView;
		this.playerStatsView = new PlayerStatsViewImpl(worldView, missionController);
	}

	/**
	 * Update all the stats.
	 */
	@Override
	public Consumer<Double> getUpdateMethod() {
		return deltaTime -> {
			playerStatsView.updateLifeStats();
			playerStatsView.updateGunStats();
			updateMissionsView();
			playerStatsView.updateMiniMapView();
		};
	}
	
	/**
	 * @return the {@code PlayerStatsView}.
	 */
	@Override
	public PlayerStatsView getPlayerStatsView() {
		return playerStatsView;
	}
	
	/**
	 * Check whether the user pressed the {@code nextMissionKey} or the {@code previousMissionKey}.
	 */
	private void updateMissionsView() {
		playerStatsView.updateMissionsStatus();
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
