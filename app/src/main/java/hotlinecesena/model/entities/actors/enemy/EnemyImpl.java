package hotlinecesena.model.entities.actors.enemy;

import java.util.Set;

import hotlinecesena.model.entities.actors.AbstractActor;
import hotlinecesena.model.inventory.Inventory;
import hotlinecesena.model.entities.actors.enemy.ai.AI;
import hotlinecesena.model.entities.actors.enemy.ai.AIImpl;
import javafx.geometry.Point2D;

public class EnemyImpl extends AbstractActor implements Enemy {
    
    private static final int ENEMY_MAX_HEALTH = 1;
    private static final double ENEMY_INITIAL_ANGLE = 0;
    private static final double ENEMY_NORMAL_SPEED = 0.25;
    private static final double ENEMY_PURSUIT_SPEED = 0.35;

    private final EnemyType enemyType;
    private final AI enemyAI;
    private final Set<Point2D> walkableSet;
    private boolean pursuit;
    
    protected EnemyImpl(Point2D pos, Inventory inv, EnemyType type, Set<Point2D> walkable, Set<Point2D> walls) {
        super(pos, ENEMY_INITIAL_ANGLE, ENEMY_NORMAL_SPEED, ENEMY_MAX_HEALTH, inv);
        this.enemyType = type;
        this.walkableSet = walkable;
        this.enemyAI = new AIImpl(this.enemyType, ENEMY_INITIAL_ANGLE, walls);
    }
    
    @Override
    public void move(Point2D direction) {
        final Point2D current = this.getPosition();
        final Point2D next = current.add(direction.multiply(!this.pursuit ? ENEMY_NORMAL_SPEED : ENEMY_PURSUIT_SPEED));
        this.enemyAI.setEnemyPos(next);
    }

    @Override
    public AI getAI() {
        return this.enemyAI;
    }
    
    @Override
    public Set<Point2D> getWalkable() {
        return this.walkableSet;
    }

    @Override
    public boolean isChasingTarget() {
        return this.pursuit;
    }

}
