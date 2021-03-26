package hotlinecesena.controller.input;

import java.util.Set;
import org.apache.commons.lang3.tuple.Triple;
import javafx.geometry.Point2D;

/**
 * 
 * Listens for inputs from the View. Its implementations are bound to be View-specific, meaning
 * that a new implementation will be needed when switching to a different graphics library.
 *
 * @param <K> keyboard key codes
 * @param <M> mouse button codes
 */
public interface InputListener<K extends Enum<K>, M extends Enum<M>> {

    /**
     * Delivers in a "raw" form all inputs coming from the View.
     * @return a {@code Triple} containing sets of raw inputs.
     */
    Triple<Set<K>, Set<M>, Point2D> deliverInputs();
}
