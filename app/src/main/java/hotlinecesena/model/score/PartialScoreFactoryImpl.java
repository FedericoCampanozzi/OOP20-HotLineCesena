package hotlinecesena.model.score;

import java.util.Set;

public final class PartialScoreFactoryImpl implements PartialScoreFactory {

    @Override
    public Set<PartialScore> createAll() {
        return Set.of(
                new CunningPartial(),
                new KillCountPartial(),
                new TimePartial()
                );
    }
}
