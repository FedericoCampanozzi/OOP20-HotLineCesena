package hotlinecesena.controller.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
	private VBox vBox;
	
	private final SceneSwapper sceneSwapper = new SceneSwapper();
	private final AudioControllerImpl audioControllerImpl;
	private final Stage primaryStage;
	
	public StartMenuController(final Stage primaryStage, final AudioControllerImpl audioControllerImpl) {
		this.primaryStage = primaryStage;
		this.audioControllerImpl = audioControllerImpl;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		vBox.getChildren().forEach(c -> VBox.setVgrow(c, Priority.ALWAYS));
	}
	
	@FXML
	public void newGameClick(final ActionEvent event) throws IOException {
		Stage stage = new Stage();
		stage.show();
        new WorldController(stage, audioControllerImpl);
        primaryStage.close();
	}
	
	@FXML
	public void optionsClick(final ActionEvent event) throws IOException {
		sceneSwapper.swapScene(
				new OptionsController(primaryStage, audioControllerImpl, Optional.empty()),
				"OptionsView.fxml",
				primaryStage);
	}
	
	@FXML
	public void exitClick(final ActionEvent event) throws IOException {
		System.exit(0);
	}
}
