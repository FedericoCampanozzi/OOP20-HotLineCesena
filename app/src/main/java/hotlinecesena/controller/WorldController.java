package hotlinecesena.controller;

import java.io.IOException;
import java.util.Optional;

import com.google.common.eventbus.Subscribe;

import hotlinecesena.controller.HUD.PlayerStatsController;
import hotlinecesena.controller.entities.ProjectileController;
import hotlinecesena.controller.entities.enemy.EnemyController;
import hotlinecesena.controller.entities.player.PlayerController;
import hotlinecesena.controller.entities.player.PlayerControllerFactoryFX;
import hotlinecesena.controller.menu.PauseController;
import hotlinecesena.controller.menu.RankingController;
import hotlinecesena.controller.mission.MissionBuilder;
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
import hotlinecesena.view.MiniMapView;
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

public class WorldController implements Subscriber {

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
    private final MiniMapView miniMapView;
    
    

    
    private double playerTimeLife = 0;
    private int totalAmmoBag = 0;
    private int totalMedikit = 0;
    private int totalWeaponsChanged = 0;
    private int totalAmmoShootedByPlayer = 0;
    private boolean pickBriefCase = false;
    private int enemyKilled;
    
    public int getPlayerLifeTime() {
		return (int)(this.playerTimeLife / 1000.0d);
	}
    
    public int getEnemyKilledByPlayer() {
		return enemyKilled;
	}
    
    public int getTotalAmmoBag() {
		return totalAmmoBag;
	}

	public int getTotalMedikit() {
		return totalMedikit;
	}

	public int getTotalWeaponsChanged() {
		return totalWeaponsChanged;
	}

	public int getTotalAmmoShootedByPlayer() {
		return totalAmmoShootedByPlayer;
	}

	public boolean isPickBriefCase() {
		return pickBriefCase;
	}

	@Subscribe
    private void addItemType(ItemPickUpEvent event) {
    	if(event.getItemType().equals(ItemsType.AMMO_BAG)) {
    		this.totalAmmoBag ++;
    	} else if(event.getItemType().equals(ItemsType.MEDIKIT)) {
    		this.totalMedikit ++;
    	} else if(event.getItemType().equals(ItemsType.BRIEFCASE)) {
    		this.pickBriefCase = true;
    	}
    }
    
    @Subscribe
    private void addChangedWeapons(WeaponPickUpEvent event) {
    	this.totalWeaponsChanged ++;
    }
    
    @Subscribe
    private void addAmmoShoot(AttackPerformedEvent event) {
    	if(event.getSourceInterfaces().contains(Player.class)) {
    		this.totalAmmoShootedByPlayer ++;
    	}
    }
    
    @Subscribe
    private void addAmmoShoot(DeathEvent event) {
    	if(event.getSourceInterfaces().contains(Enemy.class)) {
    		this.enemyKilled ++;    		
    	}
    }
    
    public WorldController(final Stage primaryStage, final AudioControllerImpl audioControllerImpl) throws IOException{
        
    	JSONDataAccessLayer.getInstance().getPlayer().getPly().register(this);
    	JSONDataAccessLayer.getInstance().getEnemy().getEnemies().forEach(itm -> itm.register(this));
    	
    	
    	this.primaryStage = primaryStage;
        this.audioControllerImpl = audioControllerImpl;
        this.audioControllerImpl.playMusic();
        new AudioEventController();
        view = new WorldView(this.primaryStage);
        view.start();
        missionController = new MissionBuilder(this)
        		.addAmmoMission(2, 6)
        		.addMedikitMission(3, 6)
        		.addChangeWeaponsMission(1, 3)
        		.addKeyObjectMission()
        		.addKillMission(1, 7)
        		.addSurviveMission(60, 240)
        		.build();
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

        score = new ScoreImpl(new PartialStrategyFactoryImpl());

        gameLoopController.addMethodToUpdate(d -> {
            if(missionController.missionPending().isEmpty() || JSONDataAccessLayer.getInstance().getPlayer().getPly().getActorStatus().equals(ActorStatus.DEAD)) {
                try {
                    audioControllerImpl.stopMusic();
                    gameLoopController.stop();
                    primaryStage.setWidth(800);
                    primaryStage.setHeight(600);
                    primaryStage.centerOnScreen();
                    sceneSwapper.swapScene(new RankingController(primaryStage, audioControllerImpl, score.getPartialScores(), score.getTotalScore()), "RankingView.fxml", primaryStage);
                } catch (final IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            
            
            
            playerTimeLife += d;
            
            
        });

        gameLoopController.addMethodToUpdate(d -> {
            view.getBorderPane().setOnKeyReleased(e -> {
                if (e.getCode() == KeyCode.ESCAPE) {
                    try {
                        audioControllerImpl.stopMusic();
                        gameLoopController.stop();
                        final Stage stage = new Stage();
                        stage.show();
                        sceneSwapper.swapScene(new PauseController(stage, Optional.of(primaryStage), audioControllerImpl, gameLoopController), "PauseView.fxml", stage);
                    } catch (final IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });
        });
        
        miniMapView = new MiniMapView(view.getBorderPane());
        gameLoopController.addMethodToUpdate(miniMapView.getUpdateMethod());
        
        gameLoopController.loop();
    }
}