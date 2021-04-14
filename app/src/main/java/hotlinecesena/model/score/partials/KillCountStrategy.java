package hotlinecesena.model.score.partials;

import com.google.common.eventbus.Subscribe;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.events.DeathEvent;
import hotlinecesena.model.events.Subscriber;

/**
 * Implements the "Kill count" algorithm: players are
 * awarded points based on how many enemies are killed.
 */
public final class KillCountStrategy implements PartialStrategy, Subscriber {

    private final int basePoints;
    private int killCount = 0;

    /**
     * Instantiates a new KillCountStrategy.
     * @param basePoints starting points for this algorithm.
     */
    public KillCountStrategy(final int basePoints) {
        this.basePoints = basePoints;
        JSONDataAccessLayer.getInstance().getEnemy().getEnemies().forEach(e -> e.register(this));
    }

    @Override
    public int applyFormula() {
        return basePoints * killCount;
    }

    @Override
    public int getRelevantFactor() {
        return killCount;
    }

    @Subscribe
    private void handleDeathEvent(final DeathEvent<Enemy> e) {
        killCount++;
    }
}
