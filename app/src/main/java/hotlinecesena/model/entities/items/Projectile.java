package hotlinecesena.model.entities.items;

import java.util.Objects;

import hotlinecesena.model.entities.AbstractMovableEntity;
import hotlinecesena.model.events.MovementEvent;
import hotlinecesena.utilities.MathUtils;
import javafx.geometry.Point2D;

public class Projectile extends AbstractMovableEntity {

	protected Projectile(Point2D position, double angle, double width, double height, double speed) {
		super(position, angle, width, height, speed);
	}

	@Override
	public void move(Point2D direction) {
		Objects.requireNonNull(direction);
        final Point2D oldPos = this.getPosition();
        final Point2D newPos = oldPos.add(direction.multiply(this.getSpeed()));
        if (!this.hasHitEnemy(newPos) && !this.hasHitObstacle(newPos)) {
            this.setPosition(newPos);
            this.publish(new MovementEvent<>(this, newPos));
        }
        if (this.hasHitObstacle(newPos)) {
			// cancello il proiettile
		}
        if(this.hasHitEnemy(newPos)) {
        	// infliggo danni al nemico e cancello il proiettile
        }
        if (this.hasHitPlayer(newPos)) {
			// infliggo danni al player e cancello il proiettile
		}
    }
	
	@Override
	protected boolean canInitiateRotation() {
		return false;
	}
	
	private boolean hasHitEnemy(final Point2D newPos) {
        return this.getGameMaster().getEnemy().getEnemies()
                .stream()
                .anyMatch(e -> MathUtils.isCollision(
                        newPos, this.getWidth(), this.getHeight(),
                        e.getPosition(), e.getWidth(), e.getHeight()));
    }
	
	private boolean hasHitObstacle(final Point2D newPos) {
        return this.getGameMaster().getPhysics().getObstacles()
                .stream()
                .anyMatch(o -> MathUtils.isCollision(
                        newPos, this.getWidth(), this.getHeight(),
                        new Point2D(o.getMinX() + o.getWidth() / 2, o.getMinY() + o.getHeight() / 2), o.getWidth(), o.getHeight()));
    }
	
	private boolean hasHitPlayer(final Point2D newPos) {
		return false;
	}
}
