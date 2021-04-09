package hotlinecesena.view;

import java.util.LinkedHashMap;
import java.util.Map;
import hotlinecesena.controller.GameLoopController;
import hotlinecesena.controller.entities.player.PlayerController;
import hotlinecesena.controller.entities.player.PlayerControllerFactoryFX;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.model.dataccesslayer.datastructure.DataWorldMap;
import hotlinecesena.view.entities.SpriteImpl;
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
    private static final int TILE_SIZE = JSONDataAccessLayer.getInstance().getSettings().getTileSize();
    private final Stage primaryStage;
    private BorderPane borderPane;
    private final GridPane gridPane = new GridPane();
    ProxyImage proxyImage = new ProxyImage();
    DataWorldMap world = JSONDataAccessLayer.getInstance().getWorld();
    Map<Pair<Integer, Integer>, SymbolsType> worldMap = world.getWorldMap();
    private final GameLoopController gc = new GameLoopController();
    private PlayerController pc;

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
        final Scene scene = new Scene(borderPane, 600, 400);
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
                tile.setFitHeight(TILE_SIZE);
                tile.setFitWidth(TILE_SIZE);
                gridPane.add(tile, 0, 0);
                trans.setX(p.getKey() * TILE_SIZE);
                trans.setY(p.getValue() * TILE_SIZE);
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
            tile.setFitHeight(TILE_SIZE);
            tile.setFitWidth(TILE_SIZE);
            gridPane.add(tile, 0, 0);
            trans.setX(p.getKey() * TILE_SIZE);
            trans.setY(p.getValue() * TILE_SIZE);
        });
        
        this.worldMap.forEach((p,s) -> {
            final char c = s.getDecotification();
            final ImageView tile = new ImageView();
            switch(c) {
                case 'P':
                    tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.PLAYER));
                    this.playersPos = new Pair<>(new Pair<>(p.getKey() * TILE_SIZE, p.getValue() * TILE_SIZE), tile);
                    System.out.println(new Pair<>(p.getKey() * TILE_SIZE, p.getValue() * TILE_SIZE));
                    System.out.println(JSONDataAccessLayer.getInstance().getPlayer().getPly().getPosition());
                    break;
                case 'E':
                    tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.ENEMY_1));
                    this.enemiesPos.put(p, tile);
                    break;
            }
            
            final Translate trans = new Translate();
            tile.getTransforms().add(trans);
            tile.setFitHeight(TILE_SIZE);
            tile.setFitWidth(TILE_SIZE);
            gridPane.add(tile, 0, 0);
            trans.setX(p.getKey() * TILE_SIZE);
            trans.setY(p.getValue() * TILE_SIZE);
        });
        
        primaryStage.setResizable(false);
        primaryStage.setWidth(1600);
        primaryStage.setHeight(900);
        primaryStage.setX(0);
        primaryStage.setY(0);
        
        pc = new PlayerControllerFactoryFX(primaryStage.getScene(), gridPane)
                .createPlayerController(new SpriteImpl(this.playersPos.getValue()));
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

    public GridPane getGridPane() {
        return gridPane;
    }
}