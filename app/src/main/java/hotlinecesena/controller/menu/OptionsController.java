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
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.utilities.SceneSwapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.util.Pair;

public class OptionsController implements Initializable {
	
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
	
	private SceneSwapper sceneSwapper = new SceneSwapper();
	private Map<String, String> resMap = JSONDataAccessLayer.getInstance().getSettings().getResolutions();
	private List<Pair<Integer, Integer>> resolutions = new ArrayList<>();
	private Optional<Stage> worldStage;
	private Stage optionsStage;
	private AudioControllerImpl audioControllerImpl;
	private Optional<PauseController> pauseController;
	
	public OptionsController(Stage optionsStage, Optional<Stage> worldStage, AudioControllerImpl audioControllerImpl, Optional<PauseController> pauseController) {
		this.optionsStage = optionsStage;
		this.worldStage = worldStage;
		this.audioControllerImpl = audioControllerImpl;
		this.pauseController = pauseController;
		this.optionsStage.setOnCloseRequest(e -> backButton.fire());
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Initialize audio settings
		audioControllerImpl.playMusic();
		musicRadioButton.setSelected(JSONDataAccessLayer.getInstance().getSettings().isMusicActive());
		soundsRadioButton.setSelected(JSONDataAccessLayer.getInstance().getSettings().isEffectActive());
		int startVolumeValue = JSONDataAccessLayer.getInstance().getSettings().getVolume();
		volSlider.setValue(startVolumeValue);
		try {
			updateVolume(startVolumeValue);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Initialize graphic settings
		fullScreenRadioButton.setSelected(JSONDataAccessLayer.getInstance().getSettings().getFullScreen());
		resMap.forEach((x, y) -> resolutions.add(new Pair<Integer, Integer>(Integer.parseInt(x), Integer.parseInt(y))));
		resolutionComboBox.setItems(FXCollections.observableArrayList(resolutions));
		resolutionComboBox.setDisable(JSONDataAccessLayer.getInstance().getSettings().getFullScreen());
	}	
	
	public void backClick(final ActionEvent event) throws IOException {
		JSONDataAccessLayer.getInstance().getSettings().write();
		audioControllerImpl.stopMusic();
		if (worldStage.isPresent()) {
			sceneSwapper.swapScene(pauseController.get(), "PauseView.fxml", optionsStage);
		}
		else {
			sceneSwapper.swapScene(new StartMenuController(optionsStage, audioControllerImpl), "StartMenuView.fxml", optionsStage);
		}
	}
	
	public void volSliderValueChanged() throws IOException {
		updateVolume((int) volSlider.getValue());
	}
	
	private void updateVolume(int value) throws JsonGenerationException, JsonMappingException, IOException {
		JSONDataAccessLayer.getInstance().getSettings().setVolume(value);
		audioControllerImpl.updateSettings();
	}
	
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
	
	public void resolutionChoosed() {
		resolutions.forEach(r -> {
			if (resolutionComboBox.getValue() == r) {
				JSONDataAccessLayer.getInstance().getSettings().setMonitorX(r.getKey());
				JSONDataAccessLayer.getInstance().getSettings().setMonitorY(r.getValue());
			}
		});
	}
	
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
	
	public void soundsRDClick() throws JsonGenerationException, JsonMappingException, IOException {
		if (musicRadioButton.isSelected()) {
			JSONDataAccessLayer.getInstance().getSettings().setEffectActive(true);
			audioControllerImpl.updateSettings();
		}
		else {
			JSONDataAccessLayer.getInstance().getSettings().setEffectActive(false);
			audioControllerImpl.updateSettings();
		}
	}
}