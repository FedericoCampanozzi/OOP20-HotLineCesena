package hotlinecesena.model.score;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import hotlinecesena.model.events.Subscriber;
import javafx.util.Pair;

public final class ScoreImpl implements Score, Subscriber {

    private final Set<PartialScore> partials;

    public ScoreImpl(final PartialScoreFactory factory) {
        partials = factory.createAll();
    }

    @Override
    public Map<PartialType, Pair<Integer, Double>> getPartialScores() {
        return partials.stream()
                .collect(Collectors.toMap(
                        PartialScore::getType,
                        p -> new Pair<>(p.getPartialPoints(), p.getFactor())));
    }

    @Override
    public int getTotalScore() {
        return partials.stream()
                .mapToInt(PartialScore::getPartialPoints)
                .sum();
    }
}
