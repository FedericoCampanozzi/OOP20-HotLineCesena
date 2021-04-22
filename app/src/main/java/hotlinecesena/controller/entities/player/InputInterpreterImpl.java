package hotlinecesena.controller.entities.player;

import static java.util.stream.Collectors.toUnmodifiableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import hotlinecesena.controller.entities.player.command.Command;
import hotlinecesena.controller.entities.player.command.MoveCommand;
import hotlinecesena.controller.entities.player.command.RotateCommand;
import hotlinecesena.model.entities.actors.Direction;
import hotlinecesena.utilities.MathUtils;
import javafx.geometry.Point2D;
import javafx.util.Pair;

/**
 *
 * Interpreter implementation.
 *
 */
public final class InputInterpreterImpl implements InputInterpreter {

    private static final float DEADZONE = 50.0f;
    private Point2D currentMouseCoords = Point2D.ZERO;
    private final Map<Enum<?>, String> bindings;
    private final Map<String, Direction> movementActions;
    private final Map<String, Command> otherActions;
    private final Map<String, Command> doOnlyOnce;
    private final Set<String> onceBuffer = new HashSet<>();

    /**
     * Instantiates a new {@code InputInterpreterImpl} that will
     * make use of the given input bindings.
     * @param bindings a Map associating keyboard keys and/or
     * mouse buttons to strings representing player actions.
     * @param movementActions Map associating strings to player movements.
     * @param otherActions Map associating strings to player actions
     * excluding movements.
     * @param doOnlyOnce Map associating strings to actions that need to
     * be executed only once if the player keeps holding the same key.
     * @throws NullPointerException if the given bindings, movementActions
     * or otherActions are null.
     */
    public InputInterpreterImpl(@Nonnull final Map<Enum<?>, String> bindings,
            @Nonnull final Map<String, Direction> movementActions, @Nonnull final Map<String, Command> otherActions,
            @Nonnull final Map<String, Command> doOnlyOnce) {
        this.bindings = Objects.requireNonNull(bindings);
        this.movementActions = Objects.requireNonNull(movementActions);
        this.otherActions = Objects.requireNonNull(otherActions);
        this.doOnlyOnce = Objects.requireNonNull(doOnlyOnce);
    }

    /**
     * @implSpec {@code spritePosition} is used to prevent unwanted
     * rotations when the cursor is too close to the player.
     * @throws NullPointerException if the given {@code inputs} or
     * {@code spritePosition} are null.
     */
    @Override
    public Collection<Command> interpret(@Nonnull final Pair<Set<Enum<?>>, Point2D> inputs,
            @Nonnull final Point2D spritePosition, final double deltaTime) {
        Objects.requireNonNull(inputs);
        Objects.requireNonNull(spritePosition);
        final List<Command> commandsToDeliver = new ArrayList<>();
        final Set<String> actionsToProcess = this.convertBindings(inputs.getKey());

        /*
         * Compute new movement direction
         */
        final Point2D newMovementDir = this.processMovementDirection(actionsToProcess);
        if (!newMovementDir.equals(Point2D.ZERO)) {
            commandsToDeliver.add(new MoveCommand(newMovementDir.multiply(deltaTime)));
        }

        /*
         * Compute new angle (or don't, if mouse coordinates haven't changed).
         */
        final Point2D newMouseCoords = this.processMouseCoordinates(inputs.getValue(), spritePosition);
        if (!currentMouseCoords.equals(newMouseCoords)) {
            commandsToDeliver.add(new RotateCommand(MathUtils.mouseToDegrees(newMouseCoords)));
            currentMouseCoords = newMouseCoords;
        }

        /*
         * Compute actions that need to be executed only once if
         * the user keeps holding the assigned key
         */
        commandsToDeliver.addAll(this.processDoOnlyOnceActions(actionsToProcess));

        /*
         * Compute all other remaining actions
         */
        commandsToDeliver.addAll(this.processRemainingActions(actionsToProcess));

        return commandsToDeliver;
    }

    /*
     * Converts all bindings to PlayerActions.
     */
    private Set<String> convertBindings(final Set<Enum<?>> inputs) {
        return inputs.stream()
                .filter(bindings::containsKey)
                .map(bindings::get)
                .collect(Collectors.toSet());
    }

    private Point2D processMovementDirection(final Set<String> actions) {
        return movementActions.entrySet()
                .stream()
                .filter(e -> actions.contains(e.getKey()))
                .map(Entry::getValue)
                .map(Direction::get)
                .reduce(Point2D.ZERO, Point2D::add)
                .normalize();
    }

    /*
     * Tweaks mouse coordinates depending on the player's sprite
     * position on screen.
     * Ignores mouse movement when too close to the sprite.
     */
    private Point2D processMouseCoordinates(final Point2D mouseCoords, final Point2D spritePosition) {
        if (spritePosition.distance(mouseCoords) > DEADZONE) {
            return mouseCoords.subtract(spritePosition);
        }
        return currentMouseCoords;
    }

    private List<Command> processDoOnlyOnceActions(final Set<String> actions) {
        final List<Command> outList = new ArrayList<>();
        doOnlyOnce.forEach((actionName, command) -> {
            if (actions.contains(actionName) && !onceBuffer.contains(actionName)) {
                onceBuffer.add(actionName);
                outList.add(command);
            } else if (!actions.contains(actionName)) {
                onceBuffer.remove(actionName);
            }
        });
        return outList;
    }

    private List<Command> processRemainingActions(final Set<String> actions) {
        return otherActions.entrySet()
                .stream()
                .filter(e -> actions.contains(e.getKey()))
                .map(Entry::getValue)
                .collect(toUnmodifiableList());
    }
}
