package hotlinecesena.model.score;

import com.google.common.eventbus.Subscribe;

import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.events.DeathEvent;

public final class KillCountPartial extends AbstractPartial {

    private static final String NAME = "Kills";
    private static final int POINTS = 100;
    private int killCount = 0;

    protected KillCountPartial() {
        super(NAME, POINTS);
        this.getGameMaster().getEnemy().getEnemies().forEach(e -> e.register(this));
    }

    @Override
    protected int formula() {
        return killCount;
    }

    @Subscribe
    private void handleDeathEvent(final DeathEvent<Enemy> e) {
        killCount++;
    }
}
