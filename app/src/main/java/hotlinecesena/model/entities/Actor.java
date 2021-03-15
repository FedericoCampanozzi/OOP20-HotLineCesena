package hotlinecesena.model.entities;

import java.util.Optional;

import hotlinecesena.model.entities.components.Component;

public interface Actor extends GameEntity {

    <C extends Component> Optional<C> getComponent(Class<C> clazz);

    <C extends Component> void addComponent(C comp, Class<C> clazz);
}
