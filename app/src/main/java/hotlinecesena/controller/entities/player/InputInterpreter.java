package hotlinecesena.controller.entities.player;

import java.util.Collection;
import java.util.Set;

import hotlinecesena.controller.entities.player.command.Command;
import hotlinecesena.model.entities.actors.player.Player;
import javafx.geometry.Point2D;
import javafx.util.Pair;

/**
 * <p>
 * Converts keyboard and mouse inputs received from the View into a collection
 * of commands the {@link Player} can understand.
 * <br>
 * Its implementations must be View-agnostic, meaning they must work with virtually
 * any graphics library without needing any modifications.
 * </p>
 * Despite the name, it's not an application of the Interpreter pattern.
 *
 */
public interface InputInterpreter {

    /**
     * Interprets inputs received from the View and converts them to
     * {@link Command}s for a {@link Player} controller to execute.
     * @param inputs raw inputs captured by the View.
     * @param spritePosition position of the player's sprite on the screen.
     * @param deltaTime time elapsed since the last frame.
     * @return a set of {@code Command}s an external player controller
     * may execute.
     */
    Collection<Command> interpret(Pair<Set<Enum<?>>, Point2D> inputs, Point2D spritePosition, double deltaTime);
}
