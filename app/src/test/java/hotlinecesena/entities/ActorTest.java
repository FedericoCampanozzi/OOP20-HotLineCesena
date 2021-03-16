package hotlinecesena.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hotlinecesena.model.entities.actors.AbstractActor;
import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.components.CombatComponent;
import hotlinecesena.model.entities.components.CombatComponentImpl;
import hotlinecesena.model.entities.components.MovementComponent;

class ActorTest {

    private interface AdvancedCombatComponent extends CombatComponent { }

    private static Actor actor;

    @BeforeAll
    static void setUp() throws Exception {
        actor = new AbstractActor() { };
    }

    // Interaction with components
    @Test
    void test() {
        assertThrows(NoSuchElementException.class, () -> actor.getComponent(MovementComponent.class));
        assertThrows(IllegalStateException.class, () -> {
            actor.addComponent(new CombatComponentImpl());
            actor.addComponent(new CombatComponentImpl());
        });
        assertThrows(IllegalStateException.class, () -> {
            actor.addComponent(new AdvancedCombatComponent() {
                @Override
                public void attackWithWeapon() { }
                @Override
                public void reloadWeapon() { }
                @Override
                public void update() { }
                });
        });
        assertDoesNotThrow(() -> actor.getComponent(CombatComponent.class));
        // Test correct interface
        assertDoesNotThrow(() -> actor.getComponent(CombatComponent.class)
                .getClass().getMethod("reloadWeapon"));
    }
}
