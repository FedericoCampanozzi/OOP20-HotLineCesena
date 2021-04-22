package hotlinecesena.controller.hud;

import java.util.function.Consumer;

import hotlinecesena.controller.mission.MissionController;
import hotlinecesena.view.hud.PlayerStatsView;
import hotlinecesena.view.hud.PlayerStatsViewImpl;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

/**
 * Controller of the {@code PlayerStatsView}.
 */
public class PlayerStatsControllerImpl implements PlayerStatsController{
	
	private final StackPane stackPane;
	private final PlayerStatsView playerStatsView;

	/**
	 * Class constructor.
	 * @param worldView
	 * @param missionController
	 */
	public PlayerStatsControllerImpl(StackPane stackPane, MissionController missionController) {
		this.stackPane = stackPane;
		this.playerStatsView = new PlayerStatsViewImpl(stackPane, missionController);
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
		stackPane.setOnKeyPressed(e -> {
		    if (e.getCode() == KeyCode.M) {
		    	playerStatsView.showNextMission(true);
		    }
		    if (e.getCode() == KeyCode.N) {
		    	playerStatsView.showNextMission(true);
		    }
		});
	}
}
