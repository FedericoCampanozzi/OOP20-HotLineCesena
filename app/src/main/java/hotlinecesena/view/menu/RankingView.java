package hotlinecesena.view.menu;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import javafx.fxml.Initializable;

/**
 * Scene that will show the data retrieved from the rankings stats, controls {@code RankingView.fxml}.
 */
public interface RankingView extends Initializable{

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