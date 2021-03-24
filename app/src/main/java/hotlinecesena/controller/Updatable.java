package hotlinecesena.controller;

import java.util.function.Consumer;

/**
 * 
 * Interface for controllers that need to be polled by the game loop.
 *
 */
public interface Updatable {

    /**
     * The update method which will be invoked from inside the game loop.
     * @return A lambda implementing {@code Consumer<Double>}, where {@code Double}
     * refers to the time delta.
     */
    Consumer<Double> getUpdateMethod();
}
