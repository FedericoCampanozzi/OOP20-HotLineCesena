package hotlinecesena.model.entities.items;

import java.util.function.Consumer;

import hotlinecesena.model.entities.actors.Actor;

/**
 * Inanimate entities that trigger a function when picked up.
 */
public interface Item {

	/**
	 * @return the function of the item.
	 */
    Consumer<Actor> usage();
    
    /**
     * @return the max amount of that item in the inventory.
     */
    int getMaxStacks();
}
