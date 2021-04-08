package hotlinecesena.controller.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hotlinecesena.controller.WorldController;
import hotlinecesena.utilities.SceneSwapper;
import hotlinecesena.view.loader.AudioType;
import hotlinecesena.view.loader.ProxyAudio;

public class StartMenuController implements Initializable{
	
	@FXML
	private Button newGameButton;
	@FXML
	private Button optionsButton;
	@FXML
	private Button exitButton;
	
	private static SceneSwapper sceneSwapper = new SceneSwapper();
	private MediaPlayer mediaPlayer;
	ProxyAudio proxyAudio = new ProxyAudio();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mediaPlayer = proxyAudio.getMediaPlayer(AudioType.BACKGROUND);
		mediaPlayer.play();
		mediaPlayer.seek(Duration.ZERO);
	}
	
	public void newGameClick(final ActionEvent event) throws IOException {
		mediaPlayer.stop();
		final Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        new WorldController(primaryStage);
	}
	
	public void optionsClick(final ActionEvent event) throws IOException {
		mediaPlayer.stop();
		sceneSwapper.changeScene("OptionsView.fxml", event);
	}
	
	public void exitClick(final ActionEvent event) throws IOException {
		mediaPlayer.stop();
		System.exit(0);
	}
}
