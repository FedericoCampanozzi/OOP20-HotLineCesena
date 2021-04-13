package hotlinecesena.controller.menu;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import hotlinecesena.controller.AudioControllerImpl;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.datastructure.DataJSONRanking.Row;
import hotlinecesena.utilities.SceneSwapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RankingController implements Initializable{
	
	@FXML
	private Button backButton;
	@FXML
	private TableView<Record> tableView;
	@FXML
	private TableColumn<Record, Integer> rank;
	@FXML
	private TableColumn<Record, String> name;
	@FXML
	private TableColumn<Record, Integer> points;
	@FXML
	private TableColumn<Record, String> time;
	@FXML
	private TableColumn<Record, Integer> kills;
	
	private SceneSwapper sceneSwapper = new SceneSwapper();
	private AudioControllerImpl audioControllerImpl;
	private Stage stage;
	private List<Row> recordList = JSONDataAccessLayer.getInstance().getRanking().getRecords();
	private ObservableList<Record> records = FXCollections.observableArrayList();
	
	public RankingController(Stage stage, AudioControllerImpl audioControllerImpl) {
		this.stage = stage;
		this.audioControllerImpl = audioControllerImpl;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		recordList.sort(Comparator.comparing(Row::getPoints).reversed());
		updateRecords();
		rank.setCellValueFactory(new PropertyValueFactory<>("rank"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		points.setCellValueFactory(new PropertyValueFactory<>("points"));
		time.setCellValueFactory(new PropertyValueFactory<>("time"));
		kills.setCellValueFactory(new PropertyValueFactory<>("kills"));
		
		tableView.setItems(records);
	}
	
	public void backButtonClick() throws IOException {
		JSONDataAccessLayer.newInstance();
		sceneSwapper.swapScene(new StartMenuController(stage, audioControllerImpl), "StartMenuView.fxml", stage);
	}
	
	public void addScoreClick() throws JsonGenerationException, JsonMappingException, IOException {
		TextInputDialog textInputDialog = new TextInputDialog();
		textInputDialog.setTitle("Text Input Dialog");
		textInputDialog.getDialogPane().setContentText("Insert your name:");
		textInputDialog.showAndWait();
		TextField input = textInputDialog.getEditor();
		if (input.getText() != null && input.getText().toString().length() != 0) {
			int kills = JSONDataAccessLayer.getInstance().getEnemy().getDeathEnemyCount();
			records.add(new Record(
					0,
					input.getText(),
					0,	//(int) (kills / worldController.getMatchTime() * 1000000),
					"a",	//this.getConvertTime(worldController.getMatchTime()),
					kills));
		}
		this.updateRecords();
		tableView.refresh();
	}
	
	public class Record {
		
		private final SimpleIntegerProperty rank;
		private final SimpleStringProperty name;
		private final SimpleIntegerProperty points;
		private final SimpleStringProperty time;
		private final SimpleIntegerProperty kills;
		
		public Record(int rank, String name, int points, String time, int kills) {
			this.rank = new SimpleIntegerProperty(rank);
			this.name = new SimpleStringProperty(name);
			this.points = new SimpleIntegerProperty(points);
			this.time = new SimpleStringProperty(time);
			this.kills = new SimpleIntegerProperty(kills);
		}

		public int getRank() {
			return rank.get();
		}

		public String getName() {
			return name.get();
		}

		public int getPoints() {
			return points.get();
		}

		public String getTime() {
			return time.get();
		}

		public int getKills() {
			return kills.get();
		}
	}
	
	private void updateRecords() {
		recordList.sort(Comparator.comparing(Row::getPoints).reversed());
		ObservableList<Record> newList = FXCollections.observableArrayList();
		for (int i = 0; i < recordList.size(); i++) {
			var r = recordList.get(i);
			newList.add(new Record(i + 1, r.getName(), r.getPoints(), r.getTime(), r.getEnemy_killed()));
		}
		records = (ObservableList<Record>) newList;
	}
	
	public String getConvertTime(long l) {
        long second = (l / 1000) % 60;
        long minute = (l / (1000 * 60)) % 60;
        long hour = (l / (1000 * 60 * 60)) % 24;
        String timeConverted = String.format("%02d:%02d:%02d", hour, minute, second);
        return timeConverted;
    }
}
