package hotlinecesena.model.score;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import hotlinecesena.model.events.Subscriber;

public final class ScoreImpl implements Score, Subscriber {

    private final Set<PartialScore> partials;

    public ScoreImpl(final PartialScoreFactory factory) {
        partials = factory.createAll();
    }

    @Override
    public Map<String, Integer> getPartialScores() {
        return partials.stream()
                .collect(Collectors.toMap(PartialScore::getName, PartialScore::getPartialPoints));
    }

    @Override
    public int getTotalScore() {
        return partials.stream()
                .mapToInt(PartialScore::getPartialPoints)
                .sum();
    }
}
