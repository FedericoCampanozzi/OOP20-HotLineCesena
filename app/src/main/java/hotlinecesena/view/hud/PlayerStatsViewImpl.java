package hotlinecesena.view.hud;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import hotlinecesena.controller.mission.MissionController;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.view.WorldView;
import hotlinecesena.view.loader.ImageType;
import hotlinecesena.view.loader.ProxyImage;
import hotlinecesena.view.loader.SceneType;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import javafx.util.Pair;

/**
 * HUD view containing the player stats, controls the {@code PlayerStatsView.fxml}.
 */
public class PlayerStatsViewImpl implements PlayerStatsView{
	
	@FXML
	private ProgressBar lifeBar;
	@FXML
	private Label hpLabel;
	@FXML
	private BorderPane borderPane;
	@FXML
	private Label bulletLabel;
	@FXML
	private CheckBox missionCheckBox;
	@FXML
	private HBox weaponHBox;
	@FXML
	private ImageView weaponImageView;
	@FXML
	private Polygon previousMission;
	@FXML
	private Polygon nextMission;
	@FXML
	private ImageView miniMapImageView;
	
	private final ProxyImage proxyImage = new ProxyImage();
	private final MissionController missionController;
	private final WorldView worldView;
	
	private List<Pair<String, Boolean>> missions;
	private Player player = JSONDataAccessLayer.getInstance().getPlayer().getPly();
	private FadeTransition fade = new FadeTransition(Duration.millis(200));
	
	private int currentMission = 0;
	
	/**
	 * Class constructor.
	 * @param worldView
	 * @param missionController
	 */
	public PlayerStatsViewImpl(WorldView worldView, MissionController missionController) {
		this.worldView = worldView;
		this.missionController = missionController;
		missions = missionController.getMissions();
	}

	/**
	 * Set up the scene layout.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initPane();
		updateMissionCB();
		initAnimations();
		initMiniMap();
	}
	
	/**
	 * Update the {@code lifeBar} and the {@code hpLabel} with the current health of the player.
	 */
	@Override
	public void updateLifeStats() {
		lifeBar.setProgress(player.getCurrentHealth() / player.getMaxHealth());
		hpLabel.setText(((int) player.getCurrentHealth()) + "/" + ((int) player.getMaxHealth()));
		if (lifeBar.getProgress() <= 0.2) {
			lifeBar.setStyle("-fx-accent: red;");
		}
		else {
			lifeBar.setStyle("-fx-accent: green;");
		}
	}
	
	/**
	 * Update the {@code weaponImageView} with the current equipped weapon 
	 * and the {@code bulletLabel} with the current amount of bullets in magazine.
	 */
	@Override
	public void updateGunStats() {
		player.getInventory().getWeapon().ifPresentOrElse(weapon -> {
            bulletLabel.setText(
                    weapon.getCurrentAmmo()
                    + "/"
                    + player.getInventory().getQuantityOf(weapon.getCompatibleAmmunition())
                    );
            switch (weapon.getWeaponType()) {
            case RIFLE:
                weaponImageView.setImage(proxyImage.getImage(SceneType.MENU, ImageType.RIFLE));
                break;
            case SHOTGUN:
                weaponImageView.setImage(proxyImage.getImage(SceneType.MENU, ImageType.SHOTGUN));
                break;
            case PISTOL:
                weaponImageView.setImage(proxyImage.getImage(SceneType.MENU, ImageType.PISTOL));
                break;
            default:
                weaponImageView.setImage(proxyImage.getImage(SceneType.GAME, ImageType.BLANK));
                break;
            }
        }, () -> bulletLabel.setText("0/0"));
	}
	
	/**
	 * Update the {@code miniMapImageView} with the current position of the player in the world.
	 */
	@Override
	public void updateMiniMapView() {
		miniMapImageView.setImage(JSONDataAccessLayer.getInstance().getWorld().getImageVIewUpdated());
	}
	
	/**
	 * Update the {@code missionCheckBox} when a mission is completed.
	 */
	@Override
	public void updateMissionsStatus() {
		missions = missionController.getMissions();
		missionCheckBox.setSelected(missions.get(currentMission).getValue());
	}
	
	/**
	 * Show the next mission if the player requested the next one, else show the previous mission.
	 * @param next
	 */
	@Override
	public void showNextMission(final boolean next) {
		if (next) {
			currentMission++;
	    	if (currentMission == missions.size()) {
				currentMission = 0;
			}
	    	fade.setNode(nextMission);
		}
		else {
			currentMission--;
	    	if (currentMission == -1) {
				currentMission = missions.size() - 1;
			}
	    	fade.setNode(previousMission);
		}
		updateMissionCB();
    	fade.play();
	}
	
	/**
	 * Set the pane to fit to the entire stage
	 */
	private void initPane() {
		borderPane.prefWidthProperty().bind(worldView.getStackPane().widthProperty());
		borderPane.prefHeightProperty().bind(worldView.getStackPane().heightProperty());
	}

	/**
	 * Update the current mission displayed
	 */
	private void updateMissionCB() {
		missionCheckBox.setText(missions.get(currentMission).getKey());
	}
	
	/**
	 * Initialize the fade animation for the {@code nextMissionKey} and the {@code previousMissionKey}.
	 */
	private void initAnimations() {
		fade.setFromValue(1.0);
		fade.setToValue(0.0);
		fade.setCycleCount(2);
		fade.setAutoReverse(true);
	}
	
	/**
	 * Initialize the {@code miniMapImageView} for a correct view.
	 */
	private void initMiniMap() {
		Rectangle2D croppedPortion = new Rectangle2D(
				0,0,0,0);
		miniMapImageView.setViewport(croppedPortion);
	}
}
