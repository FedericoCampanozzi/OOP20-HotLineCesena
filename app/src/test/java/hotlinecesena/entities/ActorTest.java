package hotlinecesena.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hotlinecesena.model.entities.AbstractActor;
import hotlinecesena.model.entities.Actor;
import hotlinecesena.model.entities.components.CombatComponent;
import hotlinecesena.model.entities.components.MovementComponent;
import hotlinecesena.util.Point2D;
import hotlinecesena.util.Point2DImpl;

class ActorTest {

    private static Actor actor;
    private static CombatComponent combat1;
    private static CombatComponent combat2;
    private static MovementComponent mov1;

    @BeforeAll
    static void setUp() throws Exception {
        actor = new AbstractActor(new Point2DImpl<>(0.0, 0.0)) { };
        combat1 = new CombatComponent() {
            @Override
            public void update() {
            }
        };
        combat2 = new CombatComponent() {
            @Override
            public void update() {
            }
        };
        mov1 = new MovementComponent() {
            @Override
            public void update() {
            }

            @Override
            public void move(Point2D<Double, Double> direction, double intensity) {
            }

            @Override
            public void rotate(Point2D<Double, Double> v) {
            }
        };
    }

    // Interaction with components
    @Test
    void test() {
        assertEquals(Optional.empty(), actor.getComponent(MovementComponent.class));
        assertThrows(IllegalStateException.class, () -> {
            actor.addComponent(combat1, CombatComponent.class);
            actor.addComponent(combat2, CombatComponent.class);
        });
        assertNotEquals(Optional.empty(), actor.getComponent(CombatComponent.class));
    }

}
