package hotlinecesena.model.score;

import java.util.Map;

public interface Score {

    Map<String, Integer> getPartialScores();

    int getTotalScore();
}
