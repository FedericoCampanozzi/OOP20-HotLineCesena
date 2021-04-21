package hotlinecesena.controller;

import java.io.IOException;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * Utility class for swapping scenes.
 */
public interface SceneSwapper {

	/**
	 * Create a scene with a fxml file and assign a controller to it a controller.
	 * @param controller
	 * 				The controller of the fxml file.
	 * @param fxml
	 * 				The fxml file name which has to be loaded in the scene.
	 * @param stage
	 * 				The stage containing the new scene.
	 * @throws IOException
	 */
	void swapScene(Initializable controller, String fxml, Stage stage) throws IOException;

	/**
	 * If a new stage has been created, set it up (title, size, icon and position). Use only for menu scenes.
	 * @param stage
	 */
	void setUpStage(Stage stage);

}