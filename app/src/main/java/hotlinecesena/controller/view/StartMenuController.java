package hotlinecesena.controller.view;

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
	
	public void newGameClick() throws IOException {
		System.out.println("'New game' button pressed");
	}
	
	public void optionsClick() throws IOException {
		System.out.println("'Options' button pressed");
		
		GameController gameScene = new GameController();
		gameScene.changeScene("OptionsView.fxml");
	}
	
	public void exitClick() throws IOException {
		System.out.println("'Exit' button pressed");
		System.exit(0);
	}

}
