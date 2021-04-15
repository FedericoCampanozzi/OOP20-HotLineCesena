package hotlinecesena.model.entities.actors;

/**
 * Statuses indicating whether {@link Actor}s are
 * performing specific actions at a given moment.
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
