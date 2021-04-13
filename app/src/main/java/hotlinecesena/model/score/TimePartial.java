package hotlinecesena.model.score;

public final class TimePartial extends AbstractPartial {

    private static final String NAME = "Time bonus";
    private static final int POINTS = 5;
    private final int timeCeiling;
    private final long start = System.currentTimeMillis();

    protected TimePartial() {
        super(NAME, POINTS);
        final int mapWidth = this.getGameMaster().getWorld().getMaxX() - this.getGameMaster().getWorld().getMinX() + 1;
        final int mapHeight = this.getGameMaster().getWorld().getMaxY() - this.getGameMaster().getWorld().getMinY() + 1;
        timeCeiling = mapWidth + mapHeight;
    }

    @Override
    protected int formula() {
        final long totalTime = System.currentTimeMillis() - start;
        return (int) (timeCeiling <= totalTime ? 0 : timeCeiling - totalTime);
    }
}
