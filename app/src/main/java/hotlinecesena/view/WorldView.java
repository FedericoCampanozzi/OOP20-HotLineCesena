package hotlinecesena.view;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import hotlinecesena.controller.generator.WorldGeneratorImpl;
import hotlinecesena.model.DALImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Pair;

public class WorldView implements Initializable{
	
	private static final int WIDTH = (int) (Screen.getPrimary().getBounds().getMaxX() / 2);
	private static final int HEIGHT = (int) (Screen.getPrimary().getBounds().getMaxY() / 2);

	@FXML
	private BorderPane borderPane;
	@FXML
	private GridPane gridPane;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Stage primaryStage = (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
		primaryStage.setWidth(WIDTH);
		primaryStage.setHeight(HEIGHT);
		
		int rows = gridPane.getRowCount();
		int cols = gridPane.getColumnCount();
        for (int row = 0 ; row < rows ; row++) {
        	for (int col = 0 ; col < cols ; col++) {
        		Button button = createButton(Integer.toString(row) + ";" + Integer.toString(col));
                gridPane.add(button, col, row);
        	}
        }
        
        try {
        	Map<Pair<Integer, Integer>, Character> gameMap = DALImpl.getInstance().getGameMap();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	 private Button createButton(String text) {
	        Button button = new Button(text);
	        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	        button.setOnAction(e -> System.out.println(text));
	        return button ;
	    }

}
