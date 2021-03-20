package hotlinecesena.model.entities.actors;

/**
 * 
 */
public enum ActorStateList implements ActorState {

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
