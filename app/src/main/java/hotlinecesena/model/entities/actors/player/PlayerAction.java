package hotlinecesena.model.entities.actors.player;

/**
 * 
 * List of constants used as a bridge between Model and Controller.
 * 
 * They represent commands that the Player may receive from its Controller class.
 *
 */
public enum PlayerAction {

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
    RELOAD;
}
