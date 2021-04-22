package hotlinecesena.model.score.partials;

import java.util.Set;

/**
 * Models a factory for {@link PartialStrategy}.
 */
public interface PartialStrategyFactory {

    /**
     * Creates all strategies currently available and
     * bundles them in a Set.
     * @return a Set containing all strategies currently
     * available.
     */
    Set<PartialStrategy> createAll();
}
