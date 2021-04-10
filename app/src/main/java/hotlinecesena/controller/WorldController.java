package hotlinecesena.controller;

//import hotlinecesena.controller.HUD.PlayerStatsController;
import hotlinecesena.controller.entities.ProjectileController;
import hotlinecesena.controller.entities.player.PlayerController;
import hotlinecesena.controller.entities.player.PlayerControllerFactoryFX;
import hotlinecesena.view.WorldView;
import hotlinecesena.view.entities.SpriteImpl;
import javafx.stage.Stage;

public class WorldController{
	
	private final WorldView view;
	private Stage primaryStage;
	private final GameLoopController gameLoopController = new GameLoopController();
    private PlayerController playerController;
    private ProjectileController projectileController;
	
	public WorldController(Stage primaryStage){
		this.primaryStage = primaryStage;
		view = new WorldView(this.primaryStage);
		view.start();
		
		playerController = new PlayerControllerFactoryFX(primaryStage.getScene(), view.getGridPane())
                .createPlayerController(new SpriteImpl(view.getPlayersPos().getValue()));
        gameLoopController.addMethodToUpdate(playerController.getUpdateMethod());
        
        projectileController = new ProjectileController(view);
        gameLoopController.addMethodToUpdate(projectileController.getUpdateMethod());
		
        /*
		PlayerStatsController playerStatsController = new PlayerStatsController(view);
		gameLoopController.addMethodToUpdate(playerStatsController.getUpdateMethod());
		*/
		
		gameLoopController.loop();
	}
	
}
