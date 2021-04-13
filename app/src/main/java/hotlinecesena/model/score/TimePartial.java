package hotlinecesena.model.score;

public final class TimePartial extends AbstractPartial {

    private final long timeCeiling;
    private final long start = System.currentTimeMillis();

    protected TimePartial() {
        super(PartialType.TIME);
        final long mapWidth = this.getGameMaster().getWorld().getMaxX()
                - this.getGameMaster().getWorld().getMinX() + 1;
        final long mapHeight = this.getGameMaster().getWorld().getMaxY()
                - this.getGameMaster().getWorld().getMinY() + 1;
        timeCeiling = (mapWidth + mapHeight) / 2 + 1;
    }

    @Override
    protected double formula() {
        final long totalTime = (System.currentTimeMillis() - start) / 1000;
        return timeCeiling <= totalTime ? 0 : timeCeiling - totalTime;
    }
}
