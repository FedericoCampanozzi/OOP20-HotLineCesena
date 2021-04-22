package hotlinecesena.controller.menu;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import hotlinecesena.controller.AudioControllerImpl;
import hotlinecesena.controller.GameLoopController;
import hotlinecesena.controller.SceneSwapper;
import hotlinecesena.controller.SceneSwapperImpl;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.view.input.InputListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * Pause menu scene, controls {@code PauseView.fxml}.
 */
public class PauseControllerImpl implements PauseController{
	
	@FXML
	Button resumeButton;
	@FXML
	Button optionsButton;
	@FXML
	Button quitButton;
	
	private final SceneSwapper sceneSwapper = new SceneSwapperImpl();
	private final AudioControllerImpl audioControllerImpl;
	private final GameLoopController gameLoopController;
	private final Stage worldStage;
	private final Stage pauseStage;
	private final InputListener listener;
	
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
	 * @param listener 
	 */
	public PauseControllerImpl(
			Stage pauseStage,
			Stage worldStage,
			AudioControllerImpl audioControllerImpl,
			GameLoopController gameLoopController,
			InputListener listener) {
		this.pauseStage = pauseStage;
		this.worldStage = worldStage;
		this.audioControllerImpl = audioControllerImpl;
		this.gameLoopController = gameLoopController;
		this.listener = listener;
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
	@Override
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
	@Override
	@FXML
	public void optionsClick(final ActionEvent event) throws IOException {
		sceneSwapper.swapScene(
				new OptionsControllerImpl(pauseStage, audioControllerImpl, Optional.of(this)),
				"OptionsView.fxml",
				pauseStage);
	}
	
	/**
	 * When the {@code quit} button is pressed, end game and go back to {@code StartMenu}.
	 * @param event
	 * @throws IOException
	 */
	@Override
	@FXML
	public void quitClick(final ActionEvent event) throws IOException {
		worldStage.close();
		JSONDataAccessLayer.newInstance();
		sceneSwapper.swapScene(
				new StartMenuControllerImpl(pauseStage, audioControllerImpl),
				"StartMenuView.fxml",
				pauseStage);
	}
	
	/**
	 * 
	 * @return the stage containing the world scene.
	 */
	@Override
	public Stage getWorldStage() {
		return worldStage;
	}

	/**
	 * Check if user press the pause key during the match.
	 * If yes, show {@code PauseMenu}.
	 */
	@Override
	public Consumer<Double> getUpdateMethod() {
		return deltaTime -> {
			if (listener.deliverInputs().getKey().contains(KeyCode.P)) {
                try {
                    audioControllerImpl.stopMusic();
                    gameLoopController.stop();
                    sceneSwapper.setUpStage(pauseStage);
                    pauseStage.show();
                    sceneSwapper.swapScene(this, "PauseView.fxml", pauseStage);
                } catch (final IOException e1) {
                    e1.printStackTrace();
                }
            }
		};
	}
}
