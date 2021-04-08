package hotlinecesena.controller.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
	
	private static SceneSwapper sceneSwapper = new SceneSwapper();
	AudioController audioController = new AudioController(Optional.empty());
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		audioController.playMusic();
	}
	
	public void newGameClick(final ActionEvent event) throws IOException {
		final Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        new WorldController(primaryStage);
	}
	
	public void optionsClick(final ActionEvent event) throws IOException {
		sceneSwapper.changeScene("OptionsView.fxml", event);
	}
	
	public void exitClick(final ActionEvent event) throws IOException {
		System.exit(0);
	}
}
