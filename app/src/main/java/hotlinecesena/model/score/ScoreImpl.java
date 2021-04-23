package hotlinecesena.model.score;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import hotlinecesena.model.score.partials.PartialScore;
import hotlinecesena.model.score.partials.PartialScoreFactory;
import javafx.util.Pair;

/**
 * Score implementation. Calculations are left to each
 * {@link PartialScore} object created by the factory.
 */
public final class ScoreImpl implements Score {

    private final Set<PartialScore> partials;

    /**
     * Instantiates a new {@code ScoreImpl} which will make
     * use of the given {@code factory} to instantiate all
     * available partial scores.
     * @param factory the factory to be used to instantiate
     * all partial scores.
     */
    public ScoreImpl(@Nonnull final PartialScoreFactory factory) {
        partials = Objects.requireNonNull(factory).createAll();
    }

    /**
     * @implSpec Partials are identified by their class name.
     */
    @Override
    public Map<String, Pair<Integer, Integer>> getPartialScores() {
        return partials.stream()
                .collect(Collectors.toMap(
                        p -> p.getClass().getSimpleName(),
                        p -> new Pair<>(p.applyFormula(), p.getRelevantFactor())));
    }

    @Override
    public int getTotalScore() {
        return partials.stream()
                .mapToInt(PartialScore::applyFormula)
                .sum();
    }
}
