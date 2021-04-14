package hotlinecesena.model.score;

import java.util.Map;

import javafx.util.Pair;

/**
 * Models the game's score computation logics.
 */
public interface Score {

    /**
     * Returns a Map containing all partial scores and their
     * relevant factors used in calculations.
     * @return a Map containing all partial scores and their
     * relevant factors.
     */
    Map<String, Pair<Integer, Integer>> getPartialScores();

    /**
     * Returns the total score achieved at the end of the
     * current game.
     * @return the total score achieved at the end of the
     * current game.
     */
    int getTotalScore();
}
