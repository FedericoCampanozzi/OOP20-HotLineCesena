package hotlinecesena.controller.core;

import hotlinecesena.controller.SceneSwapperImpl;
import hotlinecesena.view.menu.LoadingViewImpl;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameLoader extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
            primaryStage.show();
            new SceneSwapperImpl().swapScene(
                            new LoadingViewImpl(primaryStage),
                            "LoadingView.fxml",
                            primaryStage);
    }
}