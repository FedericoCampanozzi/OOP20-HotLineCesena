package hotlinecesena.controller.menu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import hotlinecesena.controller.Updatable;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public interface PauseController extends Updatable, Initializable{

	/**
	 * Set up the setOnCloeRequest. If user closes the windows, resume the game.
	 */
	void initialize(URL location, ResourceBundle resources);

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

	/**
	 * Check if user press the pause key during the match.
	 * If yes, show {@code PauseMenu}.
	 */
	Consumer<Double> getUpdateMethod();

}