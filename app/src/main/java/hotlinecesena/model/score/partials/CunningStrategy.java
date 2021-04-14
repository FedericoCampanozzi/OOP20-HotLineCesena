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
    private static final double BIG_BRAIN = 0.5;
    private final int basePoints;
    private int attacksPerformed = 0;
    private int hits = 0;

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
        return (int) (basePoints * hits / (attacksPerformed == 0 ? BIG_BRAIN : attacksPerformed));
    }

    @Override
    public int getRelevantFactor() {
        return (int) (Math.round(this.applyFormula() * 100.0));
    }

    /*
     * Counts all bullets shot by the player.
     */
    @Subscribe
    private void handleAttackEvent(final AttackPerformedEvent<Player> e) {
        if (e.getSourceInterfaces().contains(Player.class)) {
            attacksPerformed++;
        }
    }

    /*
     * Counts all instances of enemies receiving damage.
     */
    @Subscribe
    private void handleDamageReceivedEvent(final DamageReceivedEvent<Enemy> e) {
        if (e.getSourceInterfaces().contains(Enemy.class)) {
            hits++;
        }
    }
}
