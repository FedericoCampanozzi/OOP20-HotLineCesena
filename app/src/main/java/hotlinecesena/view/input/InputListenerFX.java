package hotlinecesena.view.input;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

/**
 *
 * Specific implementation created to listen for inputs in JavaFX.
 *
 */
public final class InputListenerFX implements InputListener {

    private final Scene scene;
    private final Set<KeyCode> keyboardInputs = EnumSet.noneOf(KeyCode.class);
    private final Set<MouseButton> mouseInputs = EnumSet.noneOf(MouseButton.class);
    private Point2D currentMouseCoords = Point2D.ZERO;

    /**
     * Instantiates a new InputListenerFX while also binding keyboard and mouse
     * event handlers to the given {@link Scene}.
     * @param scene the {@code Scene} on which the event handlers will be set.
     */
    public InputListenerFX(final Scene scene) {
        this.scene = Objects.requireNonNull(scene);
        this.setKeyEventHandlers();
        this.setMouseButtonHandlers();
        this.setMouseMovementHandlers();
    }

    @Override
    public Pair<Set<Enum<?>>, Point2D> deliverInputs() {
        return new Pair<>(this.combineInputs(), currentMouseCoords);
    }

    /**
     * Combines sets of {@code KeyCode}s and {@code MouseButton}s into a single
     * set of generic enums for simplicity.
     * @return a Set containing both keyboard and mouse inputs.
     */
    private Set<Enum<?>> combineInputs() {
        return Stream.concat(keyboardInputs.stream(), mouseInputs.stream())
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Sets event handlers for key presses and releases.
     */
    private void setKeyEventHandlers() {
        scene.setOnKeyReleased(e -> this.forgetInput(keyboardInputs, e.getCode()));
        scene.setOnKeyPressed(e -> this.captureInput(keyboardInputs, e.getCode()));
    }

    /**
     * Sets event handlers for mouse button presses and releases.
     */
    private void setMouseButtonHandlers() {
        scene.setOnMouseReleased(e -> this.forgetInput(mouseInputs, e.getButton()));
        scene.setOnMousePressed(e -> this.captureInput(mouseInputs, e.getButton()));
    }

    /**
     * Sets event handlers for mouse movement.
     */
    private void setMouseMovementHandlers() {
        //Mouse moved, no buttons pressed
        scene.setOnMouseMoved(this.captureMouseCoordinates());
        //Mouse moved while buttons pressed
        scene.setOnMouseDragged(this.captureMouseCoordinates());
    }

    /**
     * Event handler common to setOnMouseMoved and setOnMouseDragged properties.
     * @return an {@link EventHandler} for capturing mouse coordinates.
     */
    private EventHandler<MouseEvent> captureMouseCoordinates() {
        return e -> currentMouseCoords = new Point2D(e.getSceneX(), e.getSceneY());
    }

    private <T extends Enum<T>> void captureInput(final Set<T> field, final T code) {
        field.add(code);
    }

    private <T extends Enum<T>> void forgetInput(final Set<T> field, final T code) {
        if (field.contains(code)) {
            field.remove(code);
        }
    }
}
