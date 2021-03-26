package hotlinecesena.model.entities.actors.enemy;

/**
 * 
 */

public enum EnemyType {
    
    /**
     * Simple type of Enemy that cannot move and is standing stationary
     */
    IDLE,
    
    /**
     * Type of Enemy of which movement are generated at random
     */
    RANDOM_MOVEMENT,
    
    /**
     * Type of Enemy that patrols the area in which he is generated
     */
    PATROLLING,
    
    /**
     * Special type of Enemy
     */
    BOSS
    
}
