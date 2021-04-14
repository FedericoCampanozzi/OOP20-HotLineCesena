package hotlinecesena.model.score.partials;

import java.util.Set;

public final class PartialStrategyFactoryImpl implements PartialStrategyFactory {

    private PartialStrategy createCunning() {
        final int ppu = 500;
        return new CunningStrategy(ppu);
    }

    private PartialStrategy createTime() {
        final int ppu = 5;
        return new TimeStrategy(ppu);
    }

    private PartialStrategy createKillCount() {
        final int ppu = 50;
        return new KillCountStrategy(ppu);
    }

    @Override
    public Set<PartialStrategy> createAll() {
        return Set.of(
                this.createCunning(),
                this.createTime(),
                this.createKillCount()
                );
    }
}
