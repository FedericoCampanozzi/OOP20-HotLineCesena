package hotlinecesena;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import hotlinecesena.controller.AudioControllerImpl;
import hotlinecesena.controller.SceneSwapperImpl;
import hotlinecesena.controller.menu.OptionsControllerImpl;
import hotlinecesena.controller.menu.StartMenuControllerImpl;
import hotlinecesena.model.score.Score;
import hotlinecesena.model.score.ScoreImpl;
import hotlinecesena.model.score.partials.PartialStrategyFactoryImpl;
import hotlinecesena.view.menu.LoadingViewImpl;
import hotlinecesena.view.menu.RankingViewImpl;

public class ViewsTest {
	
	boolean exception = false;

	// This is a VIEW TEST ONLY, the user interactions are not guaranteed... LOOK BUT DON'T TOUCH!
	
    @Test
    public void viewsTest() throws InterruptedException {
    	Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				new JFXPanel();
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                    	// Call the method you want to test
                    	
                    	startMenuViewTest();
                     // optionsViewTest();
                     // rankingViewTest();
                     // LoadingViewTest();
                    }
                });
			}
		});
    	thread.start();
        Thread.sleep(10000);
    }

    public void startMenuViewTest(){
    	try {
    		Stage stage = new Stage();
            AudioControllerImpl audioControllerImpl = new AudioControllerImpl();
            SceneSwapperImpl sceneSwapperImpl = new SceneSwapperImpl();
    		sceneSwapperImpl.swapScene(new StartMenuControllerImpl(stage, audioControllerImpl), "StartMenuView.fxml", stage);
            stage.show();
		} catch (IOException e) {
			exception = true;
		}
    }

    public void optionsViewTest() {
    	try {
    		Stage stage = new Stage();
            AudioControllerImpl audioControllerImpl = new AudioControllerImpl();
            SceneSwapperImpl sceneSwapperImpl = new SceneSwapperImpl();
    		sceneSwapperImpl.swapScene(new OptionsControllerImpl(stage, audioControllerImpl, Optional.empty()), "OptionsView.fxml", stage);
            stage.show();
		} catch (IOException e) {
			exception = true;
		}
    }

    public void rankingViewTest() {
    	try {
	    	Stage stage = new Stage();
	    	Score score = new ScoreImpl(new PartialStrategyFactoryImpl());
			AudioControllerImpl audioControllerImpl = new AudioControllerImpl();
			SceneSwapperImpl sceneSwapperImpl = new SceneSwapperImpl();
			sceneSwapperImpl.swapScene(new RankingViewImpl(
	                stage,
	                audioControllerImpl,
	                score.getPartialScores(),
	                score.getTotalScore()),
	                "RankingView.fxml",
	                stage);
			stage.show();
    	} catch (IOException e) {
			exception = true;
		}
    }

    public void LoadingViewTest() {
    	try {
    		Stage stage = new Stage();
    		SceneSwapperImpl sceneSwapperImpl = new SceneSwapperImpl();
    		sceneSwapperImpl.swapScene(new LoadingViewImpl(stage), "LoadingView.fxml", stage);
            stage.show();
		} catch (IOException e) {
			exception = true;
		}
	}
}