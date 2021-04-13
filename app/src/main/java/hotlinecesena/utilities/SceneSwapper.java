package hotlinecesena.utilities;

import java.io.IOException;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.view.loader.ImageType;
import hotlinecesena.view.loader.ProxyImage;
import hotlinecesena.view.loader.SceneType;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SceneSwapper {
	
	private ProxyImage proxyImage = new ProxyImage();
	
	public void swapScene(Initializable controller, String fxml, Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(JSONDataAccessLayer.getInstance().getGuiPath().getPath(fxml)));
		loader.setController(controller);
		Pane pane;
		pane = loader.load();
		stage.setScene(new Scene(pane));
		stage.getIcons().add(proxyImage.getImage(SceneType.MENU, ImageType.ICON));
	}
}
