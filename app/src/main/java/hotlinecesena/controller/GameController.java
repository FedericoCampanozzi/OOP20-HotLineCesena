package hotlinecesena.controller;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;

public class GameController extends Application {

	private final MissionController mc = new MissionController();
	
	public MissionController getMissionController() {
		return this.mc;
	}
	
	public static void main(String[] args) throws IOException {
		JSONDataAccessLayer.getInstance();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource(JSONDataAccessLayer.getInstance().getGuiPath().getPath("LoadingView.fxml")));
        primaryStage.setTitle("HotLine Cesena");
        primaryStage.setScene(new Scene(root, JSONDataAccessLayer.getInstance().getSettings().getMonitorX(), 
        		JSONDataAccessLayer.getInstance().getSettings().getMonitorY()));
        primaryStage.setResizable(false);
        primaryStage.show();
        generateMissions();
	}
	
	private void generateMissions() {
        mc.addQuest("uccidi tutti i nemici", () -> JSONDataAccessLayer.getInstance().getEnemy().getEnemies().size() == 
        		JSONDataAccessLayer.getInstance().getEnemy().getDeathEnemy());
	}
}
