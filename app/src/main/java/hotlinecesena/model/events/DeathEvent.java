package hotlinecesena.model.events;

import hotlinecesena.model.entities.actors.Actor;

public class DeathEvent<A extends Actor> extends AbstractEvent<A> {

    public DeathEvent(A source) {
        super(source);
    }
}
