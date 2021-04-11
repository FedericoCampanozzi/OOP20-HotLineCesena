package hotlinecesena.controller.HUD;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.Set;

import hotlinecesena.controller.GameController;
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
	private MissionController missionController = new GameController().getMissionController();
	private Set<Pair<String, Boolean>> missions = missionController.getMissions();
	private Player player = JSONDataAccessLayer.getInstance().getPlayer().getPly();
	private WorldView worldView;
	private int nReloading = 0;

	public PlayerStatsController(WorldView view) {
		this.worldView = view;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		borderPane.prefWidthProperty().bind(worldView.getBorderPane().widthProperty());
		lifeBar.setProgress(player.getMaxHealth());
		
	}

	@Override
	public Consumer<Double> getUpdateMethod() {
		return deltaTime -> {
			lifeBar.setProgress(player.getCurrentHealth());
			if (lifeBar.getProgress() <= 10) {
				lifeBar.setStyle("-fx-accent: red;");
			}
			
			nReloading = player.getInventory().getWeapon().get().getNReloading();
			bulletLabel.setText(
					player.getInventory().getWeapon().get().getCurrentAmmo()
					+ "/"
					+ (player.getInventory().getWeapon().get().getCompatibleAmmunition().getMaxStacks()
							- (player.getInventory().getWeapon().get().getMagazineSize()) * nReloading));
		};
	}

}
