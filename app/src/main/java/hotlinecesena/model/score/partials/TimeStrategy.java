package hotlinecesena.model.score.partials;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.actors.ActorStatus;

/**
 * Implements the "Time bonus" algorithm: it rewards players who
 * manage to win a game within a certain time limit.
 * If the player loses, the algorithm will award no points.
 */
public final class TimeStrategy implements PartialStrategy {

    private final int basePoints;
    /**
     * Based on the generated map's dimensions.
     */
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
        timeCeiling = (mapWidth + mapHeight) / 3;
    }

    @Override
    public int applyFormula() {
        totalTime = System.currentTimeMillis() - start;
        final long totalSeconds = totalTime / 1000;
        return (int) (basePoints * (
                timeCeiling <= totalSeconds
                && JSONDataAccessLayer.getInstance().getPlayer().getPly().getActorStatus() != ActorStatus.DEAD ? 0
                        : timeCeiling - totalSeconds
                ));
    }

    /**
     * Return time in milliseconds.
     */
    @Override
    public int getRelevantFactor() {
        return (int) totalTime;
    }
}
