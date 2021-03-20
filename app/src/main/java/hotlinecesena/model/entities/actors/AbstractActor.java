package hotlinecesena.model.entities.actors;

import hotlinecesena.model.entities.AbstractEntity;
import hotlinecesena.model.inventory.Inventory;
import javafx.geometry.Point2D;

public abstract class AbstractActor extends AbstractEntity implements Actor {

    private double health;
    private double angle;
    private ActorState state = ActorStateList.IDLE; // TODO: Will replace with a finite-state machine (State pattern)
    private final Inventory inventory;
    
    
    protected AbstractActor(final Point2D pos, final double health, final double angle, final Inventory inv) {
        super(pos);
        this.health = health;
        this.angle = angle;
        this.inventory = inv;
    }
    

    @Override
    public void move(Point2D direction, Point2D velocity) {
        final Point2D oldPos = this.getPosition();
        this.setPosition(new Point2D(oldPos.getX() + direction.getX() * velocity.getX(),
                                     oldPos.getY() + direction.getY() * velocity.getY()));
    }

    @Override
    public void rotate(double angle) {
        this.angle -= angle;
    }
    
    @Override
    public void attack() {
        final var weapon = this.inventory.getEquippedWeapon();
        if (weapon.isPresent()) {
            weapon.get().activate();
        }
    }
    
    @Override
    public void reload() {
        this.inventory.reloadWeapon();
    }

    @Override
    public double getAngle() {
        return this.angle;
    }

    @Override
    public void takeDamage(double damage) {
        if (this.health > 0) {
            this.health = this.health > damage ? this.health - damage : 0;
        }
    }

    @Override
    public double getCurrentHealth() {
        return this.health;
    }
    
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public ActorState getState() {
        return this.state;
    }
    
    @Override
    public void setState(ActorState s) {
        this.state = s;
    }

}
