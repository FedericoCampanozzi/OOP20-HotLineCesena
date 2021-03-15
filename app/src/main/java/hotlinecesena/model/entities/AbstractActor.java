package hotlinecesena.model.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import hotlinecesena.model.entities.components.Component;
import hotlinecesena.util.Point2D;

public abstract class AbstractActor implements Actor {

    private final Map<Class<? extends Component>, Component> components = new HashMap<>();
    private Point2D<Double, Double> position;
    private State state = ActorStateList.IDLE;

    protected AbstractActor(final Point2D<Double, Double> pos) {
        this.position = pos;
    }

    @Override
    public Point2D<Double, Double> getPosition() {
        return this.position;
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
    public <C extends Component> Optional<C> getComponent(final Class<C> clazz) {
        return Optional.ofNullable(clazz.cast(this.components.get(clazz)));
    }

    @Override
    //TODO Find a nicer way to implement this?
    public <C extends Component> void addComponent(final C comp, final Class<C> clazz)
        throws IllegalStateException {
        if (this.components.containsKey(clazz)) {
            throw new IllegalStateException("A component of the same interface is already attached.");
        }
        this.components.put(clazz, comp);
    }
}
