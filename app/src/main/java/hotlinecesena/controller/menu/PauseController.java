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
	
	SceneSwapper sceneSwapper = new SceneSwapper();
	private Optional<Stage> worldStage;
	private AudioControllerImpl audioControllerImpl;
	private Stage pauseStage;
	private GameLoopController gameLoopController;
	
	public PauseController(Stage pauseStage, Optional<Stage> worldStage, AudioControllerImpl audioControllerImpl, GameLoopController gameLoopController) {
		this.pauseStage = pauseStage;
		this.worldStage = worldStage;
		this.audioControllerImpl = audioControllerImpl;
		this.gameLoopController = gameLoopController;
		this.pauseStage.setOnCloseRequest(e -> resumeButton.fire());
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void resumeClick(final ActionEvent event) throws IOException {
		if (JSONDataAccessLayer.getInstance().getSettings().getFullScreen() == true) {
			worldStage.get().setFullScreen(true);
		}
		else {
			worldStage.get().setWidth(JSONDataAccessLayer.getInstance().getSettings().getMonitorX());
			worldStage.get().setHeight(JSONDataAccessLayer.getInstance().getSettings().getMonitorY());
			worldStage.get().centerOnScreen();
		}
		audioControllerImpl.playMusic();
		gameLoopController.restart();
		pauseStage.close();
	}
	
	public void optionsClick(final ActionEvent event) throws IOException {
		sceneSwapper.swapScene(new OptionsController(pauseStage, worldStage, audioControllerImpl, Optional.of(this)), "OptionsView.fxml", pauseStage);
	}
	
	public void quitClick(final ActionEvent event) throws IOException {
		if (worldStage.isPresent()) {
			worldStage.get().close();
		}
		JSONDataAccessLayer.newInstance();
		sceneSwapper.swapScene(new StartMenuController(pauseStage, audioControllerImpl), "StartMenuView.fxml", pauseStage);
	}
}
