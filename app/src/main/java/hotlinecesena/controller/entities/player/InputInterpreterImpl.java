package hotlinecesena.controller.entities.player;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toUnmodifiableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nonnull;

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
    private final Map<Enum<?>, PlayerAction> bindings;
    private Point2D currentMouseCoords = Point2D.ZERO;

    /**
     * Instantiates a new {@code InputInterpreterImpl} that will
     * make use of the given input bindings.
     * @param bindings a Map which associates keyboard keys and/or
     * mouse buttons to {@link PlayerAction}s.
     * @throws NullPointerException if the given {@code bindings} is null.
     */
    public InputInterpreterImpl(@Nonnull final Map<Enum<?>, PlayerAction> bindings) {
        this.bindings = Objects.requireNonNull(bindings);
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
        final Set<PlayerAction> actions = this.convertBindings(inputs.getKey());

        /*
         * Compute new movement direction
         */
        final Point2D newMovementDir = this.processMovementDirection(actions);
        if (!newMovementDir.equals(Point2D.ZERO)) {
            commandsToDeliver.add(p -> p.move(newMovementDir.multiply(deltaTime)));
        }

        /*
         * Compute new angle (or don't, if mouse coordinates haven't changed).
         */
        final Point2D newMouseCoords = this.processMouseCoordinates(inputs.getValue(), spritePosition);
        if (!currentMouseCoords.equals(newMouseCoords)) {
            commandsToDeliver.add(p -> p.setAngle(MathUtils.mouseToDegrees(newMouseCoords)));
            currentMouseCoords = newMouseCoords;
        }

        /*
         * Compute all other remaining actions
         */
        commandsToDeliver.addAll(this.computeRemainingCommands(actions));

        return commandsToDeliver;
    }

    /**
     * Converts all bindings into PlayerActions.
     */
    private Set<PlayerAction> convertBindings(final Set<Enum<?>> inputs) {
        return inputs.stream()
                .filter(bindings::containsKey)
                .map(bindings::get)
                .collect(toCollection(() -> EnumSet.noneOf(PlayerAction.class)));
    }

    /**
     * Computes the new movement direction while also normalizing it.
     */
    private Point2D processMovementDirection(final Set<PlayerAction> actions) {
        return actions.stream()
                .map(PlayerAction::getDirection)
                .flatMap(Optional::stream)
                .map(Direction::get)
                .reduce(Point2D.ZERO, Point2D::add)
                .normalize();
    }

    /**
     * Tweaks mouse coordinates depending on the player's sprite
     * position on screen.
     * Ignores mouse movement when too close to the sprite.
     */
    private Point2D processMouseCoordinates(final Point2D mouseCoords, final Point2D spritePosition) {
        if (spritePosition.distance(mouseCoords.getX(), mouseCoords.getY()) > DEADZONE) {
            return mouseCoords.subtract(spritePosition);
        }
        return currentMouseCoords;
    }

    /**
     * Converts all remaining PlayerActions into Commands.
     */
    private List<Command> computeRemainingCommands(final Set<PlayerAction> actions) {
        return actions.stream()
                .map(PlayerAction::getCommand)
                .flatMap(Optional::stream)
                .collect(toUnmodifiableList());
    }
}
