package hotlinecesena.controller.HUD;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import hotlinecesena.controller.MissionController;
import hotlinecesena.controller.Updatable;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.entities.items.Weapon;
import hotlinecesena.view.WorldView;
import hotlinecesena.view.loader.ImageType;
import hotlinecesena.view.loader.ProxyImage;
import hotlinecesena.view.loader.SceneType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

public class PlayerStatsController implements Initializable, Updatable{
	
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
	
	private ProxyImage proxyImage = new ProxyImage();
	private List<Pair<String, Boolean>> missions;
	private Player player = JSONDataAccessLayer.getInstance().getPlayer().getPly();
	private MissionController missionController;
	
	private WorldView worldView;
	private int currentMission = 0;
	private Weapon currentWeapon;

	public PlayerStatsController(WorldView view, MissionController missionController) {
		this.worldView = view;
		this.missionController = missionController;
		missions = missionController.getMissions();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		borderPane.prefWidthProperty().bind(worldView.getBorderPane().widthProperty());
		missionCheckBox.setText(missions.get(currentMission).getKey());
	}

	@Override
	public Consumer<Double> getUpdateMethod() {
		return deltaTime -> {
			// Update of life bar
			lifeBar.setProgress(player.getCurrentHealth() / player.getMaxHealth());
			hpLabel.setText(((int) player.getCurrentHealth()) + "/" + ((int) player.getMaxHealth()));
			if (lifeBar.getProgress() <= 0.2) {
				lifeBar.setStyle("-fx-accent: red;");
			}
			
			// Update of ammo counter
			player.getInventory().getWeapon().ifPresentOrElse(weapon -> bulletLabel.setText(
	                    weapon.getCurrentAmmo()
	                    + "/"
	                    + player.getInventory().getQuantityOf(weapon.getCompatibleAmmunition())
	                    ), () -> bulletLabel.setText("0/0"));
			currentWeapon = player.getInventory().getWeapon().get();
			switch (currentWeapon.getWeaponType()) {
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
				break;
			}
			
			// Update of missions view
			missions = missionController.getMissions();
			missionCheckBox.setSelected(missions.get(currentMission).getValue());
			
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
