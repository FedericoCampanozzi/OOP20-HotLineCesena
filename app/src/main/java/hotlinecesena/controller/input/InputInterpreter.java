package hotlinecesena.controller.input;

import java.util.Set;
import hotlinecesena.model.entities.actors.player.Command;
import javafx.geometry.Point2D;
import javafx.util.Pair;

/**
 * <p>
 * Converts keyboard and mouse inputs received from the View into a set of commands
 * the Model can understand.
 * <br>
 * Its implementations must be View-agnostic, meaning they must work with virtually
 * any graphics library without needing any modifications.
 * To achieve this, implementations must work alongside an InputListener.
 * </p>
 * Not to be confused with the Interpreter pattern.
 *
 */
public interface InputInterpreter {

    /**
     * 
     * @param inputs raw inputs captured by the {@link InputListener}.
     * @param spritePosition position of the player's sprite on the screen. Used to
     * prevent unwanted rotations when the cursor is too close to the player.
     * @param deltaTime time elapsed since the last frame.
     * @return a set of {@link Command}s that an external controller may execute.
     */
    Set<Command> interpret(Pair<Set<Enum<?>>, Point2D> inputs, Point2D spritePosition, double deltaTime);
}
