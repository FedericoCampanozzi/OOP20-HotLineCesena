package hotlinecesena.model.score;

import java.util.Map;

import javafx.util.Pair;

public interface Score {

    Map<PartialType, Pair<Integer, Double>> getPartialScores();

    int getTotalScore();
}
