package hotlinecesena.model.entities.actors;

import hotlinecesena.model.entities.State;

/**
 * 
 */
public enum ActorStateList implements State {

	/**
	 * Actor is receiving no input.
	 */
    IDLE,
    
    /**
	 * Actor is moving.
	 */
    MOVING,
    
    /**
	 * Actor is attacking.
	 */
    ATTACKING,
    
    /**
	 * Actor is dead. All subsequent inputs should be ignored.
	 */
    DEAD;
}
