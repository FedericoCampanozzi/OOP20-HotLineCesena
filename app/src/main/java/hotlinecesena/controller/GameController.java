package hotlinecesena.controller;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import hotlinecesena.controller.menu.LoadingController;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.utilities.SceneSwapper;

public class GameController extends Application {

	private final MissionController mc = new MissionController();
	private final GameLoopController lc = new GameLoopController();
	
	public MissionController getMissionController() {
		return this.mc;
	}
	
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
        generateMissions();
        new AudioEventController();
        lc.loop();
	}
	
	private void generateMissions() {
        mc.addQuest("uccidi 3 nemici", () -> JSONDataAccessLayer.getInstance().getEnemy().getDeathEnemyCount() == 3);
        mc.addQuest("uccidi tutti i nemici", () -> JSONDataAccessLayer.getInstance().getEnemy().getEnemies().isEmpty());
	}
}
