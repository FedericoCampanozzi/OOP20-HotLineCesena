package hotlinecesena.model.entities.actors;

import java.util.Optional;

import hotlinecesena.model.entities.AbstractEntity;
import hotlinecesena.model.entities.items.Weapon;
import hotlinecesena.model.inventory.Inventory;
import javafx.geometry.Point2D;

public abstract class AbstractActor extends AbstractEntity implements Actor {

    private final double maxHealth;
    
    private double currentHealth;
    private double angle;
    private ActorState state = ActorStateList.IDLE;
    private final Inventory inventory;
    
    
    protected AbstractActor(final Point2D pos, final double maxHealth, final double angle, final Inventory inv) {
        super(pos);
        this.maxHealth = this.currentHealth = maxHealth;
        this.angle = angle;
        this.inventory = inv;
    }
    

    @Override
    public void move(Direction direction, double speed) {
        final Point2D oldPos = this.getPosition();
        final Point2D dir = direction.get();
        this.setPosition(oldPos.add(dir.multiply(speed)));
    }

    @Override
    public void setAngle(double angle) {
        this.angle = angle;
    }

    @Override
    public double getAngle() {
        return this.angle;
    }
    
    @Override
    public void attack() {
        final Optional<Weapon> weapon = this.inventory.getEquippedWeapon();
        if (weapon.isPresent()) {
            weapon.get().activate();
        }
    }
    
    @Override
    public void reload() {
        this.inventory.reloadWeapon();
    }


    @Override
    public void takeDamage(double damage) {
        if (this.currentHealth > 0) {
            this.currentHealth = this.currentHealth > damage ? this.currentHealth - damage : 0;
        }
    }
    
    @Override
    public double getMaxHealth() {
        return this.maxHealth;
    }

    @Override
    public double getCurrentHealth() {
        return this.currentHealth;
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
