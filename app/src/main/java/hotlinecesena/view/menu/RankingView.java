package hotlinecesena.view.menu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import javafx.fxml.Initializable;

public interface RankingView extends Initializable{

	/**
	 * Set up column layouts and the content of the TableView.
	 */
	void initialize(URL location, ResourceBundle resources);

	/**
	 * {@link hotlinecesena.controller.menu.EndgameControllerImpl#backButtonClick()}.
	 * @throws IOException
	 */
	void backButtonClick() throws IOException;

	/**
	 * When the {@code addScore} button is pressed, create a new Row, containing the current match stats.
	 * {@link hotlinecesena.controller.menu.EndgameControllerImpl#getNameFromUser()}.
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	void addScoreClick() throws JsonGenerationException, JsonMappingException, IOException;

}