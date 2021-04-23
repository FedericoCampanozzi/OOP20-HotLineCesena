package hotlinecesena.model.score.partials;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.entities.actors.player.Player;

/**
 * Implements the "Time bonus" algorithm: it rewards players who
 * manage to win a game within a certain time limit.
 * If the player loses, no points will be awarded.
 */
public final class TimePartial implements PartialScore {

    private final int basePoints;
    /**
     * Based on the generated map's dimensions.
     */
    private final long timeCeiling;
    private final long start = System.currentTimeMillis();
    private long totalTimeMilliseconds;

    /**
     * Instantiates a new TimeStrategy.
     * @param basePoints starting points for this algorithm.
     */
    public TimePartial(final int basePoints) {
        this.basePoints = basePoints;
        final long mapWidth = JSONDataAccessLayer.getInstance().getWorld().getMaxX()
                - JSONDataAccessLayer.getInstance().getWorld().getMinX() + 1;
        final long mapHeight = JSONDataAccessLayer.getInstance().getWorld().getMaxY()
                - JSONDataAccessLayer.getInstance().getWorld().getMinY() + 1;
        timeCeiling = (mapWidth + mapHeight) / 2;
    }

    @Override
    public int applyFormula() {
        totalTimeMilliseconds = System.currentTimeMillis() - start;
        final Player player = JSONDataAccessLayer.getInstance().getPlayer().getPly();
        final long totalSeconds = totalTimeMilliseconds / 1000;
        final long remainingSeconds = totalSeconds >= timeCeiling || player.getActorStatus() == ActorStatus.DEAD ? 0
                : timeCeiling - totalSeconds;
        return (int) (basePoints * remainingSeconds);
    }

    /**
     * Return time in milliseconds.
     */
    @Override
    public int getRelevantFactor() {
        return (int) totalTimeMilliseconds;
    }
}
