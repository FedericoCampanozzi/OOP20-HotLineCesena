package hotlinecesena.controller;

import hotlinecesena.view.WorldView;
import javafx.stage.Stage;

public class WorldController{
	
	private final WorldView view;
	private Stage primaryStage;
	
	public WorldController(Stage primaryStage){
		this.primaryStage = primaryStage;
		view = new WorldView(this.primaryStage);
		view.start();
	}
	
}
