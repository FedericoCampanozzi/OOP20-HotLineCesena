package hotlinecesena;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.entities.actors.DirectionList;
import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.entities.actors.enemy.EnemyImpl;
import hotlinecesena.model.entities.actors.enemy.EnemyType;
import javafx.geometry.Point2D;

class EnemyModelTest {

    @Test
    void enemyShoot() {
        
        Enemy idle = new EnemyImpl(new Point2D(0,0), null, EnemyType.IDLE, null, Collections.emptySet());
        final Point2D target = new Point2D(2,2);
        
        // ENEMY SHOULD HAVE A CLEAR LINE OF SIGHT
        assertTrue(idle.getAI().isShooting(target));
        
        Set<Point2D> wall = new HashSet<>() {{ add(new Point2D(1,1)); }};
        idle = new EnemyImpl(new Point2D(0,0), null, EnemyType.IDLE, null, wall);
        
        // WALL IS BLOCKING THE ENEMY VIEW
        assertFalse(idle.getAI().isShooting(target));
    }
    
    @Test
    void enemyIdle() {
        final Enemy idle = new EnemyImpl(new Point2D(0,0), null, EnemyType.IDLE, null, null);
        
        Point2D current = idle.getPosition();
        
        // ENEMY SHOULD REMAIN STATIONARY
        idle.move(idle.getAI().getNextMove(null, false, null));
        assertEquals(current, idle.getPosition());
        
    }
    
    @Test
    void enemyPatrolling() {
        final Set<Point2D> walkable = new HashSet<>() {{ add(new Point2D(0,0)); add(new Point2D(1,0)); add(new Point2D(1,1)); add(new Point2D(0,1));}};
        final Enemy patrol = new EnemyImpl(new Point2D(0,0), null, EnemyType.PATROLLING, walkable, null);
        
        assertEquals(DirectionList.EAST.get(), patrol.getAI().getNextMove(null, false, walkable));
        
        // MOVES EAST
        patrol.move(patrol.getAI().getNextMove(null, false, walkable));
        assertEquals(new Point2D(0.25,0), patrol.getPosition());
        
        // MOVES EAST
        patrol.move(patrol.getAI().getNextMove(null, false, walkable));
        assertEquals(new Point2D(0.5,0), patrol.getPosition());
        
        // MOVES EAST
        patrol.move(patrol.getAI().getNextMove(null, false, walkable));
        assertEquals(new Point2D(0.75,0), patrol.getPosition());
        
        // MOVES EAST FOR THE LAST TIME NO MORE SPACE TO GO ANY FURTHER
        patrol.move(patrol.getAI().getNextMove(null, false, walkable));
        assertEquals(new Point2D(1,0), patrol.getPosition());
        
        // MOVES CLOCKWISE
        patrol.move(patrol.getAI().getNextMove(null, false, walkable));
        assertEquals(new Point2D(1,0.25), patrol.getPosition());       
    }

}
