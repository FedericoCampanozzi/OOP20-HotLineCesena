package hotlinecesena.model.entities.actors;

/**
 * 
 */
public enum ActorStatus {

	/**
	 * Actor is either moving or receiving no input.
	 */
    NORMAL,
    
    /**
	 * Actor is reloading.
	 */
    RELOADING,
    
    /**
	 * Actor is attacking.
	 */
    ATTACKING,
    
    /**
	 * Actor is dead. All subsequent inputs should be ignored.
	 */
    DEAD;
}
