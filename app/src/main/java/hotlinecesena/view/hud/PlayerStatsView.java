package hotlinecesena.view.hud;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public interface PlayerStatsView extends Initializable{

	/**
	 * Set up the scene layout.
	 */
	void initialize(URL location, ResourceBundle resources);

	/**
	 * Update the {@code lifeBar} and the {@code hpLabel} with the current health of the player.
	 */
	void updateLifeStats();

	/**
	 * Update the {@code weaponImageView} with the current equipped weapon 
	 * and the {@code bulletLabel} with the current amount of bullets in magazine.
	 */
	void updateGunStats();

	/**
	 * Update the {@code miniMapImageView} with the current position of the player in the world.
	 */
	void updateMiniMapView();

	/**
	 * Update the {@code missionCheckBox} when a mission is completed.
	 */
	void updateMissionsStatus();

	/**
	 * Show the next mission if the player requested the next one, else show the previous mission.
	 * @param next
	 */
	void showNextMission(boolean next);

}