package hotlinecesena.controller.entities.enemy;

import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.view.entities.Sprite;

/**
 * Models a factory that creates {@code EnemyController}s.
 */
public interface EnemyControllerFactory {

    /**
     * Returns a new enemy controller.
     * @param enemy the enemy that will be controlled
     * @param img the enemy sprite
     * @param player the target of the enemy
     * @return a new {@code EnemyController}
     */
    EnemyController getEnemyController(Enemy enemy, Sprite img, Player player);

}
