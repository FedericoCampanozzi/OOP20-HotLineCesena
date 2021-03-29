package hotlinecesena.view;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import java.util.ResourceBundle;

import hotlinecesena.model.DALImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Pair;

public class WorldView implements Initializable{
	
	private static final int WIDTH = (int) (Screen.getPrimary().getBounds().getMaxX() / 2);
	private static final int HEIGHT = (int) (Screen.getPrimary().getBounds().getMaxY() / 2);
	
	private GridPane gridPane = new GridPane();

	@FXML
	private BorderPane borderPane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Stage primaryStage = (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
		// primaryStage.setWidth(WIDTH);
		// primaryStage.setHeight(HEIGHT);
        
        try {
        	Map<Pair<Integer, Integer>, Character> gameMap = DALImpl.getInstance().getGenerator().getMap();
        	int maxRow = 0;
        	int minRow = 0;
        	int maxCol = 0;
        	int minCol = 0;
        	for (Entry<Pair<Integer, Integer>, Character> elem : gameMap.entrySet()) {
        	    if (minRow == 0 || elem.getKey().getKey() < minRow) {
					minRow = elem.getKey().getKey();
				}
        	    if (maxRow == 0 || elem.getKey().getKey() > maxRow) {
        	    	maxRow = elem.getKey().getKey();
				}
        	    if (minCol == 0 || elem.getKey().getValue() < minCol) {
        	    	minCol = elem.getKey().getValue();
				}
        	    if (maxCol == 0 || elem.getKey().getValue() > maxCol) {
        	    	maxCol = elem.getKey().getValue();
				}
        	}

        	borderPane.setCenter(gridPane);
        	int rows = maxRow - minRow + 1;
    		int cols = maxCol - minCol + 1;
    		
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
            		Button button = createButton(row, col);
            		button.setText(Character.toString(gameMap.get(new Pair<Integer, Integer> (minRow + row, minCol + col))));
                    gridPane.add(button, col, row);
            	}
            }
            
            primaryStage.setFullScreen(true);
        	
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	 private Button createButton(int row, int col) {
		 	Button button = new Button();
	        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	        button.setDisable(true);
	        return button;
	    }

}
