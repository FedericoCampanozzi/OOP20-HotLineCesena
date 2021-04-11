package hotlinecesena.view;

import java.util.LinkedHashMap;
import java.util.Map;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.model.dataccesslayer.datastructure.DataWorldMap;
import hotlinecesena.view.loader.ImageType;
import hotlinecesena.view.loader.ProxyImage;
import hotlinecesena.view.loader.SceneType;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Pair;

public class WorldView {
    private static final String TITLE = "Hotline Cesena";
    private static final int SCALE = 100;
    
    private final Stage primaryStage;
    private BorderPane borderPane;
    private final GridPane gridPane = new GridPane();
    ProxyImage proxyImage = new ProxyImage();
    DataWorldMap world = JSONDataAccessLayer.getInstance().getWorld();
    Map<Pair<Integer, Integer>, SymbolsType> worldMap = world.getWorldMap();

    private final Map<Pair<Integer, Integer>, ImageView> enemiesPos = new LinkedHashMap<>();
    private final Map<Pair<Integer, Integer>, ImageView> itemsPos = new LinkedHashMap<>();
    private final Map<Pair<Integer, Integer>, ImageView> obstaclesPos = new LinkedHashMap<>();
    private Pair<Pair<Integer, Integer>, ImageView> playersPos;

    public WorldView(final Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public final void start() {
        primaryStage.setTitle(TITLE);
        borderPane = new BorderPane();
        final Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        borderPane.setCenter(gridPane);

        this.worldMap.forEach((p, s) -> {
            final char c = s.getDecotification();
            final ImageView tile = new ImageView();
            switch(c) {
                case 'W':
                    tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.WALL));
                    break;
                case '_':
                    tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.GRASS));
                    break;
                default:
                    tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.FLOOR));
                    break;
            }
                
                final Translate trans = new Translate();
                tile.getTransforms().add(trans);
                tile.setFitHeight(1);
                tile.setFitWidth(1);
                gridPane.add(tile, 0, 0);
                trans.setX(p.getKey());
                trans.setY(p.getValue());
        });
        
        this.worldMap.forEach((p,s) -> {
            final char c = s.getDecotification();
            final ImageView tile = new ImageView();
            switch(c) {
                case 'M':
                    tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.MEDKIT));
                    this.itemsPos.put(p, tile);
                    break;
                case 'A':
                    tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.AMMO_PISTOL));
                    this.itemsPos.put(p, tile);
                    break;
                case 'O':
                    tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.BOX));
                    this.obstaclesPos.put(p, tile);
                    break;
            }
            
            final Translate trans = new Translate();
            tile.getTransforms().add(trans);
            tile.setFitHeight(1);
            tile.setFitWidth(1);
            gridPane.add(tile, 0, 0);
            trans.setX(p.getKey());
            trans.setY(p.getValue());
        });
        
        this.worldMap.forEach((p,s) -> {
            final char c = s.getDecotification();
            final ImageView tile = new ImageView();
            switch(c) {
                case 'P':
                    tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.PLAYER_PISTOL));
                    this.playersPos = new Pair<>(new Pair<>(p.getKey(), p.getValue()), tile);
                    System.out.println(new Pair<>(p.getKey(), p.getValue()));
                    System.out.println(JSONDataAccessLayer.getInstance().getPlayer().getPly().getPosition());
                    break;
                case 'E':
                    tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.ENEMY_1));
                    this.enemiesPos.put(p, tile);
                    break;
            }
            
            final Translate trans = new Translate();
            tile.getTransforms().add(trans);
            tile.setFitHeight(1);
            tile.setFitWidth(1);
            gridPane.add(tile, 0, 0);
            trans.setX(p.getKey());
            trans.setY(p.getValue());
        });
        
        primaryStage.setResizable(false);
        primaryStage.setWidth(1600);
        primaryStage.setHeight(900);
        primaryStage.setX(0);
        primaryStage.setY(0);
        borderPane.getCenter().setScaleX(SCALE);
        borderPane.getCenter().setScaleY(SCALE);
    }

    public GridPane getGridPane() {
        return gridPane;
    }

	public Map<Pair<Integer, Integer>, ImageView> getEnemiesPos() {
		return enemiesPos;
	}

	public Map<Pair<Integer, Integer>, ImageView> getItemsPos() {
		return itemsPos;
	}

	public Map<Pair<Integer, Integer>, ImageView> getObstaclesPos() {
		return obstaclesPos;
	}

	public Pair<Pair<Integer, Integer>, ImageView> getPlayersPos() {
		return playersPos;
	}

	public BorderPane getBorderPane() {
		return borderPane;
	}

	public Stage getStage() {
		return primaryStage;
	}
    
    
}