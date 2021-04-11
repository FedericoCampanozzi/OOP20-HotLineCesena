package hotlinecesena.controller.entities.enemy;

import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.view.entities.Sprite;

/**
 * Implements the {@code EnemyControllerFactory}.
 */
public final class EnemyControllerFactoryImpl implements EnemyControllerFactory {

    @Override
    public EnemyController getEnemyController(final Enemy enemy, final Sprite img, final Player target) {
        return new EnemyController(enemy, img, target);
    }

}
