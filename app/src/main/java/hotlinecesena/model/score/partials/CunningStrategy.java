package hotlinecesena.model.score;

import com.google.common.eventbus.Subscribe;

import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.events.AttackPerformedEvent;
import hotlinecesena.model.events.DamageReceivedEvent;

public final class CunningPartial extends AbstractPartial {

    private static final double MINIMUM = 0.1;
    private int attacksPerformed = 0;
    private int hits = 0;

    protected CunningPartial() {
        super(PartialType.CUNNING);
        this.getGameMaster().getPlayer().getPly().register(this);
        this.getGameMaster().getEnemy().getEnemies().forEach(e -> e.register(this));
    }

    @Override
    protected double formula() {
        return hits / (attacksPerformed == 0 ? MINIMUM : attacksPerformed);
    }

    @Subscribe
    private void handleAttackEvent(final AttackPerformedEvent<Player> e) {
        if (e.getSource() instanceof Player) {
            attacksPerformed++;
        }
    }

    @Subscribe
    private void handleDamageReceivedEvent(final DamageReceivedEvent<Enemy> e) {
        if (e.getSource() instanceof Enemy) {
            hits++;
        }
    }
}
