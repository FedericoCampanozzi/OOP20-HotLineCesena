package hotlinecesena.controller;

import hotlinecesena.model.DALImpl;
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
    private Stage stg;
    private final GameLoopController glc = new GameLoopController();
    
	public static void main(String[] args) throws IOException {
		DALImpl.getInstance();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception{
		stg = primaryStage;
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource(DALImpl.getInstance().getGuiPath().get("StartMenuView.fxml")));
        primaryStage.setTitle("HotLine Cesena");
        primaryStage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.show();
        glc.loop();
        glc.addMethodToUpdate((t) -> this.testMethodLoop(t) );
    }
	
	public void changeScene(String fxml) throws IOException{
		Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
		owner.getScene().setRoot(FXMLLoader.load(ClassLoader.getSystemResource(DALImpl.getInstance().getGuiPath().get(fxml))));
	}
	
	public void testMethodLoop(double deltaTime) {
		System.out.println("loop example : " + deltaTime);
	}
	
	public GameLoopController getGameLoop() {
		return this.glc;
	}
}
