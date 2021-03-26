package hotlinecesena.controller.menu;

import java.io.IOException;

import hotlinecesena.controller.GameController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class OptionsController {
	
	public OptionsController() {
		
	}
	
	@FXML
	private Button backButton;
	
	public void backClick() throws IOException {
		System.out.println("'Back' button pressed");

		GameController gameScene = new GameController();
		gameScene.changeScene("StartMenuView.fxml");
	}

}