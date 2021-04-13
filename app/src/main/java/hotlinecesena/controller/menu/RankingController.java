package hotlinecesena.controller.menu;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import hotlinecesena.controller.AudioControllerImpl;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.datastructure.DataJSONRanking.Row;
import hotlinecesena.model.score.Score;
import hotlinecesena.utilities.SceneSwapper;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RankingController implements Initializable{
	
	@FXML
	private Button backButton;
	@FXML
	private Button addScoreButton;
	@FXML
	private TableView<Row> tableView;
	@FXML
	private TableColumn<Row, Integer> rank;
	@FXML
	private TableColumn<Row, String> name;
	@FXML
	private TableColumn<Row, Integer> points;
	@FXML
	private TableColumn<Row, String> time;
	@FXML
	private TableColumn<Row, Integer> enemyKilled;
	@FXML
	private TableColumn<Row, Integer> cunning;
	
	private SceneSwapper sceneSwapper = new SceneSwapper();
	private AudioControllerImpl audioControllerImpl;
	private Stage stage;
	private List<Row> recordList = JSONDataAccessLayer.getInstance().getRanking().getRecords();
	private ObservableList<Row> recordObservableList = FXCollections.observableList(recordList);
	private Row matchStats = new Row();
	private Random random = new Random();
	//private int totalScore;
	
	public RankingController(Stage stage, AudioControllerImpl audioControllerImpl) {
		this.stage = stage;
		this.audioControllerImpl = audioControllerImpl;
		//totalScore = score.getTotalScore();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rank.setCellFactory(col -> {
	      TableCell<Row, Integer> indexCell = new TableCell<>();
	      ReadOnlyObjectProperty<TableRow<Row>> rowProperty = indexCell.tableRowProperty();
	      ObjectBinding<String> rowBinding = Bindings.createObjectBinding(() -> {
	        TableRow<Row> row = rowProperty.get();
	        if (row != null) {
	          int rowIndex = row.getIndex();
	          if (rowIndex < row.getTableView().getItems().size()) {
	            return Integer.toString(rowIndex + 1);
	          }
	        }
	        return null;
	      }, rowProperty);
	      indexCell.textProperty().bind(rowBinding);
	      return indexCell;
	    });
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		points.setCellValueFactory(new PropertyValueFactory<>("points"));
		time.setCellValueFactory(new PropertyValueFactory<>("time"));
		enemyKilled.setCellValueFactory(new PropertyValueFactory<>("enemyKilled"));
		cunning.setCellValueFactory(new PropertyValueFactory<>("cunning"));
		
		tableView.setItems(recordObservableList);
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
			matchStats.setName(input.getText());
			matchStats.setPoints(random.nextInt());
			matchStats.setEnemyKilled(JSONDataAccessLayer.getInstance().getEnemy().getDeathEnemyCount());
			matchStats.setTime(convertTime(random.nextInt()));
			matchStats.setCunning(random.nextInt());
		}
		recordList.add(matchStats);
		updateList();
		JSONDataAccessLayer.getInstance().getRanking().write();
		addScoreButton.setDisable(true);
		addScoreButton.setVisible(false);
	}
	
	private void sortRecords() {
		recordList.sort(Comparator.comparing(Row::getPoints).reversed());
	}
	
	private void updateList() {
		sortRecords();
		recordObservableList = FXCollections.observableList(recordList);
		tableView.refresh();
	}
	
	public String convertTime(int time) {
        long second = (time / 1000) % 60;
        long minute = (time / (1000 * 60)) % 60;
        long hour = (time / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}
