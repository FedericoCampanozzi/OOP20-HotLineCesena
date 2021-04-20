package hotlinecesena.controller;

import java.io.IOException;
import com.google.common.eventbus.Subscribe;

import hotlinecesena.controller.HUD.PlayerStatsController;
import hotlinecesena.controller.camera.Camera;
import hotlinecesena.controller.camera.CameraImpl;
import hotlinecesena.controller.entities.EntityController;
import hotlinecesena.controller.entities.ProjectileController;
import hotlinecesena.controller.entities.enemy.EnemyController;
import hotlinecesena.controller.entities.player.PlayerControllerFactoryFX;
import hotlinecesena.controller.menu.PauseController;
import hotlinecesena.controller.mission.MissionBuilderImpl;
import hotlinecesena.controller.mission.MissionController;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.entities.items.ItemsType;
import hotlinecesena.model.events.AttackPerformedEvent;
import hotlinecesena.model.events.DeathEvent;
import hotlinecesena.model.events.ItemPickUpEvent;
import hotlinecesena.model.events.Subscriber;
import hotlinecesena.model.events.WeaponPickUpEvent;
import hotlinecesena.model.score.Score;
import hotlinecesena.model.score.ScoreImpl;
import hotlinecesena.model.score.partials.PartialStrategyFactoryImpl;
import hotlinecesena.utilities.SceneSwapper;
import hotlinecesena.view.WorldView;
import hotlinecesena.view.camera.CameraView;
import hotlinecesena.view.camera.CameraViewImpl;
import hotlinecesena.view.entities.Sprite;
import hotlinecesena.view.entities.SpriteImpl;
import hotlinecesena.view.input.InputListener;
import hotlinecesena.view.input.InputListenerFX;
import hotlinecesena.view.loader.ImageType;
import hotlinecesena.view.loader.ProxyImage;
import hotlinecesena.view.loader.SceneType;
import hotlinecesena.view.menu.RankingView;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller of {@code WorldView}.
 */
public class WorldController implements Subscriber {

	private final SceneSwapper sceneSwapper = new SceneSwapper();
	private final GameLoopController gameLoopController = new GameLoopController();
	private final Stage primaryStage;
	
    private WorldView worldView;
    private MissionController missionController;
    private InputListener listener;
    private AudioControllerImpl audioController;
    private Score score;

    private double playerTimeLife = 0;
    private int totalAmmoBag = 0;
    private int totalMedikit = 0;
    private int totalWeaponsChanged = 0;
    private int totalAmmoShootedByPlayer = 0;
    private boolean pickBriefCase = false;
    private int enemyKilled;

    /**
     * Class constructor.
     * Initialize the {@code GameLoopController} and all controllers related to it. After that, the loop starts.
     * @param primaryStage
     * 				The stage containing the world scene.
     * @param audioController
     * 				The audio controller of the entire application.
     * @throws IOException
     */
    public WorldController(final Stage primaryStage, final AudioControllerImpl audioController) throws IOException {
        JSONDataAccessLayer.getInstance().getPlayer().getPly().register(this);
        JSONDataAccessLayer.getInstance().getEnemy().getEnemies().forEach(itm -> itm.register(this));

        this.primaryStage = primaryStage;

        this.initAudioController(audioController);
        this.initWorldView();
        this.initMissionController();
        this.initHudController();
        this.initEnemyController();
        this.initListener();
        this.initPlayerAndCameraController();
        this.initProjectileController();
        this.initScoreModel();
        this.initRankingController();
        this.initPauseController();

        gameLoopController.loop();
    }

    /**
     * Initialize the {@code PauseController}.
     * Check whether user presses the pause key.
     */
    private void initPauseController() {
        gameLoopController.addMethodToUpdate(d -> {
            if (listener.deliverInputs().getKey().contains(KeyCode.P)) {
                try {
                    audioController.stopMusic();
                    gameLoopController.stop();
                    final Stage stage = new Stage();
                    stage.show();
                    sceneSwapper.setUpStage(stage);
                    sceneSwapper.swapScene(
                            new PauseController(stage, primaryStage, audioController, gameLoopController),
                            "PauseView.fxml",
                            stage);
                } catch (final IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    /**
     * Initialize the {@code RankingController}.
     * Check the match status: if all missions are completed, than its a victory. Else if the player is dead, it's a defeat.
     */
    private void initRankingController() {
        gameLoopController.addMethodToUpdate(d -> {
            try {
                if(missionController.missionPending().isEmpty()) {
                    this.endGame(true);
                }
                else if (JSONDataAccessLayer.getInstance().getPlayer().getPly().getActorStatus().equals(ActorStatus.DEAD)) {
                    this.endGame(false);
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
            playerTimeLife += d;
        });
    }

    /**
     * Show the outcome of the match for a few seconds, then go to the {@code RankingView}.
     * @param win
     * @throws IOException
     */
    private void endGame(final Boolean win) throws IOException {
        audioController.stopMusic();
        gameLoopController.stop();
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
        final FadeTransition fade = new FadeTransition(Duration.seconds(5));
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.setCycleCount(1);
        fade.setNode(imageView);
        worldView.getStackPane().getChildren().add(imageBorderPane);
        fade.play();
        fade.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                try {
                    sceneSwapper.swapScene(new RankingView(
                            primaryStage,
                            audioController,
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
     * Initialize the {@code ScoreModel}.
     * It tracks the match stats.
     */
    private void initScoreModel() {
        score = new ScoreImpl(new PartialStrategyFactoryImpl());
    }

    /**
     * Initialize the {@code ProjectileController}.
     */
    private void initProjectileController() {
        ProjectileController projectileController = new ProjectileController(worldView);
        gameLoopController.addMethodToUpdate(projectileController.getUpdateMethod());
    }

    private void initPlayerAndCameraController() {
        final Sprite playerSprite = new SpriteImpl(worldView.getPlayersPos().getValue());
        EntityController playerController = new PlayerControllerFactoryFX().create(
                JSONDataAccessLayer.getInstance().getPlayer().getPly(),
                playerSprite,
                listener
                );
        gameLoopController.addMethodToUpdate(playerController.getUpdateMethod());

        final CameraView cameraView = new CameraViewImpl(worldView.getGridPane());
        Camera camera = new CameraImpl(cameraView, playerSprite, listener);
        gameLoopController.addMethodToUpdate(camera.getUpdateMethod());
    }

    /**
     * Initialize the {@code InputListener}.
     */
    private void initListener() {
        listener = new InputListenerFX();
        listener.addEventHandlers(worldView.getStackPane().getScene());
    }

    /**
     * Initialize the {@code EnemyController}.
     */
    private void initEnemyController() {
        JSONDataAccessLayer.getInstance().getEnemy().getEnemies().forEach(e -> {
            final EnemyController ec = new EnemyController(e, worldView.getEnemiesSprite().get(0), JSONDataAccessLayer.getInstance().getPlayer().getPly());
            gameLoopController.addMethodToUpdate(ec.getUpdateMethod());
            worldView.getEnemiesSprite().remove(0);
        });
    }

    /**
     * Initialize the {@code HUDController}.
     * It tracks the player stats.
     * @throws IOException
     */
    private void initHudController() throws IOException {
        PlayerStatsController playerStatsController = new PlayerStatsController(worldView, missionController);
        final FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("GUI/PlayerStatsView.fxml"));
        loader.setController(playerStatsController.getPlayerStatsView());
        worldView.getStackPane().getChildren().add(loader.load());
        gameLoopController.addMethodToUpdate(playerStatsController.getUpdateMethod());
    }

    /**
     * Initialize the {@code MissionController}.
     */
    private void initMissionController() {
        missionController = new MissionBuilderImpl(this)
                .addAmmoMission(2, 6)
                .addMedikitMission(3, 6)
                .addChangeWeaponsMission(1, 3)
                .addKeyObjectMission()
                .addKillMission(1, 7)
                .build();
        gameLoopController.addMethodToUpdate(d -> missionController.update(d));
    }

    /**
     * Initialize the {@code WorldView}.
     */
    private void initWorldView() {
    	worldView = new WorldView(primaryStage);
    	worldView.start();
    }

    /**
     * Initialize the {@code AudioEventController}.
     * @param audioController
     */
    private void initAudioController(final AudioControllerImpl audioController) {
        this.audioController = audioController;
        this.audioController.playMusic();
        new AudioEventController();
    }

    /**
     * @return the player life time of the current match.
     */
	public int getPlayerLifeTime() {
        return (int)(playerTimeLife / 1000.0d);
    }

	/**
	 * @return the amount of kills.
	 */
	public int getEnemyKilledByPlayer() {
        return enemyKilled;
    }

	/**
	 * @return the amount of ammunitions bag picked.
	 */
	public int getTotalAmmoBag() {
        return totalAmmoBag;
    }

	/**
	 * @return the amount of medikit picked.
	 */
	public int getTotalMedikit() {
        return totalMedikit;
    }

	/**
	 * @return how many times the player has switched weapon.
	 */
	public int getTotalWeaponsChanged() {
        return totalWeaponsChanged;
    }

	/**
	 * @return the amount of bullets fired by player.
	 */
	public int getTotalAmmoShootedByPlayer() {
        return totalAmmoShootedByPlayer;
    }

	/**
	 * @return whether the player has picked or not the brief case.
	 */
	public boolean isPickBriefCase() {
        return pickBriefCase;
    }

	/**
	 * Updates the amount of items picked by player.
	 * @param event
	 */
    @Subscribe
    private void addItemType(final ItemPickUpEvent event) {
        if(event.getItemType().equals(ItemsType.AMMO_BAG)) {
            totalAmmoBag ++;
        } else if(event.getItemType().equals(ItemsType.MEDIKIT)) {
            totalMedikit ++;
        } else if(event.getItemType().equals(ItemsType.BRIEFCASE)) {
            pickBriefCase = true;
        }
    }

    /**
     * Updates the amount of times the player has changed weapon.
     * @param event
     */
    @Subscribe
    private void addChangedWeapons(final WeaponPickUpEvent event) {
        totalWeaponsChanged ++;
    }

    /**
     * updates the amount of bullets fired by player.
     * @param event
     */
    @Subscribe
    private void addAmmoShoot(final AttackPerformedEvent event) {
        if(event.getSourceInterfaces().contains(Player.class)) {
            totalAmmoShootedByPlayer ++;
        }
    }

    /**
     * Update the amount of kills.
     * @param event
     */
    @Subscribe
    private void addAmmoShoot(final DeathEvent event) {
        if(event.getSourceInterfaces().contains(Enemy.class)) {
            enemyKilled ++;
        }
    }
}
