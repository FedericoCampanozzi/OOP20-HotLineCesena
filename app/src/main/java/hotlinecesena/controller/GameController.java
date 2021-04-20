package hotlinecesena.controller;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.utilities.SceneSwapper;
import hotlinecesena.view.menu.LoadingView;

public class GameController extends Application {
	public static void main(String[] args) throws IOException {
		JSONDataAccessLayer.getInstance();
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
