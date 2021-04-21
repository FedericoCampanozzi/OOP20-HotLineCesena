package hotlinecesena.view;

import java.util.List;
import java.util.Map;

import hotlinecesena.model.events.Subscriber;
import hotlinecesena.view.entities.Sprite;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;

/**
 * Scene of the game world: map, items, enemies and player.
 */
public interface WorldView extends Subscriber{

	/**
	 * Start all the initializations of the scene.
	 */
	void start();

	/**
	 * @return the grid pane containing the world map
	 */
	GridPane getGridPane();

	/**
	 * @return the positions of all the enemies in the map.
	 */
	Map<Point2D, ImageView> getEnemiesPos();

	/**
	 * @return the positions of all the items in the map.
	 */
	Map<Point2D, ImageView> getItemsPos();

	/**
	 * @return the positions of all the obstacles in the map.
	 */
	Map<Point2D, ImageView> getObstaclesPos();

	/**
	 * @return the position of the player in the map.
	 */
	Pair<Point2D, ImageView> getPlayersPos();

	/**
	 * @return the most background pane, useful for adding overlay panes.
	 */
	StackPane getStackPane();

	/**
	 * @return a list of all the enemy sprites.
	 */
	List<Sprite> getEnemiesSprite();

}