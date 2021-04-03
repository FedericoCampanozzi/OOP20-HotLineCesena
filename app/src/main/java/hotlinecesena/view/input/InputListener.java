package hotlinecesena.view.input;

import java.util.Set;
import javafx.geometry.Point2D;
import javafx.util.Pair;

/**
 * 
 * Listens for inputs from the View. Its implementations are bound to be View-specific, meaning
 * a new implementation will be needed when switching to a different graphics library.
 *
 */
public interface InputListener {

    /**
     * Delivers sets of raw inputs coming from the View.
     * @param spritePosition current position of the player's sprite on screen.
     * Used to adjust player rotation.
     * @return a {@link Pair} containing sets of raw inputs.
     */
    Pair<Set<Enum<?>>, Point2D> deliverInputs();
}
