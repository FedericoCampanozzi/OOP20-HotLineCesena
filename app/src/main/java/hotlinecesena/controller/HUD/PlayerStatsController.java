package hotlinecesena.controller.HUD;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import hotlinecesena.controller.Updatable;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.view.WorldView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;

public class PlayerStatsController implements Initializable, Updatable{
	
	@FXML
	private ProgressBar lifeBar;
	@FXML
	private BorderPane borderPane;
	
	private WorldView worldView;
	private Player player = JSONDataAccessLayer.getInstance().getPlayer().getPly();

	public PlayerStatsController(WorldView view) {
		this.worldView = view;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		borderPane.prefWidthProperty().bind(worldView.getBorderPane().widthProperty());
		borderPane.toFront();
		lifeBar.setProgress(player.getMaxHealth());
	}

	@Override
	public Consumer<Double> getUpdateMethod() {
		return deltaTime -> {
			lifeBar.setProgress(player.getCurrentHealth());
		};
	}

}
