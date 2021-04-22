package hotlinecesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import hotlinecesena.model.entities.actors.DirectionList;
import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.entities.actors.enemy.EnemyImpl;
import hotlinecesena.model.entities.actors.enemy.EnemyType;
import hotlinecesena.model.inventory.NaiveInventoryImpl;
import javafx.geometry.Point2D;

class EnemyModelTest {

    private static final int OUT_OF_RANGE = 9;

    @Test
    void enemyIdle() {
        final Enemy idle = new EnemyImpl(new Point2D(0, 0), new NaiveInventoryImpl(), 0, EnemyType.IDLE, Set.of(), Set.of());

        Point2D current = idle.getPosition();

        // enemy remains stationary
        idle.move(idle.getAI().getNextMove(null, false, null));
        assertEquals(current, idle.getPosition());

    }

    @Test
    void enemyPatrolling() {
        final Set<Point2D> walkable = new HashSet<>() { /**
             * 
             */
            private static final long serialVersionUID = 1L;

        { add(new Point2D(0, 0)); add(new Point2D(1, 0)); add(new Point2D(1, 1)); add(new Point2D(0, 1)); }};
        final Enemy patrol = new EnemyImpl(new Point2D(0, 0), new NaiveInventoryImpl(), 0, EnemyType.PATROLLING, walkable, Set.of());

        assertEquals(DirectionList.EAST.get(), patrol.getAI().getNextMove(null, false, walkable));

        // moves clockwise
        patrol.move(patrol.getAI().getNextMove(null, false, walkable));
        assertEquals(new Point2D(1, 0), patrol.getPosition());

        // moves clockwise
        patrol.move(patrol.getAI().getNextMove(null, false, walkable));
        assertEquals(new Point2D(1, 1), patrol.getPosition());

        // moves clockwise
        patrol.move(patrol.getAI().getNextMove(null, false, walkable));
        assertEquals(new Point2D(0, 1), patrol.getPosition());

        // moves clockwise
        patrol.move(patrol.getAI().getNextMove(null, false, walkable));
        assertEquals(new Point2D(0, 0), patrol.getPosition());
    }

    @Test
    void enemyShoot() {

        Enemy idle = new EnemyImpl(new Point2D(0, 0), new NaiveInventoryImpl(), 0, EnemyType.IDLE, Set.of(), Collections.emptySet());
        Point2D target = new Point2D(2, 2);

        // enemy has a clear line of sight
        assertTrue(idle.getAI().isShooting(target));

        idle = new EnemyImpl(new Point2D(0, 0), new NaiveInventoryImpl(), 0, EnemyType.IDLE, Set.of(), Set.of(new Point2D(1, 1)));

        // wall is blocking the enemy view
        assertFalse(idle.getAI().isShooting(target));

        idle = new EnemyImpl(new Point2D(0, 0), new NaiveInventoryImpl(), 0, EnemyType.IDLE, Set.of(), Set.of(new Point2D(1, 0)));
        target = new Point2D(2, 0);

        // wall is blocking the enemy view
        assertFalse(idle.getAI().isShooting(target));

        target = new Point2D(2, 1);

        // wall is blocking the enemy view
        assertFalse(idle.getAI().isShooting(target));

        target = new Point2D(2, 2);

        // enemy has a clear line of sight
        assertTrue(idle.getAI().isShooting(target));

        idle = new EnemyImpl(new Point2D(0, 0), new NaiveInventoryImpl(), 0, EnemyType.IDLE, Set.of(), Set.of(new Point2D(2, 2)));
        target = new Point2D(2, 1);

        // enemy has a clear line of sight
        assertTrue(idle.getAI().isShooting(target));
    }

    @Test
    void enemyVision() {
        Enemy enemy = new EnemyImpl(new Point2D(1, 1), new NaiveInventoryImpl(), 0, EnemyType.IDLE, Set.of(), Collections.emptySet());
        Point2D target = new Point2D(2, 2);

        // enemy field of view = 90°

        // enemy has the target in the field of view
        assertTrue(enemy.getAI().isShooting(target));

        target = new Point2D(2, 1);

        // enemy has the target in the field of view
        assertTrue(enemy.getAI().isShooting(target));

        target = new Point2D(2, 0);

        // enemy has the target in the field of view
        assertTrue(enemy.getAI().isShooting(target));

        target = new Point2D(0, 0);

        // enemy does not have the target in the field of view
        assertFalse(enemy.getAI().isShooting(target));

        target = new Point2D(1, 0);

        // enemy does not have the target in the field of view
        assertFalse(enemy.getAI().isShooting(target));

        target = new Point2D(0, 1);

        // enemy does not have the target in the field of view
        assertFalse(enemy.getAI().isShooting(target));

        target = new Point2D(OUT_OF_RANGE, 1);

        // target is to far away
        assertFalse(enemy.getAI().isShooting(target));
    }
}
