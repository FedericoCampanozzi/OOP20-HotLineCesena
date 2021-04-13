package hotlinecesena.model.score;

public enum PartialType {

    KILLS("Kills", 100.0),
    TIME("Time bonus", 5.0),
    CUNNING("Cunning", 500.0);

    private String name;
    private double pointsPerUnit;

    PartialType(final String name, final double ppu) {
        this.name = name;
        pointsPerUnit = ppu;
    }

    public String getName() {
        return name;
    }

    public double getPointsPerUnit() {
        return pointsPerUnit;
    }
}
