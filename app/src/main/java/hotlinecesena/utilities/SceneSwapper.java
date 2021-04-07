package hotlinecesena.utilities;

import java.io.IOException;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneSwapper {
	
	public void changeScene(final String fxml, final ActionEvent event) throws IOException {
		final Parent root = FXMLLoader.load(ClassLoader.getSystemResource(JSONDataAccessLayer.getInstance().getGuiPath().getPath(fxml)));
		final Scene scene = new Scene(root, 600, 400);
		final Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	public void newStageWithScene(final String fxml) throws IOException {
		final Parent root = FXMLLoader.load(ClassLoader.getSystemResource(JSONDataAccessLayer.getInstance().getGuiPath().getPath(fxml)));
		final Scene scene = new Scene(root, 600, 400);
		final Stage pauseStage = new Stage();
		pauseStage.setScene(scene);
		pauseStage.show();
	}
}
