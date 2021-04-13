package hotlinecesena.model.score;

import java.util.Map;

import javafx.util.Pair;

public interface Score {

    Map<String, Pair<Integer, Integer>> getPartialScores();

    int getTotalScore();
}
