package hotlinecesena.view.input;

import java.util.EnumSet;
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

    private final Set<KeyCode> keyboardInputs = EnumSet.noneOf(KeyCode.class);
    private final Set<MouseButton> mouseInputs = EnumSet.noneOf(MouseButton.class);
    private Point2D currentMouseCoords = Point2D.ZERO;

    /**
     * Instantiates a new InputListenerFX while also binding keyboard and mouse
     * event handlers to the {@link Scene} passed as argument.
     * @param scene the {@code Scene} on which the event handlers will be set.
     */
    public InputListenerFX(final Scene scene) {
        this.setKeyEventHandlers(scene);
        this.setMouseButtonHandlers(scene);
        this.setMouseMovementHandlers(scene);
    }

    @Override
    public Pair<Set<Enum<?>>, Point2D> deliverInputs() {
        return new Pair<>(this.combineInputs(), currentMouseCoords);
    }

    /**
     * Combines sets of {@code KeyCode}s and {@code MouseButton}s into a single
     * {@code Set<Enum<?>>}.
     * @return
     */
    private Set<Enum<?>> combineInputs() {
        return Stream.concat(keyboardInputs.stream(), mouseInputs.stream())
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Handles key presses and key releases.
     * @param scene
     */
    private void setKeyEventHandlers(final Scene scene) {
        scene.setOnKeyReleased(e -> this.forgetInput(keyboardInputs, e.getCode()));
        scene.setOnKeyPressed(e -> this.captureInput(keyboardInputs, e.getCode()));
    }

    /**
     * Handles mouse button presses and releases.
     * @param scene
     */
    private void setMouseButtonHandlers(final Scene scene) {
        scene.setOnMouseReleased(e -> this.forgetInput(mouseInputs, e.getButton()));
        scene.setOnMousePressed(e -> this.captureInput(mouseInputs, e.getButton()));
    }

    /**
     * Handles mouse movement.
     * @param scene
     */
    private void setMouseMovementHandlers(final Scene scene) {
        //Mouse moved, no buttons pressed
        scene.setOnMouseMoved(this.captureMouseMovement());
        //Mouse moved while buttons pressed
        scene.setOnMouseDragged(this.captureMouseMovement());
    }

    /**
     * Event handler common to setOnMouseMoved and setOnMouseDragged properties.
     * @return an {@link EventHandler} for capturing mouse coordinates.
     */
    private EventHandler<MouseEvent> captureMouseMovement() {
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
