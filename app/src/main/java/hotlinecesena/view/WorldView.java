package hotlinecesena.view;

import java.io.IOException;
import java.util.Map;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.model.dataccesslayer.datastructure.DataWorldMap;
import hotlinecesena.utilities.SceneSwapper;
import hotlinecesena.view.loader.ImageType;
import hotlinecesena.view.loader.ProxyImage;
import hotlinecesena.view.loader.SceneType;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class WorldView {
	private static final String TITLE = "Hotline Cesena";
	private static final int TILE_SIZE = 16;
	private final Stage primaryStage;
	private BorderPane borderPane;
	private GridPane gridPane = new GridPane();
	private SceneSwapper sceneSwapper = new SceneSwapper();
	ProxyImage proxyImage = new ProxyImage();
	DataWorldMap world = JSONDataAccessLayer.getInstance().getWorld();
    Map<Pair<Integer, Integer>, SymbolsType> worldMap = world.getWorldMap();
	//private SceneSwapper sceneSwapper = new SceneSwapper();
	
	public WorldView(final Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public final void start() {
		this.primaryStage.setTitle(TITLE);
		this.borderPane = new BorderPane();
		final Scene scene = new Scene(this.borderPane, 600, 400);
		primaryStage.setScene(scene);
		borderPane.setCenter(gridPane);
		
		int rows = world.getMaxY() - world.getMinY() + 1;
    	int cols = world.getMaxX() - world.getMinX() + 1;
        for (int row = 0 ; row < rows ; row++) {
            for (int col = 0 ; col < cols ; col++) {
            	ImageView tile = createImage(col, row);
            	tile.setFitHeight(TILE_SIZE);
            	tile.setFitWidth(TILE_SIZE);
            	gridPane.add(tile, col, row);
            }
        }
        primaryStage.setResizable(false);
        primaryStage.setWidth(cols * TILE_SIZE);
        primaryStage.setHeight(rows * TILE_SIZE);
        primaryStage.setX(0);
        primaryStage.setY(0);
        
        primaryStage.getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ESCAPE) {
					try {
						sceneSwapper.newStageWithScene("PauseView.fxml");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	private ImageView createImage(int col, int row) {
		ImageView tile = new ImageView();
        char c = worldMap.get(new Pair<Integer, Integer> (world.getMinX() + col, world.getMinY() + row)).getDecotification();
        tile.setImage((c == 'W') ? proxyImage.getImage(SceneType.GAME, ImageType.WALL)
                : (c == '_') ? proxyImage.getImage(SceneType.GAME, ImageType.GRASS)
                : (c == 'P') ? proxyImage.getImage(SceneType.GAME, ImageType.PLAYER)
                : (c == 'E') ? proxyImage.getImage(SceneType.GAME, ImageType.ENEMY_1)
                : (c == 'M') ? proxyImage.getImage(SceneType.GAME, ImageType.MEDKIT)
                : (c == 'A') ? proxyImage.getImage(SceneType.GAME, ImageType.AMMO_PISTOL)
                : (c == 'O') ? proxyImage.getImage(SceneType.GAME, ImageType.BOX)
                : proxyImage.getImage(SceneType.GAME, ImageType.FLOOR));
		return tile;
	}
}
