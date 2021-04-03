package hotlinecesena.model.score;

public class AccuracyPartial extends AbstractPartial implements PartialScore {

    private static final String NAME = "Accuracy";
    private static final int POINTS = 1500;
    private int attacksPerformed = 0;
    private int hits = 0;

    protected AccuracyPartial() {
        super(NAME, POINTS);
    }

    @Override
    protected int formula() {
        return hits / attacksPerformed;
    }

//  @Subscribe
//  private void handleAttackEvent(AttackPerformedEvent e) {
//      if (e.getSource() == player) {
//          this.attacksPerformed++;
//      }
//  }
  
//  @Subscribe
//  private void handleDamageReceivedEvent(DamageReceivedEvent e) {
//      if (getWorld().getEnemies().contains(e.getSource()) {
//          this.hits++;
//      }
//  }
}
