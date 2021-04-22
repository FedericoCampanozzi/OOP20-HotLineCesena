package hotlinecesena;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.entities.actors.DirectionList;
import hotlinecesena.model.entities.actors.player.PlayerImpl;
import hotlinecesena.model.entities.items.Weapon;
import hotlinecesena.model.inventory.Inventory;
import hotlinecesena.model.inventory.NaiveInventoryFactory;
import javafx.geometry.Point2D;

/**
 * Runs a series of tests on methods common to all {@link Actor}.
 * <br>
 * {@link PlayerImpl} was chosen as a complete Actor implementation.
 */
@TestInstance(Lifecycle.PER_METHOD)
class ActorModelTest {

    private static final double SPEED = 1.0;
    private static final int ANGLE = 270;
    private static final double WIDTH = 1.0;
    private static final double HEIGHT = 1.0;
    private static final double MAX_HP = 100.0;
    private Actor actor;

    private void setup() {
        final Inventory inv = new NaiveInventoryFactory().createWithPistolOnly();
        actor = new PlayerImpl(Point2D.ZERO, ANGLE, WIDTH, HEIGHT, SPEED, MAX_HP, inv, Map.of(), List.of(), List.of(),
                Map.of(), Map.of());
    }

    @BeforeEach
    void reset() {
        this.setup();
    }

    @Test
    void actorMove() {
        actor.move(DirectionList.NORTH.get());
        assertThat(actor.getPosition(), equalTo(DirectionList.NORTH.get().multiply(SPEED)));
    }

    @Test
    void actorRotate() {
        actor.setAngle(90.0);
        assertThat(actor.getAngle(), is(90.0));
    }

    @Test
    void actorAttack() throws InterruptedException {
        assertThat(actor.getInventory().getWeapon(), not(Optional.empty()));
        final Weapon w = actor.getInventory().getWeapon().get();
        assertThat(actor.getInventory().getQuantityOf(w.getCompatibleAmmunition()), not(0));
        assertFalse(actor.getInventory().isReloading());
        Thread.sleep((long) w.getRateOfFire());
        actor.attack();
        assertEquals(ActorStatus.ATTACKING, actor.getActorStatus());
    }

    @Test
    void actorReload() throws InterruptedException {
        final Weapon w = actor.getInventory().getWeapon().get();
        final double reloadTime = w.getReloadTime();
        actor.attack();
        Thread.sleep((long) w.getRateOfFire());
        actor.attack();
        actor.reload();
        assertTrue(actor.getInventory().isReloading());
        actor.getInventory().update(reloadTime);
        assertFalse(actor.getInventory().isReloading());
    }

    @Test
    void actorCannotInitiateReloadingWhileAlreadyReloading() throws InterruptedException {
        final Weapon w = actor.getInventory().getWeapon().get();
        final double reloadTime = w.getReloadTime();
        actor.attack();
        Thread.sleep((long) w.getRateOfFire());
        actor.attack();
        actor.reload();
        actor.getInventory().update(reloadTime / 2.0);
        assertTrue(actor.getInventory().isReloading()); //Still reloading
        actor.reload();
        actor.getInventory().update(reloadTime / 2.0);
        assertFalse(actor.getInventory().isReloading()); //Reload timer should not reset
    }

    @Test
    void actorTakeDamage() {
        final double damage = 50.0;
        actor.takeDamage(damage);
        assertThat(actor.getCurrentHealth(), is(MAX_HP - damage));
        final double unrealDamage = 2000.0;
        actor.takeDamage(unrealDamage);
        assertThat(actor.getCurrentHealth(), is(0.0));
    }

    @Test
    void actorDoesNotMoveWhenDead() {
        actor.takeDamage(MAX_HP);
        actor.move(DirectionList.SOUTH.get());
        assertThat(actor.getPosition(), equalTo(Point2D.ZERO));
    }

    @Test
    void actorDoesNotRotateWhenDead() {
        actor.takeDamage(MAX_HP);
        actor.setAngle(0.0);
        assertThat(actor.getAngle(), is(not(0.0)));
    }

    @Test
    void actorDoesNotHealWhenDead() {
        actor.takeDamage(MAX_HP);
        final double unrealHp = 50000.0;
        actor.heal(unrealHp);
        assertThat(actor.getCurrentHealth(), is(0.0));
    }
}
