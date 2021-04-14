package hotlinecesena.model.score.partials;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;

public final class TimeStrategy implements PartialStrategy {

    private final int basePoints;
    private final long timeCeiling;
    private final long start = System.currentTimeMillis();
    private long totalTime;

    /**
     * Instantiates a new TimeStrategy.
     * @param basePoints starting points for this algorithm.
     */
    public TimeStrategy(final int basePoints) {
        this.basePoints = basePoints;
        final long mapWidth = JSONDataAccessLayer.getInstance().getWorld().getMaxX()
                - JSONDataAccessLayer.getInstance().getWorld().getMinX() + 1;
        final long mapHeight = JSONDataAccessLayer.getInstance().getWorld().getMaxY()
                - JSONDataAccessLayer.getInstance().getWorld().getMinY() + 1;
        timeCeiling = (mapWidth + mapHeight) / 2;
    }

    @Override
    public int applyFormula() {
        totalTime = (System.currentTimeMillis() - start) / 1000;
        return (int) (basePoints * (timeCeiling <= totalTime ? 0 : timeCeiling - totalTime));
    }

    @Override
    public int getRelevantFactor() {
        return (int) totalTime;
    }
}
