package hotlinecesena.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class OptionsController {
	
	public OptionsController() {
		
	}
	
	@FXML
	private Button audioButton;
	@FXML
	private Button graphicButton;
	@FXML
	private Button controlsButton;
	@FXML
	private Button backButton;
	
	public void audioClick() throws IOException {
		System.out.println("'Audio' button pressed");
	}
	
	public void graphicClick() throws IOException {
		System.out.println("'Graphic' button pressed");
	}
	
	public void controlsClick() throws IOException {
		System.out.println("'Controls' button pressed");
	}
	
	public void backClick() throws IOException {
		System.out.println("'Back' button pressed");

		GameController gameScene = new GameController();
		gameScene.changeScene("fxml/StartMenuView.fxml");
	}

}