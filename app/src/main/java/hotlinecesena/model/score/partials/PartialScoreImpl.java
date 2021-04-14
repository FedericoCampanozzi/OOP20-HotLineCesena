package hotlinecesena.model.score;

import hotlinecesena.model.dataccesslayer.DataAccessLayer;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.events.Subscriber;

public abstract class AbstractPartial implements PartialScore, Subscriber {

    private final PartialType type;

    protected AbstractPartial(final PartialType type) {
        this.type = type;
    }

    @Override
    public final PartialType getType() {
        return type;
    }

    @Override
    public final int getPartialPoints() {
        return (int) (type.getPointsPerUnit() * this.formula());
    }

    /**
     * Template method that depends on formula().
     */
    @Override
    public final double getFactor() {
        return this.formula();
    }

    protected abstract double formula();

    protected final DataAccessLayer getGameMaster() {
        return JSONDataAccessLayer.getInstance();
    }
}
