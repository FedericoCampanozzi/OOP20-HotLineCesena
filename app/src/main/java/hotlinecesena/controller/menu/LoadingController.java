package hotlinecesena.controller.menu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hotlinecesena.controller.AudioControllerImpl;
import hotlinecesena.utilities.SceneSwapper;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class LoadingController implements Initializable{
	
	@FXML
	ProgressBar progressBar;
	@FXML
	Label loadingLabel;
	
	private SceneSwapper sceneSwapper = new SceneSwapper();
	private static final double EPSILON = 0.0000005;
	private Stage stage;
	private AudioControllerImpl audioControllerImpl = new AudioControllerImpl();
	
	public LoadingController(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		final Task<Void> task = new Task<Void>() {
            final int N_ITERATIONS = 10;

            @Override
            protected Void call() throws Exception {
                for (int i = 0; i < N_ITERATIONS; i++) {
                    updateProgress(i + 1, N_ITERATIONS);
                    // sleep is used to simulate doing some work which takes some time....
                    Thread.sleep(1000);
                }

                return null;
            }
        };

        progressBar.progressProperty().bind(
                task.progressProperty()
        );
        // color the bar green when the work is complete.
        progressBar.progressProperty().addListener(observable -> {
            if (progressBar.getProgress() >= 1 - EPSILON) {
                progressBar.setStyle("-fx-accent: forestgreen;");
                try {
					sceneSwapper.swapScene(new StartMenuController(stage, audioControllerImpl), "StartMenuView.fxml", stage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            }
        });
        
        final Thread thread = new Thread(task, "task-thread");
        thread.setDaemon(true);
        thread.start();
	}
	
}
