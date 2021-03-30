package hotlinecesena.controller.input;

import java.util.Set;

import org.apache.commons.lang3.tuple.Triple;

import hotlinecesena.model.entities.actors.player.Command;
import javafx.geometry.Point2D;
/**
 * <p>
 * Converts keyboard and mouse inputs received from the View into a set of commands
 * the Model can understand.
 * <br>
 * Its implementations must be View-agnostic, meaning they must work with virtually
 * any graphics library without needing any modifications.
 * </p>
 * To achieve this, implementations must work alongside an InputListener.
 *
 * @param <K> keyboard key codes
 * @param <M> mouse button codes
 */
public interface InputInterpreter<K extends Enum<K>, M extends Enum<M>> {

    /**
     * 
     * @return interpreted commands received from the InputListener.
     */
    Set<Command> interpret(Triple<Set<K>, Set<M>, Point2D> inputs, final Point2D spritePosition, double deltaTime);
}
