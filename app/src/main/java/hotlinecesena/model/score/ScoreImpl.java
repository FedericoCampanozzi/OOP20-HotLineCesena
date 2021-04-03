package hotlinecesena.model.score;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.eventbus.Subscribe;

import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.events.AttackPerformedEvent;
import hotlinecesena.model.events.Subscriber;
import hotlinecesena.model.score.PartialScores.PartialType;

public class ScoreImpl implements Score, Subscriber {
    
    private static final double TIME_CEILING = 300;
    private double secondsElapsed = 0;
    private int attacksPerformed = 0;
    private int hits = 0;
    private int killCount = 0;

    @Override
    public Map<PartialType, Integer> getPartialScores() {
//        return new EnumMap<>(Map.of(
//                PartialScores.computeKillPartial(killCount, world.getEnemies().size(),
//                PartialScores.computeTimePartial((int)secondsElapsed, TIME_CEILING),
//                PartialScores.computeAccuracyPartial(hits, attacksPerformed))
//                ));
        return null;
    }

    @Override
    public int getTotalScore() {
        return this.getPartialScores().entrySet()
                .stream()
                .mapToInt(Entry::getValue)
                .sum();
    }

    @Override
    public void update(double timeElapsed) {
        this.secondsElapsed += timeElapsed;
    }
    
//   @Subscribe
//   private void handleDeathEvent(DeathEvent e) {
//       if (world.getEnemies().contains(e.getSource()) {
//           this.killCount++;
//       }
//   }

//    @Subscribe
//    private void handleAttackEvent(AttackPerformedEvent e) {
//        if (e.getSource() == player) {
//            this.attacksPerformed++;
//        }
//    }
    
//   @Subscribe
//   private void handleDamageReceivedEvent(DamageReceivedEvent e) {
//       if (world.getEnemies().contains(e.getSource()) {
//           this.hits++;
//       }
//   }
}
