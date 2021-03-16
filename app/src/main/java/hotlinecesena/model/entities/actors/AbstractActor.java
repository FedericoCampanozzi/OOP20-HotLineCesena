package hotlinecesena.model.entities.actors;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import hotlinecesena.model.entities.State;
import hotlinecesena.model.entities.components.Component;
import hotlinecesena.model.entities.components.MovementComponent;
import hotlinecesena.util.Point2D;

public abstract class AbstractActor implements Actor {

    private final Map<Class<?>, Component> components = new HashMap<>();
    private State state = ActorStateList.IDLE;

    @Override
    public Point2D<Double, Double> getPosition() {
        return this.getComponent(MovementComponent.class).getPosition();
    }

    @Override
    public State getState() {
        return this.state;
    }

    @Override
    public void setState(final State s) {
        this.state = s;
    }

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
