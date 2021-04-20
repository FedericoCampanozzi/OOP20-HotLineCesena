package hotlinecesena.controller.core;

import java.io.IOException;

import javafx.stage.Stage;
import javafx.application.Application;
import hotlinecesena.view.SceneSwapper;
import hotlinecesena.view.menu.LoadingView;

public class Launcher extends Application {
	/**
     * Main method of application.
     * 
     * @param args parameters
	 * @throws IOException 
     */
    public static void main(final String[] args) throws IOException {
    	launch(args);
    }
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.show();
		new SceneSwapper().swapScene(
				new LoadingView(primaryStage),
				"LoadingView.fxml",
				primaryStage);
	}
}
