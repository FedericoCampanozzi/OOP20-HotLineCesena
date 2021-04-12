package hotlinecesena.controller.menu;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import hotlinecesena.controller.AudioControllerImpl;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.utilities.SceneSwapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.util.Pair;

public class OptionsController implements Initializable {
	
	@FXML
	private Slider volSlider;
	@FXML
	private Label percVolLabel;
	@FXML
	private RadioButton musicRadioButton;
	@FXML
	private RadioButton soundsRadioButton;
	@FXML
	private RadioButton fullScreenRadioButton;
	@FXML
	private ComboBox<Label> resolutionComboBox;
	@FXML
	private Button upButton;
	@FXML
	private Button leftButton;
	@FXML
	private Button downButton;
	@FXML
	private Button rightButton;
	@FXML
	private Button backButton;
	
	private SceneSwapper sceneSwapper = new SceneSwapper();
	private Map<Pair<Integer, Integer>, Label> resolutions = new LinkedHashMap<>();
	private Optional<Stage> worldStage;
	private Stage optionsStage;
	private AudioControllerImpl audioControllerImpl;
	
	public OptionsController(Stage optionsStage, Optional<Stage> worldStage, AudioControllerImpl audioControllerImpl) {
		this.optionsStage = optionsStage;
		this.worldStage = worldStage;
		this.audioControllerImpl = audioControllerImpl;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Initialize audio settings
		musicRadioButton.setSelected(JSONDataAccessLayer.getInstance().getSettings().isMusicActive());
		soundsRadioButton.setSelected(JSONDataAccessLayer.getInstance().getSettings().isEffectActive());
		int startVolumeValue = JSONDataAccessLayer.getInstance().getSettings().getVolume();
		audioControllerImpl.playMusic();
		volSlider.setValue(startVolumeValue);
		try {
			updateVolume(startVolumeValue);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Initialize graphic settings
		resolutions.put(new Pair<Integer, Integer>(600,  400), null);
		resolutions.put(new Pair<Integer, Integer>(800,  600), null);
		resolutions.put(new Pair<Integer, Integer>(1024,  768), null);
		resolutions.put(new Pair<Integer, Integer>(1280,  720), null);
		resolutions.put(new Pair<Integer, Integer>(1920,  1080), null);
		for (var res : resolutions.entrySet()) {
			Label label = new Label(res.getKey().getKey() + "x" + res.getKey().getValue());
			res.setValue(label);
		}
		resolutionComboBox.getItems().addAll(resolutions.values());
	}	
	
	public void backClick(final ActionEvent event) throws IOException {
		if (worldStage.isPresent()) {
			audioControllerImpl.stopMusic();
			sceneSwapper.swapScene(new PauseController(optionsStage, worldStage, audioControllerImpl), "PauseView.fxml", optionsStage);
		}
		else {
			audioControllerImpl.stopMusic();
			sceneSwapper.swapScene(new StartMenuController(optionsStage, audioControllerImpl), "StartMenuView.fxml", optionsStage);
		}
	}
	
	public void volSliderValueChanged() throws IOException {
		updateVolume((int) volSlider.getValue());
	}
	
	private void updateVolume(int value) throws JsonGenerationException, JsonMappingException, IOException {
		percVolLabel.setText(Integer.toString(value) + "%");
		JSONDataAccessLayer.getInstance().getSettings().setVolume(value);
		JSONDataAccessLayer.getInstance().getSettings().write();
		audioControllerImpl.updateSettings();
	}
	
	public void fullScreenRadioButtonChangedState() {
		if (fullScreenRadioButton.isSelected()) {
			JSONDataAccessLayer.getInstance().getSettings().setFullScreen(true);
		}
		else {
			JSONDataAccessLayer.getInstance().getSettings().setFullScreen(false);
		}
	}
	
	public void resolutionChoosed() {
		/*
		Pair<Integer, Integer> res = new Pair<Integer, Integer>(null, null);
		res = resolutions.keySet()
					.stream()
					.filter(key -> resolutionComboBox.getValue().equals(resolutions.get(key)))
					.findFirst().get();
		JSONDataAccessLayer.getInstance().getSettings().setMonitorX(res.getKey());
		JSONDataAccessLayer.getInstance().getSettings().setMonitorY(res.getValue());
		optionsStage.setX(res.getKey());
		optionsStage.setY(res.getValue());
		worldStage.get().setX(res.getKey());
		worldStage.get().setY(res.getValue());
		*/
	}
	
	public void musicRDClick() throws JsonGenerationException, JsonMappingException, IOException {
		if (musicRadioButton.isSelected()) {
			JSONDataAccessLayer.getInstance().getSettings().setMusicActive(true);
			JSONDataAccessLayer.getInstance().getSettings().write();
			audioControllerImpl.updateSettings();
		}
		else {
			JSONDataAccessLayer.getInstance().getSettings().setMusicActive(false);
			JSONDataAccessLayer.getInstance().getSettings().write();
			audioControllerImpl.updateSettings();
		}
	}
	
	public void soundsRDClick() throws JsonGenerationException, JsonMappingException, IOException {
		if (musicRadioButton.isSelected()) {
			JSONDataAccessLayer.getInstance().getSettings().setEffectActive(true);
			JSONDataAccessLayer.getInstance().getSettings().write();
			audioControllerImpl.updateSettings();
		}
		else {
			JSONDataAccessLayer.getInstance().getSettings().setEffectActive(false);
			JSONDataAccessLayer.getInstance().getSettings().write();
			audioControllerImpl.updateSettings();
		}
	}
}