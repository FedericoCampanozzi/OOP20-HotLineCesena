package hotlinecesena.controller.menu;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import hotlinecesena.controller.AudioControllerImpl;
import hotlinecesena.controller.SceneSwapper;
import hotlinecesena.controller.SceneSwapperImpl;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

/**
 * Controller of {@code RankingView}.
 */
public class RankingControllerImpl implements RankingController{
	
	private final SceneSwapper sceneSwapper = new SceneSwapperImpl();
	private final AudioControllerImpl audioControllerImpl;
	private final Stage primaryStage;

	/**
	 * Class constructor.
	 * @param primaryStage
	 * 				The stage containing the rankings scene.
	 * @param audioControllerImpl
	 * 				The audio controller of the entire application.
	 */
    public RankingControllerImpl(Stage primaryStage, AudioControllerImpl audioControllerImpl) {
    	this.primaryStage = primaryStage;
    	this.audioControllerImpl = audioControllerImpl;
    }

    /**
     * When the {@code back} button is pressed, go back to {@code StartMenu} and create a new instance of DAL.
     * @throws IOException
     */
    @Override
	public void backButtonClick() throws IOException {
        JSONDataAccessLayer.newInstance();
        sceneSwapper.swapScene(
        		new StartMenuControllerImpl(primaryStage, audioControllerImpl),
        		"StartMenuView.fxml", primaryStage);
    }

    /**
     * Show a TextInputDialog where user can enter his name. The current match stats will be saved with that name.
     * @return The name entered by the user in the TextInputDialog.
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @Override
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
