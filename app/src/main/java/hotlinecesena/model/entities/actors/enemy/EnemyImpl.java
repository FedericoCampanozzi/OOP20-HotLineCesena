package hotlinecesena.model.entities.actors.enemy;

import java.util.Objects;
import java.util.Set;

import javax.annotation.Nonnull;

import hotlinecesena.model.entities.actors.AbstractActor;
import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.inventory.Inventory;
import hotlinecesena.model.entities.actors.enemy.ai.AI;
import hotlinecesena.model.entities.actors.enemy.ai.AIImpl;
import hotlinecesena.model.events.MovementEvent;
import javafx.geometry.Point2D;

/**
 * Class that represent the generic enemy implementation.
 */
public final class EnemyImpl extends AbstractActor implements Enemy {

    private static final int ENEMY_MAX_HEALTH = 1;
    private static final double ENEMY_NORMAL_SPEED = 1;
    private static final double ENEMY_WIDTH = 1;
    private static final double ENEMY_HEIGHT = 1;

    private final EnemyType enemyType;
    private final AI enemyAI;
    private final Set<Point2D> walkableSet;
    private final Set<Point2D> wallSet;
    private boolean pursuit;

    /**
     * Class constructor.
     * @param pos the starting position
     * @param inv the weapon that is equipped
     * @param rotation the angle that the enemy faces
     * @param type the type of movement that the enemy will inherit
     * @param walkable collections of points that are traversable by
     * the enemy
     * @param walls collections of all wall objects that could reduce
     * enemy vision
     * @see WorldGeneratorBuilder
     */
    public EnemyImpl(final Point2D pos, final Inventory inv, final double rotation,
            final EnemyType type, @Nonnull final  Set<Point2D> walkable, final Set<Point2D> walls) {

        super(pos, rotation, ENEMY_NORMAL_SPEED, ENEMY_MAX_HEALTH,
                ENEMY_WIDTH, ENEMY_HEIGHT, inv);
        this.enemyType = type;
        this.walkableSet = Objects.requireNonNull(walkable);
        this.wallSet = walls;
        this.enemyAI = new AIImpl(this.getPosition(), this.enemyType,
                rotation, this.wallSet);
    }

    @Override
    public void executeMovement(final Point2D direction) {
        if (!this.getActorStatus().equals(ActorStatus.DEAD)) {
            final Point2D current = this.getPosition();
            final Point2D next = current.add(direction.multiply(ENEMY_NORMAL_SPEED));
            this.setPosition(next);
            this.enemyAI.setEnemyPos(next);
            if (!current.equals(next)) {
                this.publish(new MovementEvent(this, next));
            }
        }
    }

    @Override
    public AI getAI() {
        return this.enemyAI;
    }

    @Override
    public void setIsInPursuit(final boolean pursuit) {
        this.pursuit = pursuit;
    }

    @Override
    public Set<Point2D> getWalkable() {
        return this.walkableSet;
    }

    @Override
    public boolean isChasingTarget() {
        return this.pursuit;
    }

    @Override
    public EnemyType getEnemyType() {
        return this.enemyType;
    }
}
