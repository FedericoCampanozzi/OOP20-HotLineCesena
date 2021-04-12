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
import hotlinecesena.view.loader.ImageType;
import hotlinecesena.view.loader.ProxyImage;
import hotlinecesena.view.loader.SceneType;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Pair;

public class WorldView implements Subscriber {
    private static final String TITLE = "Hotline Cesena";
    private static final int SCALE = 100;
    
    private final Stage primaryStage;
    private BorderPane borderPane;
    private final GridPane gridPane = new GridPane();
    ProxyImage proxyImage = new ProxyImage();
    DataWorldMap world = JSONDataAccessLayer.getInstance().getWorld();
    final Player player = JSONDataAccessLayer.getInstance().getPlayer().getPly();
    Map<Pair<Integer, Integer>, SymbolsType> worldMap = world.getWorldMap();
    List<Sprite> enemiesSprite = new ArrayList<>();
    List<Sprite> itemsSprite = new ArrayList<>();

    private final Map<Pair<Integer, Integer>, ImageView> enemiesPos = new LinkedHashMap<>();
    private final Map<Pair<Integer, Integer>, ImageView> itemsPos = new LinkedHashMap<>();
    private final Map<Pair<Integer, Integer>, ImageView> obstaclesPos = new LinkedHashMap<>();
    private Pair<Pair<Integer, Integer>, ImageView> playersPos;

    public WorldView(final Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public final void start() {
        this.player.register(this);
        primaryStage.setTitle(TITLE);
        borderPane = new BorderPane();
        final Scene scene = new Scene(borderPane);
        scene.setCursor(new ImageCursor(this.proxyImage.getImage(SceneType.MENU, ImageType.SCOPE)));
        primaryStage.setScene(scene);
        borderPane.setCenter(gridPane);

        this.worldMap.forEach((p, s) -> {
            final ImageView tile = new ImageView();
            switch(s) {
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
                
                final Translate trans = new Translate();
                tile.getTransforms().add(trans);
                tile.setFitHeight(1);
                tile.setFitWidth(1);
                gridPane.add(tile, 0, 0);
                trans.setX(p.getKey());
                trans.setY(p.getValue());
        });
        
        this.worldMap.forEach((p,s) -> {
            final ImageView tile = new ImageView();
            switch(s) {
                case ITEM:
                	if (JSONDataAccessLayer.getInstance().getDataItems().getItems().get(Utilities.convertPairToPoint2D(p)).equals(ItemsType.MEDIKIT)) {
                		tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.MEDKIT));
                        this.itemsPos.put(p, tile);
                	}
                	else if (JSONDataAccessLayer.getInstance().getDataItems().getItems().get(Utilities.convertPairToPoint2D(p)).equals(ItemsType.AMMO_BAG)) {
                		tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.AMMO_BOX));
                        this.itemsPos.put(p, tile);
					}
                    break;
                case OBSTACOLES:
                    tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.BOX));
                    this.obstaclesPos.put(p, tile);
                    break;
                case WEAPONS:
                	if (JSONDataAccessLayer.getInstance().getWeapons().getWeapons().get(Utilities.convertPairToPoint2D(p)).getWeaponType() == (WeaponType.PISTOL)) {
                		tile.setImage(proxyImage.getImage(SceneType.MENU, ImageType.PISTOL));
                        this.itemsPos.put(p, tile);
                	}
                	else if (JSONDataAccessLayer.getInstance().getWeapons().getWeapons().get(Utilities.convertPairToPoint2D(p)).getWeaponType() == (WeaponType.RIFLE)) {
                		tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.RIFLE));
                        this.itemsPos.put(p, tile);
                	}
                	else if (JSONDataAccessLayer.getInstance().getWeapons().getWeapons().get(Utilities.convertPairToPoint2D(p)).getWeaponType() == (WeaponType.SHOTGUN)) {
                		tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.SHOTGUN));
                        this.itemsPos.put(p, tile);
                	}
                	break;
			default:
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
            final ImageView tile = new ImageView();
            switch(s) {
                case PLAYER:
                    tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.PLAYER_RIFLE));
                    this.playersPos = new Pair<>(new Pair<>(p.getKey(), p.getValue()), tile);
                    System.out.println(new Pair<>(p.getKey(), p.getValue()));
                    System.out.println(JSONDataAccessLayer.getInstance().getPlayer().getPly().getPosition());
                    break;
                case ENEMY:
                    tile.setImage(proxyImage.getImage(SceneType.GAME, ImageType.ENEMY_1));
                    this.enemiesPos.put(p, tile);
                    break;
			default:
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
        
        enemiesPos.forEach((p, i) -> {
            enemiesSprite.add(new SpriteImpl(i));
        });
        
        primaryStage.setResizable(false);
        primaryStage.setWidth(1600);
        primaryStage.setHeight(900);
        primaryStage.setX(0);
        primaryStage.setY(0);
        borderPane.getCenter().setScaleX(SCALE);
        borderPane.getCenter().setScaleY(SCALE);
        
        System.out.println(JSONDataAccessLayer.getInstance().getDataItems().getItems());
        System.out.println(itemsPos.keySet());
        System.out.println("X" + JSONDataAccessLayer.getInstance().getWorld().getMaxX());
        System.out.println("x" + JSONDataAccessLayer.getInstance().getWorld().getMinX());
        System.out.println("Y" + JSONDataAccessLayer.getInstance().getWorld().getMaxY());
        System.out.println("y" + JSONDataAccessLayer.getInstance().getWorld().getMinY());
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

	public List<Sprite> getEnemiesSprite() {
		return enemiesSprite;
	}
	
	@Subscribe
	private void onItemPickUP(final ItemPickUpEvent<Player> e) {
	    this.getItemsPos().get(new Pair<>((int) e.getItemPosition().getX(), (int) e.getItemPosition().getY()))
	    .setImage(this.proxyImage.getImage(SceneType.GAME, ImageType.BLANK));
	}
	
	@Subscribe
	private void onWeaponPickUP(final WeaponPickUpEvent<Player> e) {
	    this.getItemsPos().get(new Pair<>((int) e.getItemPosition().getX(), (int) e.getItemPosition().getY()))
            .setImage(this.proxyImage.getImage(SceneType.GAME, ImageType.BLANK)); 
	}
    
}