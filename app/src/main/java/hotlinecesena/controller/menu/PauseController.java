package hotlinecesena.controller.menu;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import hotlinecesena.controller.AudioControllerImpl;
import hotlinecesena.controller.GameLoopController;
import hotlinecesena.controller.SceneSwapper;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Pause menu scene, controls {@code PauseView.fxml}.
 */
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
	
	/**
	 * Class constructor.
	 * @param pauseStage
	 * 				The stage containing the pause menu scene.
	 * @param worldStage
	 * 				The stage containing the world scene.
	 * @param audioControllerImpl
	 * 				The audio controller of the entire application.
	 * @param gameLoopController
	 * 				The loop controller of the game.
	 */
	public PauseController(Stage pauseStage, Stage worldStage, AudioControllerImpl audioControllerImpl, GameLoopController gameLoopController) {
		this.pauseStage = pauseStage;
		this.worldStage = worldStage;
		this.audioControllerImpl = audioControllerImpl;
		this.gameLoopController = gameLoopController;
	}
	
	/**
	 * Set up the setOnCloeRequest. If user closes the windows, resume the game.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pauseStage.setOnCloseRequest(e -> resumeButton.fire());
	}
	
	/**
	 * When the {@code resume} button is pressed, resume the game.
	 * @param event
	 * @throws IOException
	 */
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
	
	/**
	 * When the {@code options} button is pressed, initialize the {@code OptionsMenu}.
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void optionsClick(final ActionEvent event) throws IOException {
		sceneSwapper.swapScene(
				new OptionsController(pauseStage, audioControllerImpl, Optional.of(this)),
				"OptionsView.fxml",
				pauseStage);
	}
	
	/**
	 * When the {@code quit} button is pressed, end game and go back to {@code StartMenu}.
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void quitClick(final ActionEvent event) throws IOException {
		worldStage.close();
		JSONDataAccessLayer.newInstance();
		sceneSwapper.swapScene(
				new StartMenuController(pauseStage, audioControllerImpl),
				"StartMenuView.fxml",
				pauseStage);
	}
	
	/**
	 * 
	 * @return the stage containing the world scene.
	 */
	public Stage getWorldStage() {
		return worldStage;
	}
}
