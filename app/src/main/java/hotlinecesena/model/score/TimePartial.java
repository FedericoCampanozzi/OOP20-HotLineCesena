package hotlinecesena.model.score;

public final class TimePartial extends AbstractPartial {

    private static final String NAME = "Time bonus";
    private static final int POINTS = 5;
    //    private final int timeCeiling;

    protected TimePartial() {
        super(NAME, POINTS);
        //        this.timeCeiling = getWorld().getMapSize() / 5;
    }

    @Override
    protected int formula() {
        return 0;
        //        return timeCeiling <= getWorld().getTotalTime() ? 0 : timeCeiling - getWorld().getTotalTime();
    }
}
