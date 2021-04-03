package hotlinecesena.model.score;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import hotlinecesena.model.events.Subscriber;

public class ScoreImpl implements Score, Subscriber {

    private final Set<PartialScore> partials;

    public ScoreImpl(final PartialScoreFactory factory) {
        this.partials = factory.createAll();
    }

    @Override
    public Map<String, Integer> getPartialScores() {
        return this.partials.stream()
                .collect(Collectors.toMap(PartialScore::getName, PartialScore::getPartialPoints));
    }

    @Override
    public int getTotalScore() {
        return this.partials.stream()
                .mapToInt(PartialScore::getPartialPoints)
                .sum();
    }
}
