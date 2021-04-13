package hotlinecesena.model.score;

import hotlinecesena.model.dataccesslayer.DataAccessLayer;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.events.Subscriber;

public abstract class AbstractPartial implements PartialScore, Subscriber {

    private final String name;
    private final int pointsPerUnit;

    protected AbstractPartial(final String name, final int points) {
        this.name = name;
        pointsPerUnit = points;
    }

    @Override
    public final String getName() {
        return name;
    }

    /**
     * Template method that depends on formula().
     */
    @Override
    public final int getPartialPoints() {
        return pointsPerUnit * this.formula();
    }

    protected abstract int formula();

    protected final DataAccessLayer getGameMaster() {
        return JSONDataAccessLayer.getInstance();
    }
}
