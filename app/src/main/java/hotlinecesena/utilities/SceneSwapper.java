package hotlinecesena.utilities;

import java.io.IOException;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SceneSwapper {
	public void swapScene(Initializable controller, String fxml, Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(JSONDataAccessLayer.getInstance().getGuiPath().getPath(fxml)));
		loader.setController(controller);
		Pane pane;
		pane = loader.load();
		stage.setScene(new Scene(pane));
	}
}
