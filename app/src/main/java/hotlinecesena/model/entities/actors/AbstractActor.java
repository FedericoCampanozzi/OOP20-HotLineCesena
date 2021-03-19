package hotlinecesena.model.entities.actors;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import hotlinecesena.model.entities.components.Component;

public abstract class AbstractActor implements Actor {

    private final Map<Class<?>, Component> components = new HashMap<>();

    @Override
    public <C extends Component> C getComponent(final Class<C> compInterface) {
        return Optional.ofNullable(compInterface.cast(this.components.get(compInterface))).orElseThrow();
    }

    @Override
    //TODO Find a nicer way to implement this?
    public <C extends Component> void addComponent(final C component) throws IllegalStateException {
        var compInterface = component.getClass().getInterfaces()[0];

        // Checks whether this component's interface (or a superinterface of it) is already present.
        if (this.components.keySet()
                .stream()
                .anyMatch(cl -> cl.isAssignableFrom(compInterface))) {
            throw new IllegalStateException("A component of the same interface is already attached.");
        }
        this.components.put(compInterface, component);
    }
}
