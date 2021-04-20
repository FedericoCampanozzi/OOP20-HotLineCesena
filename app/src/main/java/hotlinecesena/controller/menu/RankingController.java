package hotlinecesena.controller.menu;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import hotlinecesena.controller.AudioControllerImpl;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.utilities.SceneSwapper;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class RankingController{
	
	private final SceneSwapper sceneSwapper = new SceneSwapper();
	private final AudioControllerImpl audioControllerImpl;
	private final Stage primaryStage;

    public RankingController(Stage primaryStage, AudioControllerImpl audioControllerImpl) {
    	this.primaryStage = primaryStage;
    	this.audioControllerImpl = audioControllerImpl;
    }

    public void backButtonClick() throws IOException {
        JSONDataAccessLayer.newInstance();
        sceneSwapper.swapScene(
        		new StartMenuController(primaryStage, audioControllerImpl),
        		"StartMenuView.fxml", primaryStage);
    }

    public String getNameFromUser() throws JsonGenerationException, JsonMappingException, IOException {
        final TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Text Input Dialog");
        textInputDialog.getDialogPane().setContentText("Insert your name:");
        textInputDialog.showAndWait();
        final TextField input = textInputDialog.getEditor();
        if (input.getText() != null && input.getText().toString().length() != 0) {
        	return input.getText();
        }
        else {
			throw new IOException("Invalid name!");
		}
    }
}
