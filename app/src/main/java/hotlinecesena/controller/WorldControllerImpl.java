package hotlinecesena.controller;

import java.io.IOException;
import com.google.common.eventbus.Subscribe;

import hotlinecesena.controller.camera.Camera;
import hotlinecesena.controller.camera.CameraImpl;
import hotlinecesena.controller.entities.EntityController;
import hotlinecesena.controller.entities.ProjectileControllerImpl;
import hotlinecesena.controller.entities.enemy.EnemyController;
import hotlinecesena.controller.entities.player.PlayerControllerFactoryFX;
import hotlinecesena.controller.hud.PlayerStatsControllerImpl;
import hotlinecesena.controller.menu.PauseControllerImpl;
import hotlinecesena.controller.mission.MissionBuilderImpl;
import hotlinecesena.controller.mission.MissionController;
import hotlinecesena.controller.mission.MissionControllerImpl;
import hotlinecesena.model.dataccesslayer.DataAccessLayer;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.entities.items.ItemsType;
import hotlinecesena.model.events.AttackPerformedEvent;
import hotlinecesena.model.events.DeathEvent;
import hotlinecesena.model.events.ItemPickUpEvent;
import hotlinecesena.model.events.WeaponPickUpEvent;
import hotlinecesena.model.score.Score;
import hotlinecesena.model.score.ScoreImpl;
import hotlinecesena.model.score.partials.PartialStrategyFactoryImpl;
import hotlinecesena.view.WorldView;
import hotlinecesena.view.WorldViewImpl;
import hotlinecesena.view.camera.CameraView;
import hotlinecesena.view.camera.CameraViewImpl;
import hotlinecesena.view.entities.Sprite;
import hotlinecesena.view.entities.SpriteImpl;
import hotlinecesena.view.input.InputListener;
import hotlinecesena.view.input.InputListenerFX;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 * Controller of {@code WorldView}.
 */
public class WorldControllerImpl implements WorldController {

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
    public WorldControllerImpl(final Stage primaryStage, final AudioControllerImpl audioController) throws IOException {
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
        this.initEndgameController();
        this.initPauseController();

        gameLoopController.loop();
    }

    /**
     * Initialize the {@code PauseController}.
     * Check whether user presses the pause key.
     */
    private void initPauseController() {
    	final Stage pauseStage = new Stage();
    	PauseControllerImpl pauseController = new PauseControllerImpl(
    			pauseStage,
    			primaryStage,
    			audioController,
    			gameLoopController,
    			listener);
        gameLoopController.addMethodToUpdate(pauseController.getUpdateMethod());
    }

    /**
     * Initialize the {@code RankingController}.
     * Check the match status: if all missions are completed, than its a victory. Else if the player is dead, it's a defeat.
     */
    private void initEndgameController() {
        gameLoopController.addMethodToUpdate(new EndgameControllerImpl(
        		missionController,
        		audioController,
        		gameLoopController,
        		primaryStage,
        		worldView.getStackPane(),
        		score)
        		.getUpdateMethod());
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
        ProjectileControllerImpl projectileController = new ProjectileControllerImpl(worldView);
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
        PlayerStatsControllerImpl playerStatsController = new PlayerStatsControllerImpl(worldView, missionController);
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
                .addKeyObjectMission()
                .addKillMission(1, 7)
                .build();
        //gameLoopController.addMethodToUpdate(d -> missionController.update(d));
        gameLoopController.addMethodToUpdate(missionController.getUpdateMethod());
    }

    /**
     * Initialize the {@code WorldView}.
     */
    private void initWorldView() {
    	DataAccessLayer dataAccessLayer =  JSONDataAccessLayer.getInstance();
    	worldView = new WorldViewImpl(
    			primaryStage,
    			dataAccessLayer.getWorld(),
    			dataAccessLayer.getPlayer(),
    			dataAccessLayer.getDataItems(),
    			dataAccessLayer.getWeapons());
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
	@Override
	public int getPlayerLifeTime() {
        return (int)(playerTimeLife / 1000.0d);
    }

	/**
	 * @return the amount of kills.
	 */
	@Override
	public int getEnemyKilledByPlayer() {
        return enemyKilled;
    }

	/**
	 * @return the amount of ammunitions bag picked.
	 */
	@Override
	public int getTotalAmmoBag() {
        return totalAmmoBag;
    }

	/**
	 * @return the amount of medikit picked.
	 */
	@Override
	public int getTotalMedikit() {
        return totalMedikit;
    }

	/**
	 * @return how many times the player has switched weapon.
	 */
	@Override
	public int getTotalWeaponsChanged() {
        return totalWeaponsChanged;
    }

	/**
	 * @return the amount of bullets fired by player.
	 */
	@Override
	public int getTotalAmmoShootedByPlayer() {
        return totalAmmoShootedByPlayer;
    }

	/**
	 * @return whether the player has picked or not the brief case.
	 */
	@Override
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
