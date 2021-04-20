package hotlinecesena.view.menu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hotlinecesena.controller.AudioControllerImpl;
import hotlinecesena.controller.menu.StartMenuController;
import hotlinecesena.utilities.SceneSwapper;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

/**
 * Timed project presentation scene, controls {@code LoadingView.fxml}.
 */
public class LoadingView implements Initializable{
	
	@FXML
	private ProgressBar progressBar;
	@FXML
	private Label loadingLabel;
	
	private static final double EPSILON = 0.0000005;
	
	private final SceneSwapper sceneSwapper = new SceneSwapper();
	private final AudioControllerImpl audioControllerImpl = new AudioControllerImpl();
	private final Stage primaryStage;
	
	/**
	 * Class constructor.
	 * @param primaryStage
	 * 				The stage where the scene is contained.
	 */
	public LoadingView(final Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	/**
	 * Set up the loading screen, when loading is completed the {@code StartMenu} will be shown.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sceneSwapper.setUpStage(primaryStage);
		final Task<Void> task = new Task<Void>() {
            final int N_ITERATIONS = 10;

            @Override
            protected Void call() throws Exception {
                for (int i = 0; i < N_ITERATIONS; i++) {
                    updateProgress(i + 1, N_ITERATIONS);
                    Thread.sleep(1000);
                }
                return null;
            }
        };
        progressBar.progressProperty().bind(task.progressProperty());
        progressBar.progressProperty().addListener(observable -> {
            if (progressBar.getProgress() >= 1 - EPSILON) {
                progressBar.setStyle("-fx-accent: green;");
                try {
					sceneSwapper.swapScene(
							new StartMenuController(primaryStage, audioControllerImpl),
							"StartMenuView.fxml",
							primaryStage);
				} catch (IOException e) {
					e.printStackTrace();
				}
                
            }
        });
        final Thread thread = new Thread(task, "task-thread");
        thread.setDaemon(true);
        thread.start();
	}
}
