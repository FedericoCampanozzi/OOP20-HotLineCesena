package hotlinecesena.controller.input;

import java.util.Set;

import javafx.geometry.Point2D;
import javafx.util.Pair;

/**
 * <p>
 * Converts keyboard and mouse inputs received from the View into a set of commands
 * which the Model can understand.
 * <br>
 * Its implementations must be View-agnostic, meaning that they must work with virtually
 * any graphics library without needing any modifications.
 * </p>
 * To achieve this, implementations must work alongside an InputListener.
 *
 * @param <C> set of commands that the Model can understand
 * @param <K> keyboard key codes
 * @param <M> mouse button codes
 */
public interface InputInterpreter<C extends Enum<C>, K extends Enum<K>, M extends Enum<M>> {

    /**
     * 
     * @return interpreted commands received from the View.
     */
    Pair<Set<C>, Point2D> interpret();
}
