package hotlinecesena.controller.menu;

import java.io.IOException;
import hotlinecesena.utilities.SceneSwapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

public class PauseController{
	@FXML
	Button resumeButton;
	@FXML
	Button optionsButton;
	@FXML
	Button quitButton;
	
	SceneSwapper sceneSwapper = new SceneSwapper();
	private Stage worldStage;
	private Stage pauseStage;
	
	public void resumeClick(final ActionEvent event) throws IOException {
		pauseStage = (Stage) ((Node) event.getSource()).getScene().getWindow();;
		pauseStage.close();
	}
	
	public void optionsClick(final ActionEvent event) throws IOException {
		sceneSwapper.changeScene("OptionsView.fxml", event);
	}
	
	public void quitClick(final ActionEvent event) throws IOException {
		worldStage = (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
		worldStage.close();
		sceneSwapper.changeScene("StartMenuView.fxml", event);
	}
}
