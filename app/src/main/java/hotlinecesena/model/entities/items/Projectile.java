package hotlinecesena.model.entities.items;

import java.util.Objects;
import java.util.Optional;

import hotlinecesena.model.entities.AbstractMovableEntity;
import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.utilities.MathUtils;
import javafx.geometry.Point2D;

public final class Projectile extends AbstractMovableEntity {

    public enum ProjectileStatus {
        MOVING,
        STOP;
    }

    private ProjectileStatus projectileStatus = ProjectileStatus.MOVING;
    private final double damage;

    protected Projectile(final Point2D position, final double angle, final double width, final double height, final double speed, final double damage) {
        super(position, angle, width, height, speed);
        this.damage = damage;
    }

    @Override
    public void move(final Point2D pointDeltaTime) {
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

    @Override
    protected boolean canInitiateRotation() {
        return false;
    }

    private Point2D directionFromAngle(final double angle) {
        final double radians = Math.toRadians(angle);
        return new Point2D(Math.cos(radians), Math.sin(radians));
    }

    private Optional<Enemy> hasHitEnemy(final Point2D newPos) {
        return this.getGameMaster().getEnemy().getEnemies()
                .stream()
                .filter(enemy -> !enemy.getActorStatus().equals(ActorStatus.DEAD))
                .filter(enemy -> MathUtils.isCollision(
                        newPos, this.getWidth(), this.getHeight(),
                        enemy.getPosition(), enemy.getWidth(), enemy.getHeight()))
                .findAny();
    }

    private boolean hasHitObstacle(final Point2D newPos) {
        return this.getGameMaster().getPhysics().getObstacles()
                .stream()
                .anyMatch(o -> MathUtils.isCollision(
                        newPos, this.getWidth(), this.getHeight(),
                        o.getPosition(), o.getWidth(), o.getHeight()));
    }

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

    public ProjectileStatus getProjectileStatus() {
        return projectileStatus;
    }
}