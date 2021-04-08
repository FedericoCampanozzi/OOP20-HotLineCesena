package hotlinecesena.view;

import java.io.IOException;
import java.util.Map;

import hotlinecesena.controller.GameLoopController;
import hotlinecesena.controller.entities.player.PlayerController;
import hotlinecesena.controller.entities.player.PlayerControllerFactoryFX;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.model.dataccesslayer.datastructure.DataWorldMap;
import hotlinecesena.utilities.SceneSwapper;
import hotlinecesena.view.loader.ImageType;
import hotlinecesena.view.loader.ProxyImage;
import hotlinecesena.view.loader.SceneType;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class WorldView {
    private static final String TITLE = "Hotline Cesena";
    private static final int TILE_SIZE = JSONDataAccessLayer.getInstance().getSettings().getTileSize();
    private final Stage primaryStage;
    private BorderPane borderPane;
    private final GridPane gridPane = new GridPane();
    private final SceneSwapper sceneSwapper = new SceneSwapper();
    ProxyImage proxyImage = new ProxyImage();
    DataWorldMap world = JSONDataAccessLayer.getInstance().getWorld();
    Map<Pair<Integer, Integer>, SymbolsType> worldMap = world.getWorldMap();
    private final GameLoopController gc = new GameLoopController();
    //private SceneSwapper sceneSwapper = new SceneSwapper();

    public WorldView(final Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public final void start() {
        primaryStage.setTitle(TITLE);
        borderPane = new BorderPane();
        final Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setScene(scene);
        borderPane.setCenter(gridPane);

        final int rows = world.getMaxY() - world.getMinY() + 1;
        final int cols = world.getMaxX() - world.getMinX() + 1;
        for (int row = 0 ; row < rows ; row++) {
            for (int col = 0 ; col < cols ; col++) {
                pickImage(col, row);
            }
        }
        primaryStage.setResizable(false);
        primaryStage.setWidth(1600);
        primaryStage.setHeight(900);
        primaryStage.setX(0);
        primaryStage.setY(0);

        primaryStage.getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(final KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    try {
                        sceneSwapper.newStageWithScene("PauseView.fxml");
                    } catch (final IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        final PlayerController pc = new PlayerControllerFactoryFX(primaryStage.getScene(), gridPane).createPlayerController();
        gc.addMethodToUpdate(pc.getUpdateMethod());
        System.out.println("Obstacles: ");
        for (final var o : JSONDataAccessLayer.getInstance().getPhysics().getObstacles()) {
            System.out.println(o.getMinX() + "; " + o.getMinY());
        }
        System.out.println("Enemies: ");
        for (final var o : JSONDataAccessLayer.getInstance().getEnemy().getEnemies()) {
            System.out.println(o.getPosition());
        }
        gc.loop();
    }
    
    private void pickImage(final int col, final int row) {
    	final char c = worldMap.get(new Pair<Integer, Integer> (world.getMinX() + col, world.getMinY() + row)).getDecotification();
    	if (c == 'W' || c == '_' || c == '.') {
			createImage(c, col, row);
		}
    	else {
			blendImages(c, col, row);
		}
    }

    private void createImage(final char c, final int col, final int row) {
        final ImageView tile = new ImageView();
        switch (c) {
		case 'W':
			tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.WALL));
			break;
		case '_':
			tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.GRASS));
			break;
		case '.':
			tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.FLOOR));
			break;
		}
        tile.setFitHeight(TILE_SIZE);
        tile.setFitWidth(TILE_SIZE);
        gridPane.add(tile, col, row);
    }
    
    private void blendImages(final char symbol, final int col, final int row) {
    	final ImageView top = new ImageView();
    	final ImageView bottom = new ImageView(proxyImage.getImage(SceneType.GAME, ImageType.FLOOR));
    	switch (symbol) {
		case 'P':
			top.setImage(proxyImage.getImage(SceneType.GAME, ImageType.PLAYER));
			break;
		case 'E':
			top.setImage(proxyImage.getImage(SceneType.GAME, ImageType.ENEMY_1));
			break;
		case 'M':
			top.setImage(proxyImage.getImage(SceneType.GAME, ImageType.MEDKIT));
			break;
		case 'A':
			top.setImage(proxyImage.getImage(SceneType.GAME, ImageType.AMMO_PISTOL));
			break;
		case 'O':
			top.setImage(proxyImage.getImage(SceneType.GAME, ImageType.BOX));
			break;
		}
    	top.setFitHeight(TILE_SIZE);
    	top.setFitWidth(TILE_SIZE);
    	bottom.setFitHeight(TILE_SIZE);
        bottom.setFitWidth(TILE_SIZE);
    	top.setBlendMode(BlendMode.SRC_OVER);
    	Group blend = new Group(
    			bottom,
    			top
    	);
		gridPane.add(blend, col, row);
    }
}