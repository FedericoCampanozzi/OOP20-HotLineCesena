package hotlinecesena.view.input;

import java.util.Set;
import org.apache.commons.lang3.tuple.Triple;
import javafx.geometry.Point2D;

/**
 * 
 * Listens for inputs from the View. Its implementations are bound to be View-specific, meaning
 * a new implementation will be needed when switching to a different graphics library.
 *
 * @param <K> keyboard key codes
 * @param <M> mouse button codes
 */
public interface InputListener<K extends Enum<K>, M extends Enum<M>> {

    /**
     * Delivers sets of raw inputs coming from the View.
     * @param spritePosition current position of the player's sprite on screen.
     * Used to adjust player rotation.
     * @return a {@link Triple} containing sets of raw inputs.
     */
    Triple<Set<K>, Set<M>, Point2D> deliverInputs();
}
