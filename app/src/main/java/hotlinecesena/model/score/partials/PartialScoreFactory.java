package hotlinecesena.model.score.partials;

import java.util.Set;

/**
 * Models a factory for {@link PartialScore}.
 */
public interface PartialScoreFactory {

    /**
     * Creates instances of all algorithms currently available and
     * bundles them in a Set.
     * @return a Set containing all algorithms currently
     * available.
     */
    Set<PartialScore> createAll();
}
