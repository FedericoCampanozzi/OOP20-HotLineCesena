package hotlinecesena.controller.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import hotlinecesena.controller.AudioControllerImpl;
import hotlinecesena.controller.SceneSwapper;
import hotlinecesena.controller.SceneSwapperImpl;
import hotlinecesena.controller.WorldControllerImpl;

/**
 * Start menu scene, controls {@code StartMenuView.fxml}.
 */
public class StartMenuControllerImpl implements StartMenuController{
	
	@FXML
	private VBox vBox;
	
	private final SceneSwapper sceneSwapper = new SceneSwapperImpl();
	private final AudioControllerImpl audioControllerImpl;
	private final Stage primaryStage;
	
	/**
	 * Class constructor.
	 * @param primaryStage
	 * 				The stage where the scene is contained.
	 * @param audioControllerImpl
	 * 				The audio controller of the entire application.
	 */
	public StartMenuControllerImpl(final Stage primaryStage, final AudioControllerImpl audioControllerImpl) {
		this.primaryStage = primaryStage;
		this.audioControllerImpl = audioControllerImpl;
	}
	
	/**
	 * Set up the scene layout.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		vBox.getChildren().forEach(c -> VBox.setVgrow(c, Priority.ALWAYS));
	}
	
	/**
	 * When the {@code newGame} button is pressed, initialize the World.
	 * @param event
	 * @throws IOException
	 */
	@Override
	@FXML
	public void newGameClick(final ActionEvent event) throws IOException {
		Stage stage = new Stage();
		stage.show();
        new WorldControllerImpl(stage, audioControllerImpl);
        primaryStage.close();
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
				new OptionsControllerImpl(primaryStage, audioControllerImpl, Optional.empty()),
				"OptionsView.fxml",
				primaryStage);
	}
	
	/**
	 * When the {@code exit} button is pressed, close the app.
	 * @param event
	 * @throws IOException
	 */
	@Override
	@FXML
	public void exitClick(final ActionEvent event) throws IOException {
		System.exit(0);
	}
}
