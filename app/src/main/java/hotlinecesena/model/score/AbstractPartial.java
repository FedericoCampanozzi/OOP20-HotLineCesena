package hotlinecesena.model.score;

public abstract class AbstractPartial implements PartialScore {

    private final String name;
    private final int pointsPerUnit;
    //    private final DAL world = DALImpl.getInstance();

    protected AbstractPartial(final String name, final int points) {
        this.name = name;
        pointsPerUnit = points;
    }

    @Override
    public final String getName() {
        return name;
    }

    /**
     * Template method
     */
    @Override
    public final int getPartialPoints() {
        return pointsPerUnit * this.formula();
    }

    protected abstract int formula();

    //    protected DAL getWorld() {
    //        return this.world;
    //    }
}
