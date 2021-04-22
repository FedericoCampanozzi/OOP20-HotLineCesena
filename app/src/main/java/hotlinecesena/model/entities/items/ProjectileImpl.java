package hotlinecesena.model.entities.items;

import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nonnull;

import hotlinecesena.model.entities.AbstractMovableEntity;
import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.entities.actors.Status;
import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.utilities.MathUtils;
import javafx.geometry.Point2D;

/**
 * Implementation of a projectile.
 */
public final class ProjectileImpl extends AbstractMovableEntity implements Projectile {

	/**
	 * Possible status of the projectile.
	 */
    public enum ProjectileStatus implements Status {
        MOVING,
        STOP;
    }

    private Status projectileStatus = ProjectileStatus.MOVING;
    private final double damage;

    /**
     * Class constructor.
     * @param position
     * 				The starting position of the projectile.
     * @param angle
     * 				The starting angle of the projectile.
     * @param width
     * 				The width of the projectile.
     * @param height
     * 				The height of the projectile.
     * @param speed
     * 				The speed of the projectile.
     * @param damage
     * 				The damage of the projectile.
     */
    public ProjectileImpl(final Point2D position, final double angle, final double width, final double height, final double speed, final double damage) {
        super(position, angle, width, height, speed);
        this.damage = damage;
    }

    /**
     * Checks if the projectile has hit an obstacle or an entity.
     * If not, update the position of the projectile.
     */
    @Override
    protected void executeMovement(@Nonnull final Point2D pointDeltaTime) {
        Objects.requireNonNull(pointDeltaTime);
        final Point2D oldPos = this.getPosition();
        final Point2D unitVector = this.directionFromAngle(this.getAngle());
        final Point2D newPos = oldPos.add(new Point2D(
                unitVector.getX() * pointDeltaTime.getX(),
                unitVector.getY() * pointDeltaTime.getY()
                ).multiply(this.getSpeed()));
        if (this.hasHitObstacle(newPos)) {
            projectileStatus = ProjectileStatus.STOP;
        } else {
            final Optional<Enemy> enemy = this.hasHitEnemy(newPos);
            if (enemy.isPresent()) {
                enemy.get().takeDamage(damage);
                projectileStatus = ProjectileStatus.STOP;
            } else {
                final Optional<Player> player = this.hasHitPlayer(newPos);
                if (player.isPresent()) {
                    player.get().takeDamage(damage);
                    projectileStatus = ProjectileStatus.STOP;
                } else {
                    this.setPosition(newPos);
                }
            }
        }
    }

    /**
     * The projectile can't rotate.
     */
    @Override
    protected boolean canInitiateRotation() {
        return false;
    }

    /**
     * @param angle
     * @return the direction of the projectile based on its angle.
     */
    private Point2D directionFromAngle(final double angle) {
        final double radians = Math.toRadians(angle);
        return new Point2D(Math.cos(radians), Math.sin(radians));
    }

    /**
     * @param newPos
     * @return the enemy hit (Optional).
     */
    private Optional<Enemy> hasHitEnemy(final Point2D newPos) {
        return this.getGameMaster().getEnemy().getEnemies()
                .stream()
                .filter(enemy -> !enemy.getActorStatus().equals(ActorStatus.DEAD))
                .filter(enemy -> MathUtils.isCollision(
                        newPos, this.getWidth(), this.getHeight(),
                        enemy.getPosition(), enemy.getWidth(), enemy.getHeight()))
                .findAny();
    }

    /**
     * @param newPos
     * @return true if has hit an obstacle.
     */
    private boolean hasHitObstacle(final Point2D newPos) {
        return this.getGameMaster().getPhysics().getObstacles()
                .stream()
                .anyMatch(o -> MathUtils.isCollision(
                        newPos, this.getWidth(), this.getHeight(),
                        o.getPosition(), o.getWidth(), o.getHeight()));
    }

    /**
     * @param newPos
     * @return true if has hit the player.
     */
    private Optional<Player> hasHitPlayer(final Point2D newPos) {
        final Player player = this.getGameMaster().getPlayer().getPly();
        if (MathUtils.isCollision(
                newPos, this.getWidth(), this.getHeight(),
                player.getPosition(), player.getWidth(), player.getHeight()
                )) {
            return Optional.of(player);
        }
        return Optional.empty();
    }

    /**
     * @return the projectile status.
     */
    @Override
    public Status getProjectileStatus() {
        return projectileStatus;
    }
}