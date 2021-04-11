package hotlinecesena.controller;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import hotlinecesena.controller.menu.LoadingController;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.utilities.SceneSwapper;

public class GameController extends Application {
	public static void main(String[] args) throws IOException {
		JSONDataAccessLayer.getInstance();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		new SceneSwapper().swapScene(new LoadingController(primaryStage), "LoadingView.fxml", primaryStage);
		primaryStage.setWidth(JSONDataAccessLayer.getInstance().getSettings().getMonitorX());
		primaryStage.setHeight(JSONDataAccessLayer.getInstance().getSettings().getMonitorY());
        primaryStage.setTitle("HotLine Cesena");
        primaryStage.setResizable(false);
        primaryStage.show();
        new AudioEventController();
	}
}