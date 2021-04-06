package hotlinecesena.controller.menu;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import hotlinecesena.controller.GameController;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.util.Pair;

public class OptionsController implements Initializable {
	
	private int volValue;
	GameController gameController = new GameController();
	private List<Pair<Integer, Integer>> resolutions = new ArrayList<>();
	
	public OptionsController(){
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		resolutions.add(new Pair<Integer, Integer>(600,  400));
		resolutions.add(new Pair<Integer, Integer>(800,  600));
		resolutions.add(new Pair<Integer, Integer>(1024,  768));
		resolutions.add(new Pair<Integer, Integer>(1280,  720));
		resolutions.add(new Pair<Integer, Integer>(1920,  1080));
		
		updatePercVolLabel(JSONDataAccessLayer.getInstance().getSettings().getVolume());
		volSlider.setValue(volValue);
	}	
	
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
	
	public void backClick() throws IOException {
		gameController.changeScene("StartMenuView.fxml");
	}
	
	public void volSliderValueChanged() throws IOException {
		updatePercVolLabel((int) volSlider.getValue());
	}
	
	private void updatePercVolLabel(int value) {
		volValue = value;
		percVolLabel.setText(Integer.toString(volValue) + "%");
	}

}