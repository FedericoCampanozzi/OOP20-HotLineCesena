package hotlinecesena.controller.menu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

import hotlinecesena.controller.GameController;

public class StartMenuController {
	
	public StartMenuController() {
		
	}
	
	@FXML
	private Button newGameButton;
	@FXML
	private Button optionsButton;
	@FXML
	private Button exitButton;
	
	private static GameController gameScene = new GameController();
	
	public void newGameClick() throws IOException {
		gameScene.changeScene("WorldView.fxml");
	}
	
	public void optionsClick() throws IOException {
		// System.out.println("'Options' button pressed");
		gameScene.changeScene("OptionsView.fxml");
	}
	
	public void exitClick() throws IOException {
		// System.out.println("'Exit' button pressed");
		System.exit(0);
	}

}
