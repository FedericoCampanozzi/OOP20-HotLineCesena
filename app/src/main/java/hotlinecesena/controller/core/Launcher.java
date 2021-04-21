package hotlinecesena.controller.core;

import java.io.IOException;
import javafx.stage.Stage;
import javafx.application.Application;
import hotlinecesena.controller.SceneSwapperImpl;
import hotlinecesena.view.menu.LoadingViewImpl;

/**
 * The entry point of application
 * @author Federico
 *
 */
public class Launcher extends Application {
	
	/**
     * Main method of application. 
     * @param args parameters
	 * @throws IOException 
     */
    public static void main(final String[] args) throws IOException {
    	launch(args);
    }
    
    /**
     * {@inheritDoc}
     */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.show();
		new SceneSwapperImpl().swapScene(
				new LoadingViewImpl(primaryStage),
				"LoadingView.fxml",
				primaryStage);
	}
}