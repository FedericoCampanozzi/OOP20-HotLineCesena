package hotlinecesena.model.entities.items;

import java.util.Optional;
import java.util.function.Consumer;

import hotlinecesena.model.entities.actors.Actor;

public interface Item {

    Optional<Consumer<Actor>> usage();
    
    int getMaxStacks();
}
