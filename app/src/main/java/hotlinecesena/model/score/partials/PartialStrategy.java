package hotlinecesena.model.score.partials;

/**
 * Algorithm for a specific partial score.
 */
public interface PartialStrategy {

    /**
     * Applies this algorithm's formula and returns the
     * computed partial score.
     * @return the computed partial score.
     */
    int applyFormula();

    /**
     * Gets the relevant factor used by this algorithm.
     * @return the relevant factor used by this algorithm.
     */
    int getRelevantFactor();
}
