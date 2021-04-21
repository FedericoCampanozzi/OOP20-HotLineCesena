package hotlinecesena.controller;

import java.io.IOException;
import java.util.function.Consumer;

import hotlinecesena.controller.mission.MissionController;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.score.Score;
import hotlinecesena.view.loader.ImageType;
import hotlinecesena.view.loader.ProxyImage;
import hotlinecesena.view.loader.SceneType;
import hotlinecesena.view.menu.RankingViewImpl;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Check the end game conditions.
 */
public class EndgameControllerImpl implements EndgameController{
	
	private final SceneSwapper sceneSwapper = new SceneSwapper();
	private final MissionController missionController;
	private final AudioControllerImpl audioControllerImpl;
	private final GameLoopController gameLoopController;
	private final Stage primaryStage;
	private final StackPane mainPane;
	
	private Score score;
	
	/**
	 * Class constructor.
	 * @param missionController
	 * @param audioControllerImpl
	 * @param gameLoopController
	 * @param primaryStage
	 * @param mainPane
	 * @param score
	 */
	public EndgameControllerImpl(
			MissionController missionController,
			AudioControllerImpl audioControllerImpl,
			GameLoopController gameLoopController,
			Stage primaryStage,
			StackPane mainPane,
			Score score
			) {
		this.missionController = missionController;
		this.audioControllerImpl = audioControllerImpl;
		this.gameLoopController = gameLoopController;
		this.primaryStage = primaryStage;
		this.mainPane = mainPane;
		this.score = score;
	}

	/**
	 * If user complete all the missions, it's a victory.
	 * Else if the player dies, it's a defeat.
	 */
	@Override
	public Consumer<Double> getUpdateMethod() {
		return deltaTime -> {
			try {
                if(missionController.missionPending().isEmpty()) {
                    this.showOutcome(true);
                }
                else if (JSONDataAccessLayer.
                		getInstance().
                		getPlayer().
                		getPly().
                		getActorStatus().
                		equals(ActorStatus.DEAD)) {
                    this.showOutcome(false);
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
		};
	}
	
	/**
     * Show the outcome of the match for a few seconds, then go to the {@code RankingView}.
     * @param win
     * @throws IOException
     */
    private void showOutcome(final Boolean win) throws IOException {
        audioControllerImpl.stopMusic();
        gameLoopController.stop();
        FadeTransition fade = getFadeTransition(win);
        fade.play();
        fade.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                try {
                    sceneSwapper.swapScene(new RankingViewImpl(
                            primaryStage,
                            audioControllerImpl,
                            score.getPartialScores(),
                            score.getTotalScore()),
                            "RankingView.fxml",
                            primaryStage);
                    sceneSwapper.setUpStage(primaryStage);
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Creates a fade transition on the image of the outcome of the match.
     * @param win
     * @return
     */
    private FadeTransition getFadeTransition(final Boolean win) {
    	final BorderPane imageBorderPane = new BorderPane();
        Image image;
        ImageView imageView = new ImageView();
        final ProxyImage proxyImage = new ProxyImage();
        if (win) {
            image = proxyImage.getImage(SceneType.MENU, ImageType.VICTORY);
        }
        else {
            image = proxyImage.getImage(SceneType.MENU, ImageType.YOU_DIED);
        }
        imageView.setOpacity(0.0);
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(primaryStage.widthProperty());
        imageBorderPane.setCenter(imageView);
        FadeTransition fade = new FadeTransition(Duration.seconds(5));
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.setCycleCount(1);
        fade.setNode(imageView);
        mainPane.getChildren().add(imageBorderPane);
        return fade;
    }
}
