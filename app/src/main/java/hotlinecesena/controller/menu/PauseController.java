package hotlinecesena.controller.menu;

import java.io.IOException;
import hotlinecesena.controller.Updatable;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * Pause menu scene, controls {@code PauseView.fxml}.
 */
public interface PauseController extends Updatable, Initializable{

	/**
	 * When the {@code resume} button is pressed, resume the game.
	 * @param event
	 * @throws IOException
	 */
	void resumeClick(ActionEvent event) throws IOException;

	/**
	 * When the {@code options} button is pressed, initialize the {@code OptionsMenu}.
	 * @param event
	 * @throws IOException
	 */
	void optionsClick(ActionEvent event) throws IOException;

	/**
	 * When the {@code quit} button is pressed, end game and go back to {@code StartMenu}.
	 * @param event
	 * @throws IOException
	 */
	void quitClick(ActionEvent event) throws IOException;

	/**
	 * 
	 * @return the stage containing the world scene.
	 */
	Stage getWorldStage();

}