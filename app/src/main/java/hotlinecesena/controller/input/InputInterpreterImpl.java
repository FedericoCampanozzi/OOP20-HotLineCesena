package hotlinecesena.controller.input;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Triple;

import hotlinecesena.model.entities.actors.player.Command;
import hotlinecesena.model.entities.actors.player.PlayerAction;
import hotlinecesena.utilities.MathUtils;
import javafx.geometry.Point2D;

/**
 * 
 * Interpreter implementation.
 *
 * @param <K> keyboard key codes
 * @param <M> mouse button codes
 */
public final class InputInterpreterImpl<K extends Enum<K>, M extends Enum<M>>
    implements InputInterpreter<K, M> {

    private static final float DEADZONE = 65.0f;
    private final Map<K, PlayerAction> keyBindings;
    private final Map<M, PlayerAction> mouseBindings;
    private Point2D currentMouseCoords = Point2D.ZERO;

    public InputInterpreterImpl(final Map<K, PlayerAction> keyBindings, final Map<M, PlayerAction> mouseBindings) {
        this.keyBindings = keyBindings;
        this.mouseBindings = mouseBindings;
    }

    @Override
    public Set<Command> interpret(final Triple<Set<K>, Set<M>, Point2D> inputs, final Point2D spritePosition,
            final double deltaTime) {
        final Set<PlayerAction> actions = new HashSet<>();
        final Set<Command> commandsToDeliver = new HashSet<>();

        actions.addAll(convertBindings(inputs.getLeft(), this.keyBindings));
        actions.addAll(convertBindings(inputs.getMiddle(), this.mouseBindings));

        // Compute normalized movement direction
        final Point2D newMovementDir = this.processMovementDirection(actions);
        if (!newMovementDir.equals(Point2D.ZERO)) {
            commandsToDeliver.add(p -> p.move(newMovementDir.multiply(deltaTime)));
        }

        // Compute new angle
        final Point2D newMouseCoords = this.processMouseCoordinates(inputs.getRight(), spritePosition);
        if (!this.currentMouseCoords.equals(newMouseCoords)) {
            commandsToDeliver.add(p -> p.setAngle(MathUtils.mouseToDegrees(newMouseCoords)));
            this.currentMouseCoords = newMouseCoords;
        }

        // Add remaining commands
        commandsToDeliver.addAll(this.computeRemainingCommands(actions));

        return commandsToDeliver;
    }

    // Converts all bindings into Commands
    private <X extends Enum<X>> Set<PlayerAction> convertBindings(Set<X> inputs, Map<X, PlayerAction> bindings) {
        return inputs.stream()
            .filter(bindings::containsKey)
            .map(bindings::get)
            .collect(Collectors.toUnmodifiableSet());
    }

    // Computes the new direction
    private Point2D processMovementDirection(Set<PlayerAction> actions) {
        Point2D direction = actions.stream()
                .filter(a -> a.getDirection().isPresent())
                .map(a -> a.getDirection().get().get())
                .reduce(Point2D.ZERO, Point2D::add);
        final double magnitude = direction.magnitude();
        if (magnitude > 1) {
            direction = MathUtils.normalizeWithMagnitude(direction, magnitude);
        }
        return direction;
    }

    // Ignores mouse movement when too close to the player's sprite
    private Point2D processMouseCoordinates(Point2D mouseCoords, Point2D spritePosition) {
        if (spritePosition.distance(mouseCoords.getX(), mouseCoords.getY()) > DEADZONE) {
            return new Point2D(mouseCoords.getX() - spritePosition.getX(),
                               mouseCoords.getY() - spritePosition.getY());
        }
        return this.currentMouseCoords;
    }

    // Converts all remaining PlayerActions into Commands
    private Set<Command> computeRemainingCommands(Set<PlayerAction> actions) {
        return actions.stream()
            .filter(a -> a.getCommand().isPresent())
            .map(a -> a.getCommand().get())
            .collect(Collectors.toUnmodifiableSet());
    }
}
