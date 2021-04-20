package hotlinecesena.controller.menu;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import hotlinecesena.controller.AudioControllerImpl;
import hotlinecesena.controller.GameLoopController;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.utilities.SceneSwapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PauseController implements Initializable{
	@FXML
	Button resumeButton;
	@FXML
	Button optionsButton;
	@FXML
	Button quitButton;
	
	private final SceneSwapper sceneSwapper = new SceneSwapper();
	private final AudioControllerImpl audioControllerImpl;
	private final GameLoopController gameLoopController;
	private final Stage worldStage;
	private final Stage pauseStage;
	
	
	public PauseController(Stage pauseStage, Stage worldStage, AudioControllerImpl audioControllerImpl, GameLoopController gameLoopController) {
		this.pauseStage = pauseStage;
		this.worldStage = worldStage;
		this.audioControllerImpl = audioControllerImpl;
		this.gameLoopController = gameLoopController;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pauseStage.setOnCloseRequest(e -> resumeButton.fire());
	}
	
	@FXML
	public void resumeClick(final ActionEvent event) throws IOException {
		if (JSONDataAccessLayer.getInstance().getSettings().getFullScreen()) {
			worldStage.setFullScreen(true);
		}
		else {
			worldStage.setFullScreen(false);
			worldStage.setWidth(JSONDataAccessLayer.getInstance().getSettings().getMonitorX());
			worldStage.setHeight(JSONDataAccessLayer.getInstance().getSettings().getMonitorY());
			worldStage.centerOnScreen();
		}
		audioControllerImpl.playMusic();
		gameLoopController.restart();
		pauseStage.close();
	}
	
	@FXML
	public void optionsClick(final ActionEvent event) throws IOException {
		sceneSwapper.swapScene(
				new OptionsController(pauseStage, audioControllerImpl, Optional.of(this)),
				"OptionsView.fxml",
				pauseStage);
	}
	
	@FXML
	public void quitClick(final ActionEvent event) throws IOException {
		worldStage.close();
		JSONDataAccessLayer.newInstance();
		sceneSwapper.swapScene(
				new StartMenuController(pauseStage, audioControllerImpl),
				"StartMenuView.fxml",
				pauseStage);
	}
	
	public Stage getWorldStage() {
		return worldStage;
	}
}
