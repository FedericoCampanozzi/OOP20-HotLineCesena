package hotlinecesena.model.entities.actors;

/**
 *
 */
public enum ActorStatus implements Status {

    /**
     * Actor is receiving no inputs.
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
