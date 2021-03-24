package hotlinecesena.model.entities.actors.player;

/**
 * 
 * List of constants used as a bridge between Model and Controller.
 * 
 * They represent commands that the player model may receive from the input manager.
 *
 */
public enum CommandType {

    /**
     * Move north.
     */
    MOVE_NORTH,
    
    /**
     * Move south.
     */
    MOVE_SOUTH,
    
    /**
     * Move east.
     */
    MOVE_EAST,
    
    /**
     * Move west.
     */
    MOVE_WEST,
    
    /**
     * Attack.
     */
    ATTACK,
    
    /**
     * Reload weapon.
     */
    RELOAD,
    
    /**
     * Pick up item or weapon.
     */
    PICK_UP,
    
    /** Use item.
     * 
     */
    USE;
}
