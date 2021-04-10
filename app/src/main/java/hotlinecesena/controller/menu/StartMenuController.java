package hotlinecesena.controller.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import hotlinecesena.controller.AudioController;
import hotlinecesena.controller.WorldController;
import hotlinecesena.utilities.SceneSwapper;

public class StartMenuController implements Initializable{
	
	@FXML
	private Button newGameButton;
	@FXML
	private Button optionsButton;
	@FXML
	private Button exitButton;
	
	private SceneSwapper sceneSwapper = new SceneSwapper();
	private AudioController audioController = new AudioController();
	private Stage stage;
	
	public StartMenuController(Stage stage) {
		this.stage = stage;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		audioController.playMusic();
	}
	
	public void newGameClick(final ActionEvent event) throws IOException {
        new WorldController(stage);
	}
	
	public void optionsClick(final ActionEvent event) throws IOException {
		sceneSwapper.swapScene(new OptionsController(stage, Optional.empty()), "OptionsView.fxml", stage);
	}
	
	public void exitClick(final ActionEvent event) throws IOException {
		System.exit(0);
	}
}
