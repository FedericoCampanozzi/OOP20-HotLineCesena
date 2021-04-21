package hotlinecesena.view.menu;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public interface LoadingView extends Initializable{

	/**
	 * Set up the loading screen, when loading is completed the {@code StartMenu} will be shown.
	 */
	void initialize(URL location, ResourceBundle resources);

}