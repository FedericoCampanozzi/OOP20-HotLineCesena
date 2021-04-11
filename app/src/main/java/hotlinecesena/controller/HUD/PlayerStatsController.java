package hotlinecesena.controller.HUD;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import hotlinecesena.controller.Updatable;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.entities.items.WeaponType;
import hotlinecesena.view.WorldView;
import hotlinecesena.view.loader.ImageType;
import hotlinecesena.view.loader.ProxyImage;
import hotlinecesena.view.loader.SceneType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class PlayerStatsController implements Initializable, Updatable{
	
	@FXML
	private ProgressBar lifeBar;
	@FXML
	private BorderPane borderPane;
	@FXML
	private Label bulletLabel;
	@FXML
	private ImageView weaponImage;
	
	private WorldView worldView;
	ProxyImage proxyImage = new ProxyImage();
	private Player player = JSONDataAccessLayer.getInstance().getPlayer().getPly();
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
