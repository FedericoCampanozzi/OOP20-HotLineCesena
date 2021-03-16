package hotlinecesena.model.entities.actors;

import java.util.NoSuchElementException;

import hotlinecesena.model.entities.GameEntity;
import hotlinecesena.model.entities.components.Component;

/**
 * <p>
 * Heavily inspired by https://github.com/divotkey/ecs
 * </p>
 * <p>
 * Represents a container of {@link Component}s.
 * </p>
 */

public interface Actor extends GameEntity {

    /**
     * <p>
     * Adds a {@link Component} to this actor.
     * </p>
     * 
     * @param <C>
     * @param component {@link Component} to be added.
     * @throws IllegalStateException if this actor already contains a component
     * implementing an interface which directly extends Component.
     * For example, suppose we have an actor that possesses a component implementing CombatComponent.
     * We now wish to add a component implementing the AdvancedCombatComponent interface, which in turn
     * extends the aforementioned CombatComponent interface. Since a CombatComponent is already attached
     * to our actor, this method will fail, throwing an exception.
     */
    <C extends Component> void addComponent(C component) throws IllegalStateException;

    /**
     * 
     * @param <C>
     * @param compInterface .class object of the interface implemented by the desired component.
     * @return the component matching the interface passed as input.
     * @throws NoSuchElementException if no component implementing {@code compInterface} is found.
     */
    <C extends Component> C getComponent(Class<C> compInterface) throws NoSuchElementException;
}
