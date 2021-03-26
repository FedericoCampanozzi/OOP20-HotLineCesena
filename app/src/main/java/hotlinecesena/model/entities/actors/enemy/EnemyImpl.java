package hotlinecesena.model.entities.actors.enemy;

import hotlinecesena.model.entities.actors.AbstractActor;
import hotlinecesena.model.inventory.Inventory;
import hotlinecesena.model.entities.actors.enemy.ai.AI;
import hotlinecesena.model.entities.actors.enemy.ai.AIImpl;
import javafx.geometry.Point2D;

public class EnemyImpl extends AbstractActor implements Enemy {
    
    private static final double ENEMY_INITIAL_ANGLE = 0;
    private static final double ENEMY_SPEED = 5;
    private static final int ENEMY_MAX_HEALTH = 1;

    private final EnemyType enemyType;
    private final AI enemyAI;
    
    protected EnemyImpl(Point2D pos, Inventory inv, EnemyType type) {
        super(pos, ENEMY_INITIAL_ANGLE, ENEMY_SPEED, ENEMY_MAX_HEALTH, inv);
        this.enemyType = type;
        this.enemyAI = new AIImpl(this.enemyType);
    }

    @Override
    public AI getAI() {
        return this.enemyAI;
    }
}
