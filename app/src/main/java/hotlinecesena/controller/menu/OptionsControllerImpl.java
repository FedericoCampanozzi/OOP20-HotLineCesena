package hotlinecesena.controller.menu;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import hotlinecesena.controller.AudioControllerImpl;
import hotlinecesena.controller.SceneSwapper;
import hotlinecesena.controller.SceneSwapperImpl;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * Options menu scene where user can sets up custom settings, controls {@code OptionsView.fxml}.
 */
public class OptionsControllerImpl implements OptionsController {
	
	@FXML
	private Slider volSlider;
	@FXML
	private RadioButton musicRadioButton;
	@FXML
	private RadioButton soundsRadioButton;
	@FXML
	private RadioButton fullScreenRadioButton;
	@FXML
	private ComboBox<Pair<Integer, Integer>> resolutionComboBox;
	@FXML
	private Button backButton;
	
	private final SceneSwapper sceneSwapper = new SceneSwapperImpl();
	private final AudioControllerImpl audioControllerImpl;
	private final Optional<PauseControllerImpl> pauseController;
	private final Stage optionsStage;
	
	private Map<String, String> resMap = JSONDataAccessLayer.getInstance().getSettings().getResolutions();
	private List<Pair<Integer, Integer>> resolutions = new ArrayList<>();

	/**
	 * Class constructor.
	 * @param optionsStage
	 * 				The stage of the Options scene.
	 * @param audioControllerImpl
	 * 				The audio controller of the entire application.
	 * @param pauseController
	 * 				The controller of the Pause menu (Optional).
	 */
	public OptionsControllerImpl(Stage optionsStage, AudioControllerImpl audioControllerImpl, Optional<PauseControllerImpl> pauseController) {
		this.optionsStage = optionsStage;
		this.audioControllerImpl = audioControllerImpl;
		this.pauseController = pauseController;
		this.optionsStage.setOnCloseRequest(e -> backButton.fire());
	}
	
	/**
	 * Initialize audio and graphic settings.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			initAudioSettings();
			initGraphicSettings();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * When the {@code back} button is pressed, go back to previous scene.
	 * @param event
	 * @throws IOException
	 */
	@Override
	@FXML
	public void backClick(final ActionEvent event) throws IOException {
		JSONDataAccessLayer.getInstance().getSettings().write();
		audioControllerImpl.stopMusic();
		if (pauseController.isPresent()) {
			sceneSwapper.swapScene(pauseController.get(), "PauseView.fxml", optionsStage);
		}
		else {
			sceneSwapper.swapScene(
					new StartMenuControllerImpl(optionsStage, audioControllerImpl),
					"StartMenuView.fxml",
					optionsStage);
		}
	}
	
	/**
	 * When user changes the {@code volumeSlider} value, update volume in settings.
	 * @throws IOException
	 */
	@Override
	@FXML
	public void volSliderValueChanged() throws IOException {
		JSONDataAccessLayer.getInstance().getSettings().setVolume((int) volSlider.getValue());
		audioControllerImpl.updateSettings();
	}
	
	/**
	 * When user changes the {@code fullScreenRadioButton} value, update full screen status in settings.
	 */
	@Override
	@FXML
	public void fullScreenRadioButtonChangedState() {
		if (fullScreenRadioButton.isSelected()) {
			resolutionComboBox.setDisable(true);
			JSONDataAccessLayer.getInstance().getSettings().setFullScreen(true);
		}
		else {
			resolutionComboBox.setDisable(false);
			JSONDataAccessLayer.getInstance().getSettings().setFullScreen(false);
		}
	}
	/**
	 * When user changes the {@code resolutionComboBox} value, update resolution in settings.
	 */
	@Override
	@FXML
	public void resolutionChoosed() {
		resolutions.forEach(r -> {
			if (resolutionComboBox.getValue() == r) {
				JSONDataAccessLayer.getInstance().getSettings().setMonitorX(r.getKey());
				JSONDataAccessLayer.getInstance().getSettings().setMonitorY(r.getValue());
			}
		});
	}
	
	/**
	 * When user changes the {@code musicRadioButton} value, update playing music status in settings.
	 */
	@Override
	@FXML
	public void musicRDClick() throws JsonGenerationException, JsonMappingException, IOException {
		if (musicRadioButton.isSelected()) {
			JSONDataAccessLayer.getInstance().getSettings().setMusicActive(true);
			audioControllerImpl.updateSettings();
		}
		else {
			JSONDataAccessLayer.getInstance().getSettings().setMusicActive(false);
			audioControllerImpl.updateSettings();
		}
	}
	
	/**
	 * When user changes the {@code soundsRadioButton} value, update playing sounds status in settings.
	 */
	@Override
	@FXML
	public void soundsRDClick() throws JsonGenerationException, JsonMappingException, IOException {
		if (soundsRadioButton.isSelected()) {
			JSONDataAccessLayer.getInstance().getSettings().setEffectActive(true);
			audioControllerImpl.updateSettings();
		}
		else {
			JSONDataAccessLayer.getInstance().getSettings().setEffectActive(false);
			audioControllerImpl.updateSettings();
		}
	}
	
	/**
	 * Initialize audio view settings.
	 * @throws IOException
	 */
	private void initAudioSettings() throws IOException{
		audioControllerImpl.playMusic();
		musicRadioButton.setSelected(JSONDataAccessLayer.getInstance().getSettings().isMusicActive());
		soundsRadioButton.setSelected(JSONDataAccessLayer.getInstance().getSettings().isEffectActive());
		int startVolumeValue = JSONDataAccessLayer.getInstance().getSettings().getVolume();
		volSlider.setValue(startVolumeValue);
		volSliderValueChanged();
	}
	
	/**
	 * Initialize graphic view settings.
	 */
	private void initGraphicSettings() {
		fullScreenRadioButton.setSelected(JSONDataAccessLayer.getInstance().getSettings().getFullScreen());
		resMap.forEach((x, y) -> resolutions.add(new Pair<Integer, Integer>(Integer.parseInt(x), Integer.parseInt(y))));
		resolutionComboBox.setItems(FXCollections.observableArrayList(resolutions));
		resolutionComboBox.setDisable(JSONDataAccessLayer.getInstance().getSettings().getFullScreen());
	}
}