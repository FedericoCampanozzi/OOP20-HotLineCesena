package hotlinecesena.view;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SimbolsType;
import hotlinecesena.model.dataccesslayer.datastructure.DataWorldMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Pair;

public class WorldView implements Initializable{
	
	// private static final int WIDTH = (int) (Screen.getPrimary().getBounds().getMaxX() / 2);
	// private static final int HEIGHT = (int) (Screen.getPrimary().getBounds().getMaxY() / 2);
	
	private GridPane gridPane = new GridPane();
	DataWorldMap world = JSONDataAccessLayer.getInstance().getWorld();
    Map<Pair<Integer, Integer>, SimbolsType> worldMap = world.getWorldMap();

	@FXML
	private BorderPane borderPane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Stage primaryStage = (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
		// primaryStage.setWidth(WIDTH);
		// primaryStage.setHeight(HEIGHT);
        
        int rows = world.getMaxY() - world.getMinY() + 1;
    	int cols = world.getMaxX() - world.getMinX() + 1;
    	
    	System.out.println("rows: " + rows);
    	System.out.println("cols: " + cols);
    		
    	for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
    		RowConstraints rc = new RowConstraints();
    		rc.setVgrow(Priority.ALWAYS) ; // allow row to grow
    		rc.setFillHeight(true); // ask nodes to fill height for row
    		// other settings as needed...
    		gridPane.getRowConstraints().add(rc);
    	}
    	for (int colIndex = 0; colIndex < cols; colIndex++) {
    		ColumnConstraints cc = new ColumnConstraints();
    		cc.setHgrow(Priority.ALWAYS) ; // allow column to grow
    		cc.setFillWidth(true); // ask nodes to fill space for column
    		// other settings as needed...
    		gridPane.getColumnConstraints().add(cc);
    	}
        for (int row = 0 ; row < rows ; row++) {
            for (int col = 0 ; col < cols ; col++) {
            	Button button = createButton(col, row);
            	gridPane.add(button, col, row);
            }
        }
        borderPane.setCenter(gridPane);    
        primaryStage.setFullScreen(true);
	}

	 private Button createButton(int col, int row) {
		 	Button button = new Button();
	        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	        button.setDisable(true);
	        button.setText(Character.toString(worldMap.get(new Pair<Integer, Integer> (world.getMinX() + col, world.getMinY() + row)).getDecotification()));
	        return button;
	    }

}
