package hotlinecesena.controller.menu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

public interface StartMenuController extends Initializable{

	/**
	 * Set up the scene layout.
	 */
	void initialize(URL location, ResourceBundle resources);

	/**
	 * When the {@code newGame} button is pressed, initialize the World.
	 * @param event
	 * @throws IOException
	 */
	void newGameClick(ActionEvent event) throws IOException;

	/**
	 * When the {@code options} button is pressed, initialize the {@code OptionsMenu}.
	 * @param event
	 * @throws IOException
	 */
	void optionsClick(ActionEvent event) throws IOException;

	/**
	 * When the {@code exit} button is pressed, close the app.
	 * @param event
	 * @throws IOException
	 */
	void exitClick(ActionEvent event) throws IOException;

}