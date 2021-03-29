package hotlinecesena.controller.input;

import java.util.Set;

import hotlinecesena.model.entities.actors.player.Command;
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
     * @return interpreted commands received from the View.
     */
    Set<Command> interpret(double deltaTime);
}
