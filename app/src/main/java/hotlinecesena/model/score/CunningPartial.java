package hotlinecesena.model.score;

import com.google.common.eventbus.Subscribe;

import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.events.AttackPerformedEvent;
import hotlinecesena.model.events.DamageReceivedEvent;

public final class CunningPartial extends AbstractPartial {

    private static final String NAME = "Cunning";
    private static final int POINTS = 500;
    private int attacksPerformed = 0;
    private int hits = 0;

    protected CunningPartial() {
        super(NAME, POINTS);
    }

    @Override
    protected int formula() {
        return hits / attacksPerformed;
    }

    @Subscribe
    private void handleAttackEvent(final AttackPerformedEvent<Player> e) {
        attacksPerformed++;
    }

    @Subscribe
    private void handleDamageReceivedEvent(final DamageReceivedEvent<Enemy> e) {
        hits++;
    }
}
