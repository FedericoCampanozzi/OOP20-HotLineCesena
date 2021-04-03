package hotlinecesena.controller.input;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toUnmodifiableSet;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import hotlinecesena.model.entities.actors.player.Command;
import hotlinecesena.model.entities.actors.player.PlayerAction;
import hotlinecesena.utilities.MathUtils;
import javafx.geometry.Point2D;
import javafx.util.Pair;

/**
 * 
 * Interpreter implementation.
 * 
 */
public final class InputInterpreterImpl implements InputInterpreter {

    private static final float DEADZONE = 65.0f;
    private final Map<Enum<?>, PlayerAction> bindings;
    private Point2D currentMouseCoords = Point2D.ZERO;

    public InputInterpreterImpl(final Map<Enum<?>, PlayerAction> bindings) {
        this.bindings = bindings;
    }

    @Override
    public Set<Command> interpret(final Pair<Set<Enum<?>>, Point2D> inputs, final Point2D spritePosition,
            final double deltaTime) {
        final Set<PlayerAction> actions = EnumSet.noneOf(PlayerAction.class);
        final Set<Command> commandsToDeliver = new HashSet<>();

        actions.addAll(convertBindings(inputs.getKey()));

        final Point2D newMovementDir = this.processMovementDirection(actions);
        if (!newMovementDir.equals(Point2D.ZERO)) {
            commandsToDeliver.add(p -> p.move(newMovementDir.multiply(deltaTime)));
        }

        final Point2D newMouseCoords = this.processMouseCoordinates(inputs.getValue(), spritePosition);
        if (!this.currentMouseCoords.equals(newMouseCoords)) {
            commandsToDeliver.add(p -> p.setAngle(MathUtils.mouseToDegrees(newMouseCoords)));
            this.currentMouseCoords = newMouseCoords;
        }

        commandsToDeliver.addAll(this.computeRemainingCommands(actions));

        return commandsToDeliver;
    }

    /**
     * Converts all bindings into PlayerActions.
     * @param inputs
     * @return
     */
    private Set<PlayerAction> convertBindings(Set<Enum<?>> inputs) {
        return inputs.stream()
            .filter(bindings::containsKey)
            .map(bindings::get)
            .collect(toCollection(() -> EnumSet.noneOf(PlayerAction.class)));
    }

    /**
     * Computes the new movement direction while also normalizing it, if necessary.
     * @param actions
     * @return
     */
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

    /**
     * Tweaks coordinates depending on the cursor's position relative
     * to the player's sprite position on screen.
     * Ignores mouse movement when too close to the sprite.
     * @param mouseCoords
     * @param spritePosition
     * @return
     */
    private Point2D processMouseCoordinates(Point2D mouseCoords, Point2D spritePosition) {
        if (spritePosition.distance(mouseCoords.getX(), mouseCoords.getY()) > DEADZONE) {
            return new Point2D(mouseCoords.getX() - spritePosition.getX(),
                               mouseCoords.getY() - spritePosition.getY());
        }
        return this.currentMouseCoords;
    }

    /**
     * Converts all remaining PlayerActions into Commands.
     * @param actions
     * @return
     */
    private Set<Command> computeRemainingCommands(Set<PlayerAction> actions) {
        return actions.stream()
            .filter(a -> a.getCommand().isPresent())
            .map(a -> a.getCommand().get())
            .collect(toUnmodifiableSet());
    }
}
