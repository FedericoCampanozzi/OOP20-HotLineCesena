package hotlinecesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.actors.DirectionList;
import hotlinecesena.model.entities.actors.player.PlayerImpl;
import hotlinecesena.model.inventory.NaiveInventoryImpl;
import javafx.geometry.Point2D;

/**
 * Runs a series of tests on methods common to all Actors.
 * <br>
 * In this case, Player was chosen as an Actor implementation.
 */
class ActorModelTest {

    private static final double SPEED = 500;
    private static final int ANGLE = 270;
    private static final double WIDTH = 100;
    private static final double HEIGHT = 300;
    private static final double MAX_HP = 100;

    private Actor actor;

    @BeforeAll
    void setUpBeforeClass() throws Exception {
        actor = new PlayerImpl(Point2D.ZERO, ANGLE, SPEED, WIDTH, HEIGHT, MAX_HP, new NaiveInventoryImpl(), Map.of());
    }

    @Test
    private void actorMoveTest() {
        actor.move(DirectionList.NORTH.get());
        assertEquals(DirectionList.NORTH.get().multiply(SPEED), actor.getPosition());
    }

    @Test
    private void actorRotateTest() {
        actor.setAngle(90);
        assertEquals(90, actor.getAngle());
    }

    @Test
    private void actorInventoryInteraction() {
        //TODO
    }

    @Test
    private void actorAttackTest() {
        //TODO
    }

    @Test
    private void actorReloadTest() {
        //TODO
    }

    @Test
    private void actorCannotInitiateReloadingWhileAlreadyReloading() {

    }

    @Test
    private void actorHurtTest() {
        final int damage = 50;
        actor.takeDamage(damage);
        assertEquals(damage, actor.getCurrentHealth());
        final int unrealDamage = 2000;
        actor.takeDamage(unrealDamage);
        assertEquals(0, actor.getCurrentHealth());
    }

    @Test
    private void actorDoesNotMoveWhenDead() {
        actor.move(DirectionList.SOUTH.get());
        assertEquals(DirectionList.NORTH.get().multiply(SPEED), actor.getPosition());
    }

    @Test
    private void actorDoesNotRotateWhenDead() {
        actor.setAngle(0);
        assertNotEquals(0, actor.getAngle());
    }

    @Test
    private void actorDoesNotHealWhenDead() {
        final int unrealHp = 50000;
        actor.heal(unrealHp);
        assertEquals(0, actor.getCurrentHealth());
    }
}
