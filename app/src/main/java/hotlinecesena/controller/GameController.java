package hotlinecesena.controller;

import hotlinecesena.model.DALImpl;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class GameController extends Application {
	
	private static final int SCENE_WIDTH = 600;
    private static final int SCENE_HEIGHT = 400;
    private static Stage stg;
    
	public static void main(String[] args) throws IOException {
		DALImpl.getInstance();
	}

	@Override
	public void start(Stage primaryStage) throws Exception{
		stg = primaryStage;
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("hotlinecesena/view/StartMenuView.fxml"));

        // Stage configuration
        primaryStage.setTitle("HotLine Cesena");
        primaryStage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
	
    public Object getGreeting() {
        // TODO Auto-generated method stub
        return null;
    }
    
	public void changeScene(String fxml) throws IOException{
		Parent pane = FXMLLoader.load(ClassLoader.getSystemResource(fxml));
		stg.getScene().setRoot(pane);
	}
}
