package hotlinecesena.model.score;

import com.google.common.eventbus.Subscribe;

import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.events.DeathEvent;

public final class KillCountPartial extends AbstractPartial {

    private int killCount = 0;

    protected KillCountPartial() {
        super(PartialType.KILLS);
        this.getGameMaster().getEnemy().getEnemies().forEach(e -> e.register(this));
    }

    @Override
    protected double formula() {
        return killCount;
    }

    @Subscribe
    private void handleDeathEvent(final DeathEvent<Enemy> e) {
        if (e.getSource() instanceof Enemy) {
            killCount++;
        }
    }
}
