package hotlinecesena.model.entities.items;

import java.util.function.Consumer;

import hotlinecesena.model.entities.actors.Actor;

public interface Item {

    Consumer<Actor> usage();
    
    int getMaxStacks();
}
