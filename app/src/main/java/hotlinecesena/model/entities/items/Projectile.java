package hotlinecesena.model.entities.items;

import java.util.Objects;
import java.util.Optional;

import hotlinecesena.model.entities.AbstractMovableEntity;
import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.events.MovementEvent;
import hotlinecesena.utilities.MathUtils;
import javafx.geometry.Point2D;

public class Projectile extends AbstractMovableEntity {
	
	public enum ProjectileStatus{
		MOVING,
		STOP;
	}
	
	private ProjectileStatus projectileStatus = ProjectileStatus.MOVING;
	private double damage;

	protected Projectile(Point2D position, double angle, double width, double height, double speed, final double damage) {
		super(position, angle, width, height, speed);
		this.damage = damage;
	}

	@Override
	public void move(Point2D direction) {
		Objects.requireNonNull(direction);
        final Point2D oldPos = this.getPosition();
        final Point2D newPos = oldPos.add(direction.multiply(this.getSpeed()));
        if (this.hasHitObstacle(newPos)) {
			this.projectileStatus = ProjectileStatus.STOP;
		}
        else {
        	final Optional<Enemy> enemy = this.hasHitEnemy(newPos);
        	if (enemy.isPresent()) {
				enemy.get().takeDamage(this.damage);
				this.projectileStatus = ProjectileStatus.STOP;
			}
        	else {
        		final Optional<Player> player = this.hasHitPlayer(newPos);
        		if (player.isPresent()) {
					player.get().takeDamage(this.damage);
					this.projectileStatus = ProjectileStatus.STOP;
				}
        		else {
        			this.setPosition(newPos);
                    this.publish(new MovementEvent<>(this, newPos));
				}
			}
        }
    }
	
	@Override
	protected boolean canInitiateRotation() {
		return false;
	}
	
	private Optional<Enemy> hasHitEnemy(final Point2D newPos) {
        	
        return this.getGameMaster().getEnemy().getEnemies()
                .stream()
                .filter(e -> MathUtils.isCollision(
                        newPos, this.getWidth(), this.getHeight(),
                        e.getPosition(), e.getWidth(), e.getHeight()))
                .findFirst();
    }
	
	private boolean hasHitObstacle(final Point2D newPos) {
        return this.getGameMaster().getPhysics().getObstacles()
                .stream()
                .anyMatch(o -> MathUtils.isCollision(
                        newPos, this.getWidth(), this.getHeight(),
                        new Point2D(o.getMinX() + o.getWidth() / 2, o.getMinY() + o.getHeight() / 2), o.getWidth(), o.getHeight()));
    }
	
	private Optional<Player> hasHitPlayer(final Point2D newPos) {
		Player player = getGameMaster().getPlayer().getPly();
		if (MathUtils.isCollision(newPos, getWidth(), getHeight(), player.getPosition(), player.getWidth(), player.getHeight())) {
			return Optional.of(player);
		}
		return Optional.empty();
	}
	
	public ProjectileStatus getProjectileStatus() {
		return this.projectileStatus;
	}
}
