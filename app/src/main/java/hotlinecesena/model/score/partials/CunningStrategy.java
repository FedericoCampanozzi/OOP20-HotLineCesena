package hotlinecesena.model.score.partials;

import com.google.common.eventbus.Subscribe;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.events.AttackPerformedEvent;
import hotlinecesena.model.events.DamageReceivedEvent;
import hotlinecesena.model.events.Subscriber;

/**
 * Implements the "Cunning" algorithm: it rewards players
 * who are skilled enough to win a game by shooting the
 * least amount of bullets (or even none at all) or by letting
 * enemies kill each other through exploitation of friendly fire.
 */
public final class CunningStrategy implements PartialStrategy, Subscriber {

    /**
     * Value to be used if the player manages to finish
     * the game without shooting a single bullet.
     */
    private static final float BIG_BRAIN = 0.75f;
    private final int basePoints;
    private float attacksPerformed = 0.0f;
    private float hits = 0.0f;

    /**
     * Instantiates a new CunningStrategy.
     * @param basePoints starting points for this algorithm.
     */
    public CunningStrategy(final int basePoints) {
        this.basePoints = basePoints;
        JSONDataAccessLayer.getInstance().getPlayer().getPly().register(this);
        JSONDataAccessLayer.getInstance().getEnemy().getEnemies().forEach(e -> e.register(this));
    }

    @Override
    public int applyFormula() {
        return (int) (basePoints * this.internalFormula());
    }

    @Override
    public int getRelevantFactor() {
        return (Math.round(this.internalFormula() * 100.0f));
    }

    private float internalFormula() {
        return hits / (attacksPerformed == 0 ? BIG_BRAIN : attacksPerformed);
    }

    /*
     * Counts all bullets shot by the player.
     */
    @Subscribe
    private void handleAttackEvent(final AttackPerformedEvent e) {
        if (e.getSourceInterfaces().contains(Player.class)) {
            attacksPerformed++;
        }
    }

    /*
     * Counts all instances of enemies receiving damage.
     */
    @Subscribe
    private void handleDamageReceivedEvent(final DamageReceivedEvent e) {
        if (e.getSourceInterfaces().contains(Enemy.class)) {
            hits++;
        }
    }
}
