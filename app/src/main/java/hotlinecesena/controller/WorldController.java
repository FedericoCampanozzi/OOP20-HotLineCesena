package hotlinecesena.controller;

import java.io.IOException;
import java.util.Optional;
import hotlinecesena.controller.HUD.PlayerStatsController;
import hotlinecesena.controller.entities.ProjectileController;
import hotlinecesena.controller.entities.enemy.EnemyController;
import hotlinecesena.controller.entities.player.PlayerController;
import hotlinecesena.controller.entities.player.PlayerControllerFactoryFX;
import hotlinecesena.controller.menu.PauseController;
import hotlinecesena.controller.menu.RankingController;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.score.PartialScoreFactoryImpl;
import hotlinecesena.model.score.Score;
import hotlinecesena.model.score.ScoreImpl;
import hotlinecesena.utilities.SceneSwapper;
import hotlinecesena.view.WorldView;
import hotlinecesena.view.camera.CameraView;
import hotlinecesena.view.camera.CameraViewImpl;
import hotlinecesena.view.entities.Sprite;
import hotlinecesena.view.entities.SpriteImpl;
import hotlinecesena.view.input.InputListener;
import hotlinecesena.view.input.InputListenerFX;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class WorldController{

    private final WorldView view;
    private final CameraView camera;
    private final Stage primaryStage;
    private final GameLoopController gameLoopController = new GameLoopController();
    private final PlayerController playerController;
    private final ProjectileController projectileController;
    private final PlayerStatsController playerStatsController;
    private final MissionController missionController;
    private final InputListener listener;
    private final AudioControllerImpl audioControllerImpl;
    private final SceneSwapper sceneSwapper = new SceneSwapper();
    private final Score score;

    public WorldController(final Stage primaryStage, AudioControllerImpl audioControllerImpl) throws IOException{
        this.primaryStage = primaryStage;
        this.audioControllerImpl = audioControllerImpl;
        this.audioControllerImpl.playMusic();
        new AudioEventController();
        view = new WorldView(this.primaryStage);
        view.start();
        
        missionController = new MissionController();
        gameLoopController.addMethodToUpdate(d -> missionController.update(d));

        playerStatsController = new PlayerStatsController(view, missionController);
        final FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(JSONDataAccessLayer.getInstance().getGuiPath().getPath("PlayerStatsView.fxml")));
        loader.setController(playerStatsController);
        view.getBorderPane().setBottom(loader.load());
        gameLoopController.addMethodToUpdate(playerStatsController.getUpdateMethod());


        JSONDataAccessLayer.getInstance().getEnemy().getEnemies().forEach(e -> {
            final EnemyController ec = new EnemyController(e, view.getEnemiesSprite().get(0), JSONDataAccessLayer.getInstance().getPlayer().getPly());
            gameLoopController.addMethodToUpdate(ec.getUpdateMethod());
            view.getEnemiesSprite().remove(0);
        });

        listener = new InputListenerFX();
        listener.addEventHandlers(view.getStage().getScene());

        final Sprite playerSprite = new SpriteImpl(view.getPlayersPos().getValue());
        playerController = new PlayerControllerFactoryFX()
                .createPlayerController(playerSprite, listener);
        gameLoopController.addMethodToUpdate(playerController.getUpdateMethod());

        camera = new CameraViewImpl(playerSprite);
        camera.setPane(view.getGridPane());
        gameLoopController.addMethodToUpdate(camera.getUpdateMethod());

        projectileController = new ProjectileController(view);
        gameLoopController.addMethodToUpdate(projectileController.getUpdateMethod());
        
        score = new ScoreImpl(new PartialScoreFactoryImpl());
        
        gameLoopController.addMethodToUpdate(d -> {
            if(missionController.missionPending().isEmpty() || JSONDataAccessLayer.getInstance().getPlayer().getPly().getActorStatus().equals(ActorStatus.DEAD)) {
                try {
                	audioControllerImpl.stopMusic();
                	gameLoopController.stop();
                	primaryStage.setWidth(800);
                	primaryStage.setHeight(600);
                	primaryStage.centerOnScreen();
					sceneSwapper.swapScene(new RankingController(primaryStage, audioControllerImpl, score.getPartialScores(), score.getTotalScore()), "RankingView.fxml", primaryStage);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        
        gameLoopController.addMethodToUpdate(d -> {
        	view.getBorderPane().setOnKeyReleased(e -> {
        		if (e.getCode() == KeyCode.ESCAPE) {
            		try {
            			audioControllerImpl.stopMusic();
                		gameLoopController.stop();
                    	Stage stage = new Stage();
                    	stage.setWidth(800);
                    	stage.setHeight(600);
                    	stage.show();
        				sceneSwapper.swapScene(new PauseController(stage, Optional.of(primaryStage), audioControllerImpl, gameLoopController), "PauseView.fxml", stage);
        			} catch (IOException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			}
    			}
        	});
        });

        gameLoopController.loop();
    }
}