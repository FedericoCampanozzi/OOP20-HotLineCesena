package hotlinecesena.controller;

import java.io.IOException;

import hotlinecesena.controller.HUD.PlayerStatsController;
import hotlinecesena.controller.entities.ProjectileController;
import hotlinecesena.controller.entities.player.PlayerController;
import hotlinecesena.controller.entities.player.PlayerControllerFactoryFX;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.view.WorldView;
import hotlinecesena.view.entities.SpriteImpl;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class WorldController{
	
	private final WorldView view;
	private Stage primaryStage;
	private final GameLoopController gameLoopController = new GameLoopController();
    private PlayerController playerController;
    private ProjectileController projectileController;
    private PlayerStatsController playerStatsController;
	
	public WorldController(Stage primaryStage) throws IOException{
		this.primaryStage = primaryStage;
		view = new WorldView(this.primaryStage);
		view.start();
		
		playerStatsController = new PlayerStatsController(view);
		FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(JSONDataAccessLayer.getInstance().getGuiPath().getPath("PlayerStatsView.fxml")));
		loader.setController(playerStatsController);
		view.getBorderPane().setBottom(loader.load());
		gameLoopController.addMethodToUpdate(playerStatsController.getUpdateMethod());
		
		playerController = new PlayerControllerFactoryFX(primaryStage.getScene(), view.getGridPane())
                .createPlayerController(new SpriteImpl(view.getPlayersPos().getValue()));
        gameLoopController.addMethodToUpdate(playerController.getUpdateMethod());
        
        projectileController = new ProjectileController(view);
        gameLoopController.addMethodToUpdate(projectileController.getUpdateMethod());

		gameLoopController.loop();
	}
	
}
