package hotlinecesena.model.score;

import java.util.Map;

import hotlinecesena.model.score.PartialScores.PartialType;

public interface Score {

    Map<PartialType, Integer> getPartialScores();
    
    int getTotalScore();
    
    void update(double timeElapsed);
}
