package hotlinecesena.controller.input;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import hotlinecesena.model.entities.actors.player.Command;
import hotlinecesena.model.entities.actors.player.PlayerAction;
import hotlinecesena.utilities.MathUtils;
import javafx.geometry.Point2D;

/**
 * 
 * Interpreter implementation. Made it as generic as humanly possible.
 *
 * @param <K> keyboard key codes
 * @param <M> mouse button codes
 */
public final class InputInterpreterImpl<K extends Enum<K>, M extends Enum<M>>
    implements InputInterpreter<K, M> {

    private final Map<K, PlayerAction> keyBindings;
    private final Map<M, PlayerAction> mouseBindings;
    private final InputListener<K, M> listener;

    public InputInterpreterImpl(final Map<K, PlayerAction> keyBindings, final Map<M, PlayerAction> mouseBindings,
            final InputListener<K, M> listener) {
        this.keyBindings = keyBindings;
        this.mouseBindings = mouseBindings;
        this.listener = listener;
    }

    @Override
    public Set<Command> interpret(double deltaTime) {
        final var receivedInputs = this.listener.deliverInputs();
        final Set<PlayerAction> actions = new HashSet<>();
        final Set<Command> commandsToDeliver = new HashSet<>();

        actions.addAll(convertBindings(receivedInputs.getLeft(), this.keyBindings));
        actions.addAll(convertBindings(receivedInputs.getMiddle(), this.mouseBindings));

        // Compute normalized movement direction
        final Point2D newMovementDir = this.processMovementDirection(actions);
        if (!newMovementDir.equals(Point2D.ZERO)) {
            commandsToDeliver.add(p -> p.move(newMovementDir.multiply(deltaTime)));
        }

        // Compute new angle (radians)
        commandsToDeliver.add(p -> p.setAngle(MathUtils.convertIntoRadians(receivedInputs.getRight())));

        // Add remaining commands
        commandsToDeliver.addAll(this.computeRemainingCommands(actions));

        return commandsToDeliver;
    }

    private <X extends Enum<X>> Set<PlayerAction> convertBindings(Set<X> inputs, Map<X, PlayerAction> bindings) {
        return inputs.stream()
            .filter(bindings::containsKey)
            .map(bindings::get)
            .collect(Collectors.toUnmodifiableSet());
    }

    private Point2D processMovementDirection(Set<PlayerAction> actions) {
        return actions.stream()
            .filter(a -> a.getDirection().isPresent())
            .map(a -> a.getDirection().get().get())
            .reduce(Point2D.ZERO, Point2D::add)
            .normalize();
    }

    private Set<Command> computeRemainingCommands(Set<PlayerAction> actions) {
        return actions.stream()
                .filter(a -> a.getCommand().isPresent())
                .map(a -> a.getCommand().get())
                .collect(Collectors.toUnmodifiableSet());
    }
}
