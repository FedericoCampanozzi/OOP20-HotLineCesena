package hotlinecesena.model.score;

import java.util.Set;

public final class PartialScoreFactoryImpl implements PartialScoreFactory {

    private AccuracyPartial createAccuracyPartial() {
        return new AccuracyPartial();
    }

    private KillCountPartial createKillCountPartial() {
        return new KillCountPartial();
    }

    private TimePartial createTimePartial() {
        return new TimePartial();
    }

    @Override
    public Set<PartialScore> createAll() {
        return Set.of(
                this.createAccuracyPartial(),
                this.createKillCountPartial(),
                this.createTimePartial()
                );
    }
}
