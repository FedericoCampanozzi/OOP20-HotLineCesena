package hotlinecesena.controller.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import hotlinecesena.controller.WorldController;
import hotlinecesena.utilities.SceneSwapper;

public class StartMenuController{
	
	@FXML
	private Button newGameButton;
	@FXML
	private Button optionsButton;
	@FXML
	private Button exitButton;
	
	private static SceneSwapper sceneSwapper = new SceneSwapper();
	
	public void newGameClick(final ActionEvent event) throws IOException {
		// sceneSwapper.changeScene("WorldView.fxml", event);
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
