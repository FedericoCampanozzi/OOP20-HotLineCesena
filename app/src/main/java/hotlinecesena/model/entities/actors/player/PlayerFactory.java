package hotlinecesena.model.entities.actors.player;

import java.util.Collection;
import java.util.Map;

import hotlinecesena.model.dataccesslayer.datastructure.DataPhysicsCollision.Obstacle;
import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.entities.items.ItemsType;
import hotlinecesena.model.entities.items.Weapon;
import hotlinecesena.model.inventory.Inventory;
import javafx.geometry.Point2D;

/**
 *
 * Models a factory to create {@link Player} instances.
 *
 */
public interface PlayerFactory {

    /**
     * Creates a {@code Player} with custom position and
     * rotation angle.
     * @param position the starting position of the Player.
     * @param angle the starting angle that the Player will face.
     * @param obstacles obstacles on the game map.
     * @param enemies enemies on the game map.
     * @param itemsOnMap items on the game map.
     * @param weaponsOnMap weapons on the game map.
     * @return a {@code Player} with the aforementioned custom values.
     */
    Player createPlayer(Point2D position, double angle, Collection<Obstacle> obstacles,
            Collection<Enemy> enemies, Map<Point2D, ItemsType> itemsOnMap,
            Map<Point2D, Weapon> weaponsOnMap);

    /**
     * Creates a {@code Player} with custom position,
     * rotation angle and inventory.
     * @param position the starting position of the Player.
     * @param angle the starting angle that the Player will face.
     * @param inventory the inventory that the Player will use.
     * @param obstacles obstacles on the game map.
     * @param enemies enemies on the game map.
     * @param itemsOnMap items on the game map.
     * @param weaponsOnMap weapons on the game map.
     * @return a {@code Player} with the aforementioned custom values.
     */
    Player createWithCustomInventory(Point2D position, double angle, Inventory inventory,
            Collection<Obstacle> obstacles, Collection<Enemy> enemies, Map<Point2D, ItemsType> itemsOnMap,
            Map<Point2D, Weapon> weaponsOnMap);
}
