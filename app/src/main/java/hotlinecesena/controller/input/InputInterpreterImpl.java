package hotlinecesena.controller.input;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.geometry.Point2D;
import javafx.util.Pair;

public class InputInterpreterImpl<C extends Enum<C>, K extends Enum<K>, M extends Enum<M>>
    implements InputInterpreter<C, K, M> {

    private final Map<K, C> keyBindings;
    private final Map<M, C> mouseBindings;
    private final InputListener<K, M> listener;

    public InputInterpreterImpl(final Map<K, C> keyBindings, final Map<M, C> mouseBindings,
            final InputListener<K, M> listener) {
        this.keyBindings = keyBindings;
        this.mouseBindings = mouseBindings;
        this.listener = listener;
    }

    @Override
    public Pair<Set<C>, Point2D> interpret() {
        final var receivedInputs = this.listener.deliverInputs();
        final Set<C> outSet = new HashSet<>();

        outSet.addAll(convertBindings(receivedInputs.getLeft(), this.keyBindings));
        outSet.addAll(convertBindings(receivedInputs.getMiddle(), this.mouseBindings));
        return new Pair<>(outSet, receivedInputs.getRight());
    }

    private <X extends Enum<X>> Set<C> convertBindings(Set<X> inputs, Map<X, C> bindings) {
        return inputs.stream()
            .filter(bindings::containsKey)
            .map(bindings::get)
            .collect(Collectors.toUnmodifiableSet());
    }
}
