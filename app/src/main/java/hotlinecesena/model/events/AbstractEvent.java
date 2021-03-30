package hotlinecesena.model.events;

public abstract class AbstractEvent<T> implements Event<T> {

    private T source;

    protected AbstractEvent(final T source) {
        this.source = source;
    }

    @Override
    public T getSource() {
        return this.source;
    }
}
