package hotlinecesena.view.input;

import java.util.Set;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.util.Pair;

/**
 *
 * Listens for keyboard and mouse inputs from the View.
 * <br>
 * It is bound to be View-specific: slight modifications will need
 * to be made to this interface's methods when switching to a different
 * graphics library.
 *
 */
public interface InputListener {

    /**
     * Delivers sets of raw inputs coming from the View.
     * @return a {@link Pair} containing sets of raw inputs.
     */
    Pair<Set<Enum<?>>, Point2D> deliverInputs();

    /**
     *
     * Binds keyboard and mouse event handlers to the given {@link Scene}.
     * @param node the JavaFX {@code Scene} to which the event handlers
     * will be set.
     */
    void setEventHandlers(Scene scene);

    /**
     * Removes this listener's event handlers from the given {@link Scene},
     * if it contains them.
     * @param node the JavaFX {@code Scene} from which this listener's
     * event handlers will be removed.
     */
    void removeEventHandlersFrom(Scene scene);
}
