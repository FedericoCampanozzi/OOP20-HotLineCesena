package hotlinecesena.controller.menu;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

/**
 * Options menu scene where user can sets up custom settings, controls {@code OptionsView.fxml}.
 */
public interface OptionsController extends Initializable{

	/**
	 * When the {@code back} button is pressed, go back to previous scene.
	 * @param event
	 * @throws IOException
	 */
	void backClick(ActionEvent event) throws IOException;

	/**
	 * When user changes the {@code volumeSlider} value, update volume in settings.
	 * @throws IOException
	 */
	void volSliderValueChanged() throws IOException;

	/**
	 * When user changes the {@code fullScreenRadioButton} value, update full screen status in settings.
	 */
	void fullScreenRadioButtonChangedState();

	/**
	 * When user changes the {@code resolutionComboBox} value, update resolution in settings.
	 */
	void resolutionChoosed();

	/**
	 * When user changes the {@code musicRadioButton} value, update playing music status in settings.
	 */
	void musicRDClick() throws JsonGenerationException, JsonMappingException, IOException;

	/**
	 * When user changes the {@code soundsRadioButton} value, update playing sounds status in settings.
	 */
	void soundsRDClick() throws JsonGenerationException, JsonMappingException, IOException;

}