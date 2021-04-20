package hotlinecesena.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.eventbus.Subscribe;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.model.dataccesslayer.datastructure.DataWorldMap;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.entities.items.ItemsType;
import hotlinecesena.model.entities.items.WeaponType;
import hotlinecesena.model.events.ItemPickUpEvent;
import hotlinecesena.model.events.Subscriber;
import hotlinecesena.model.events.WeaponPickUpEvent;
import hotlinecesena.utilities.Utilities;
import hotlinecesena.view.entities.Sprite;
import hotlinecesena.view.entities.SpriteImpl;
import hotlinecesena.view.loader.ImageLoader;
import hotlinecesena.view.loader.ImageType;
import hotlinecesena.view.loader.ProxyImage;
import hotlinecesena.view.loader.SceneType;
import javafx.geometry.Point2D;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Pair;

public class WorldView implements Subscriber {

    private static final int SCALE = 100;

    private final Stage primaryStage;
    private final StackPane stackPane = new StackPane();
    private final BorderPane borderPane = new BorderPane();
    private final GridPane gridPane = new GridPane();
    private final ImageLoader proxyImage = new ProxyImage();
    private final DataWorldMap world = JSONDataAccessLayer.getInstance().getWorld();
    private final Player player = JSONDataAccessLayer.getInstance().getPlayer().getPly();
    private final Map<Pair<Integer, Integer>, SymbolsType> worldMap = world.getWorldMap();
    private final List<Sprite> enemiesSprite = new ArrayList<>();

    private final Map<Point2D, ImageView> enemiesPos = new LinkedHashMap<>();
    private final Map<Point2D, ImageView> itemsPos = new LinkedHashMap<>();
    private final Map<Point2D, ImageView> obstaclesPos = new LinkedHashMap<>();
    private Pair<Point2D, ImageView> playersPos;

    public WorldView(final Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public final void start() {
        player.register(this);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setResizable(false);
        stackPane.getChildren().add(borderPane);
        final Scene scene = new Scene(stackPane);
        scene.setCursor(new ImageCursor(proxyImage.getImage(SceneType.MENU, ImageType.SCOPE)));
        primaryStage.setScene(scene);
        borderPane.setCenter(gridPane);

        worldMap.forEach((p, s) -> {
            final ImageView tile = new ImageView();
            final Point2D point = Utilities.convertPairToPoint2D(p);
            switch (s) {
            case WALL:
                tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.WALL));
                break;
            case VOID:
                tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.GRASS));
                break;
            default:
                tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.FLOOR));
                break;
            }
            this.addTileToMap(tile, point);
        });

        worldMap.forEach((p, s) -> {
            final ImageView tile = new ImageView();
            final Point2D point = Utilities.convertPairToPoint2D(p);
            switch (s) {
            case ITEM:
                tile.setImage(this.pickItemImage(
                        JSONDataAccessLayer.getInstance().getDataItems().getItems().get(point))
                        );
                itemsPos.put(point, tile);
                break;
            case KEY_ITEM:
                tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.MONEY_BAG));
                itemsPos.put(point, tile);
                break;
            case WEAPONS:
                tile.setImage(this.pickWeaponImage(
                        JSONDataAccessLayer.getInstance().getWeapons().getWeapons().get(point)
                        .getWeaponType())
                        );
                itemsPos.put(point, tile);
                break;
            case OBSTACOLES:
                tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.BOX));
                obstaclesPos.put(point, tile);
                break;
            default:
                break;
            }
            this.addTileToMap(tile, point);
        });

        worldMap.forEach((p, s) -> {
            final ImageView tile = new ImageView();
            final Point2D point = Utilities.convertPairToPoint2D(p);
            switch (s) {
            case PLAYER:
                tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.PLAYER_RIFLE));
                playersPos = new Pair<>(point, tile);
                break;
            case ENEMY:
                tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.ENEMY_1));
                enemiesPos.put(point, tile);
                break;
            default:
                break;
            }
            this.addTileToMap(tile, point);
        });

        enemiesPos.forEach((p, i) -> {
            enemiesSprite.add(new SpriteImpl(i));
        });

        borderPane.getCenter().setScaleX(SCALE);
        borderPane.getCenter().setScaleY(SCALE);

        if (JSONDataAccessLayer.getInstance().getSettings().getFullScreen()) {
            primaryStage.setFullScreen(true);
        }
        else {
            primaryStage.setFullScreen(false);
            primaryStage.setWidth(JSONDataAccessLayer.getInstance().getSettings().getMonitorX());
            primaryStage.setHeight(JSONDataAccessLayer.getInstance().getSettings().getMonitorY());
            primaryStage.centerOnScreen();
        }
    }

    private void addTileToMap(final ImageView tile, final Point2D point) {
        final Translate trans = new Translate();
        tile.getTransforms().add(trans);
        tile.setFitHeight(1);
        tile.setFitWidth(1);
        gridPane.add(tile, 0, 0);
        trans.setX(point.getX());
        trans.setY(point.getY());
    }

    private Image pickItemImage(final ItemsType type) {
        Image image = proxyImage.getImage(SceneType.GAME, ImageType.BLANK);
        switch (type) {
        case MEDIKIT:
            image = proxyImage.getImage(SceneType.GAME, ImageType.MEDKIT);
            break;
        case AMMO_BAG:
            image = proxyImage.getImage(SceneType.GAME, ImageType.AMMO_BOX);
            break;
        default:
            break;
        }
        return image;
    }

    private Image pickWeaponImage(final WeaponType type) {
        Image image = proxyImage.getImage(SceneType.GAME, ImageType.BLANK);
        switch (type) {
        case PISTOL:
            image = proxyImage.getImage(SceneType.MENU, ImageType.PISTOL);
            break;
        case RIFLE:
            image = proxyImage.getImage(SceneType.GAME, ImageType.RIFLE);
            break;
        case SHOTGUN:
            image = proxyImage.getImage(SceneType.GAME, ImageType.SHOTGUN);
            break;
        default:
            break;
        }
        return image;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public Map<Point2D, ImageView> getEnemiesPos() {
        return enemiesPos;
    }

    public Map<Point2D, ImageView> getItemsPos() {
        return itemsPos;
    }

    public Map<Point2D, ImageView> getObstaclesPos() {
        return obstaclesPos;
    }

    public Pair<Point2D, ImageView> getPlayersPos() {
        return playersPos;
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    public Stage getStage() {
        return primaryStage;
    }

    public void updateResolution(final int width, final int height) {
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
    }

    public List<Sprite> getEnemiesSprite() {
        return enemiesSprite;
    }

    @Subscribe
    private void onItemPickUP(final ItemPickUpEvent e) {
        this.getItemsPos().get(e.getItemPosition())
        .setImage(proxyImage.getImage(SceneType.GAME, ImageType.BLANK));
    }

    @Subscribe
    private void onWeaponPickUP(final WeaponPickUpEvent e) {
        Image image = proxyImage.getImage(SceneType.GAME, ImageType.BLANK);
        if (e.getOldWeapon().isPresent()) {
            image = this.pickWeaponImage(e.getOldWeapon().get());
        }
        this.getItemsPos().get(e.getItemPosition())
        .setImage(image);
    }
}