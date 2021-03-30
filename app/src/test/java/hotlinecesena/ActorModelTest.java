package hotlinecesena;

import static org.junit.jupiter.api.Assertions.*;

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

    static Actor actor;
    static final double SPEED = 500;
    static final double MAX_HP = 100;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        actor = new PlayerImpl(Point2D.ZERO, 270, SPEED, MAX_HP, new NaiveInventoryImpl(), Map.of());
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
    private void actorHurtTest() {
        actor.takeDamage(50);
        assertEquals(50, actor.getCurrentHealth());
        actor.takeDamage(2000);
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
        actor.heal(50000);
        assertEquals(0, actor.getCurrentHealth());
    }

}
