package hotlinecesena.controller;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class GameController extends Application {
	
	private static final int SCENE_WIDTH = 600;
    private static final int SCENE_HEIGHT = 400;
    
	public static void main(String[] args) throws IOException {
		JSONDataAccessLayer.generateSeed();
		JSONDataAccessLayer.getInstance();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource(JSONDataAccessLayer.getInstance().getGuiPath().getPath("StartMenuView.fxml")));
        primaryStage.setTitle("HotLine Cesena");
        primaryStage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.show();
	}
	
	public void changeScene(String fxml) throws IOException{
		Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
		owner.getScene().setRoot(FXMLLoader.load(ClassLoader.getSystemResource(JSONDataAccessLayer.getInstance().getGuiPath().getPath(fxml))));
	}
}
