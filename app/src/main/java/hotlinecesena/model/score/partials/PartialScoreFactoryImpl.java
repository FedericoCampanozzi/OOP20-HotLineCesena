package hotlinecesena.model.score.partials;

import java.util.Set;

/**
 * {@link PartialScoreFactory} implementation.
 */
public final class PartialScoreFactoryImpl implements PartialScoreFactory {

    private PartialScore createCunning() {
        final int ppu = 500;
        return new CunningPartial(ppu);
    }

    private PartialScore createTime() {
        final int ppu = 5;
        return new TimePartial(ppu);
    }

    private PartialScore createKillCount() {
        final int ppu = 50;
        return new KillCountPartial(ppu);
    }

    @Override
    public Set<PartialScore> createAll() {
        return Set.of(
                this.createCunning(),
                this.createTime(),
                this.createKillCount()
                );
    }
}
