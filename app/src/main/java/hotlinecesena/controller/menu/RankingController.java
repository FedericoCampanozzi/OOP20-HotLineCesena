package hotlinecesena.controller.menu;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface RankingController {

	/**
	 * When the {@code back} button is pressed, go back to {@code StartMenu} and create a new instance of DAL.
	 * @throws IOException
	 */
	void backButtonClick() throws IOException;

	/**
	 * Show a TextInputDialog where user can enter his name. The current match stats will be saved with that name.
	 * @return The name entered by the user in the TextInputDialog.
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	String getNameFromUser() throws JsonGenerationException, JsonMappingException, IOException;

}