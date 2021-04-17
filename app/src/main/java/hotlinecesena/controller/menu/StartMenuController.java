package hotlinecesena.controller.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import hotlinecesena.controller.AudioControllerImpl;
import hotlinecesena.controller.WorldController;
import hotlinecesena.utilities.SceneSwapper;

public class StartMenuController implements Initializable{
	
	@FXML
	private Button newGameButton;
	@FXML
	private Button optionsButton;
	@FXML
	private Button exitButton;
	@FXML
	private VBox vBox;
	
	private SceneSwapper sceneSwapper = new SceneSwapper();
	private Stage stage;
	private AudioControllerImpl audioControllerImpl;
	
	public StartMenuController(Stage stage, AudioControllerImpl audioControllerImpl) {
		this.stage = stage;
		this.audioControllerImpl = audioControllerImpl;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		vBox.getChildren().forEach(c -> VBox.setVgrow(c, Priority.ALWAYS));
		vBox.prefWidthProperty().bind(stage.widthProperty().multiply(3).divide(5));
	}
	
	public void newGameClick(final ActionEvent event) throws IOException {
        new WorldController(stage, audioControllerImpl);
	}
	
	public void optionsClick(final ActionEvent event) throws IOException {
		sceneSwapper.swapScene(new OptionsController(
				stage,
				Optional.empty(),
				audioControllerImpl,
				Optional.empty()),
				"OptionsView.fxml",
				stage);
	}
	
	public void exitClick(final ActionEvent event) throws IOException {
		System.exit(0);
	}
}
