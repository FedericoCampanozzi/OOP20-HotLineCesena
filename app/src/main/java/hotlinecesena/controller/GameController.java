package hotlinecesena.controller;

import java.io.IOException;

import model.DALImpl;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameController extends Application {

	public static void main(String[] args) throws IOException {
		DALImpl.getInstance();
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		launch(args);
	}
	
    public Object getGreeting() {
        // TODO Auto-generated method stub
        return null;
    }
}
