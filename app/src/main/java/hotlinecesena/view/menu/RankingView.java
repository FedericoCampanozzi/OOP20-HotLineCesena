package hotlinecesena.view.menu;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import hotlinecesena.controller.AudioControllerImpl;
import hotlinecesena.controller.menu.RankingController;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.datastructure.DataJSONRanking.Row;
import hotlinecesena.model.score.partials.CunningStrategy;
import hotlinecesena.model.score.partials.KillCountStrategy;
import hotlinecesena.model.score.partials.TimeStrategy;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

public class RankingView implements Initializable{

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
    @FXML
    private VBox vBox;

    private final RankingController rankingController;
    private final Map<String, Pair<Integer, Integer>> partialScore;
    private final int totalScore;
    private List<Row> recordList = JSONDataAccessLayer.getInstance().getRanking().getRecords();
    private ObservableList<Row> recordObservableList = FXCollections.observableList(recordList);

    public RankingView(final Stage primaryStage, final AudioControllerImpl audioControllerImpl, final Map<String, Pair<Integer, Integer>> partialScore, final int totalScore) {
        this.partialScore = partialScore;
        this.totalScore = totalScore;
        this.rankingController = new RankingController(primaryStage, audioControllerImpl);
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
    	setUpRankColumn();
        setUpColumn(name, "name");
        setUpColumn(points, "points");
        setUpColumn(time, "time");
        setUpColumn(enemyKilled, "enemyKilled");
        setUpColumn(cunning, "cunning");

        tableView.setItems(recordObservableList);
    }

    @FXML
    public void backButtonClick() throws IOException {
        rankingController.backButtonClick();
    }

    @FXML
    public void addScoreClick() throws JsonGenerationException, JsonMappingException, IOException {
    	String name = rankingController.getNameFromUser();
    	Row matchStatsRow = new Row(
                name,
                totalScore,
                partialScore.get(TimeStrategy.class.getSimpleName()).getValue(),
                partialScore.get(KillCountStrategy.class.getSimpleName()).getValue(),
                partialScore.get(CunningStrategy.class.getSimpleName()).getValue()
                );
        addRowToRecords(matchStatsRow);
        addScoreButton.setDisable(true);
        addScoreButton.setVisible(false);
    }

    private void addRowToRecords(Row matchStatsRow) throws JsonGenerationException, JsonMappingException, IOException {
    	recordList.add(matchStatsRow);
    	recordList.sort(Comparator.comparing(Row::getPoints).reversed());
        recordObservableList = FXCollections.observableList(recordList);
        tableView.refresh();
        JSONDataAccessLayer.getInstance().getRanking().write();
    }
    
    private void setUpRankColumn() {
    	rank.setCellFactory(col -> {
            final TableCell<Row, Integer> indexCell = new TableCell<>();
            final ReadOnlyObjectProperty<TableRow<Row>> rowProperty = indexCell.tableRowProperty();
            final ObjectBinding<String> rowBinding = Bindings.createObjectBinding(() -> {
                final TableRow<Row> row = rowProperty.get();
                if (row != null) {
                    final int rowIndex = row.getIndex();
                    if (rowIndex < row.getTableView().getItems().size()) {
                        return Integer.toString(rowIndex + 1);
                    }
                }
                return null;
            }, rowProperty);
            indexCell.textProperty().bind(rowBinding);
            return indexCell;
        });
    }
    
    private <S, T> void setUpColumn(TableColumn<S, T> tableColumn, String value) {
    	tableColumn.setCellValueFactory(new PropertyValueFactory<>(value));
    }
}