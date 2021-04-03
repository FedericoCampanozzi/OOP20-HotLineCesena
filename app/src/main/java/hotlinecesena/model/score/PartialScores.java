package hotlinecesena.model.score;

import java.util.Map;
import java.util.Map.Entry;

public class PartialScores {

    private static Entry<PartialType, Integer> createPartialEntry(final PartialType type, final int value) {
        return Map.entry(type, type.getPoints() * value);
    }

    public static Entry<PartialType, Integer> computeKillPartial(final int killCount, final int enemiesRemaining) {
        final int ratio = killCount / enemiesRemaining;
        return createPartialEntry(PartialType.KILLS, ratio);
    }

    public static Entry<PartialType, Integer> computeTimePartial(final double limit, final double secondsElapsed) {
        final double timeDelta = (limit <= secondsElapsed) ? 0 : (limit - secondsElapsed);
        return createPartialEntry(PartialType.TIME_BONUS, (int) Math.round(timeDelta));
    }

    public static Entry<PartialType, Integer> computeAccuracyPartial(final int hits, final int attacksPerformed) {
        final int ratio = hits / attacksPerformed;
        return createPartialEntry(PartialType.ACCURACY, ratio);
    }

    public enum PartialType {

        /**
         * 
         */
        KILLS("Kills", 100),

        /**
         * 
         */
        TIME_BONUS("Time bonus", 5),

        /**
         * 
         */
        ACCURACY("Accuracy", 1500);

        private String name;
        private int points;

        PartialType(final String name, final int points) {
            this.name = name;
            this.points = points;
        }

        public String getName() {
            return this.name;
        }

        public int getPoints() {
            return this.points;
        }
    }
}
