package hotlinecesena.controller.HUD;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import hotlinecesena.controller.MissionController;
import hotlinecesena.controller.Updatable;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.view.WorldView;
import hotlinecesena.view.loader.ProxyImage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.util.Pair;

public class PlayerStatsController implements Initializable, Updatable{
	
	@FXML
	private ProgressBar lifeBar;
	@FXML
	private BorderPane borderPane;
	@FXML
	private Label bulletLabel;
	@FXML
	private ImageView weaponImage;
	@FXML
	private CheckBox missionCheckBox;
	
	private ProxyImage proxyImage = new ProxyImage();
	private List<Pair<String, Boolean>> missions;
	private Player player = JSONDataAccessLayer.getInstance().getPlayer().getPly();
	private MissionController missionController;
	
	private WorldView worldView;
	private int currentMission = 0;

	public PlayerStatsController(WorldView view, MissionController missionController) {
		this.worldView = view;
		this.missionController = missionController;
		missions = missionController.getMissions();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		borderPane.prefWidthProperty().bind(worldView.getBorderPane().widthProperty());
		lifeBar.setProgress(player.getMaxHealth());
		missionCheckBox.setText(missions.get(currentMission).getKey());
		System.out.println(JSONDataAccessLayer.getInstance().getEnemy().getEnemies().size());
	}

	@Override
	public Consumer<Double> getUpdateMethod() {
		return deltaTime -> {
			missions = missionController.getMissions();
			// Update of life bar
			lifeBar.setProgress(player.getCurrentHealth());
			if (lifeBar.getProgress() <= 10) {
				lifeBar.setStyle("-fx-accent: red;");
			}
			
			// Update of ammo counter
			 player.getInventory().getWeapon().ifPresentOrElse(weapon -> bulletLabel.setText(
	                    weapon.getCurrentAmmo()
	                    + "/"
	                    + player.getInventory().getQuantityOf(weapon.getCompatibleAmmunition())
	                    ), () -> bulletLabel.setText("0/0"));
			
			// Update of missions view
			missionCheckBox.setSelected(missions.get(currentMission).getValue());
			
			// ************
			worldView.getBorderPane().setOnKeyPressed(e -> {
			    if (e.getCode() == KeyCode.M) {
			    	currentMission++;
			    	if (currentMission == missions.size()) {
						currentMission = 0;
					}
			    	missionCheckBox.setText(missions.get(currentMission).getKey());
			    }
			    if (e.getCode() == KeyCode.N) {
			    	currentMission--;
			    	if (currentMission == -1) {
						currentMission = missions.size() - 1;
					}
			    	missionCheckBox.setText(missions.get(currentMission).getKey());
			    }
			});
		};
	}

}
