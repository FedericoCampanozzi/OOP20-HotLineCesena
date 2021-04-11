package hotlinecesena.view.input;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Nonnull;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

/**
 *
 * Listener implementation.
 *
 */
public final class InputListenerFX implements InputListener {

    private final Set<Enum<?>> inputs = new HashSet<>();
    private Point2D currentMouseCoords = Point2D.ZERO;

    /**
     * Instantiates a new InputListenerFX.
     */
    public InputListenerFX() {
    }

    /**
     * @throws NullPointerException if the given scene is null.
     */
    @Override
    public void setEventHandlers(@Nonnull final Scene scene) {
        Objects.requireNonNull(scene);
        // Setting keyboard events
        scene.setOnKeyReleased(this.getKeyReleasedEvent());
        scene.setOnKeyPressed(this.getKeyPressedEvent());

        // Setting mouse button events
        scene.setOnMouseReleased(this.getMouseReleasedEvent());
        scene.setOnMousePressed(this.getMousePressedEvent());

        // Setting mouse movement events
        scene.setOnMouseMoved(this.captureMouseCoordinates());
        scene.setOnMouseDragged(this.captureMouseCoordinates());
    }

    /**
     * @throws NullPointerException if the given scene is null.
     */
    @Override
    public void removeEventHandlersFrom(@Nonnull final Scene scene) {
        Objects.requireNonNull(scene);
        // Removing keyboard events
        scene.removeEventHandler(KeyEvent.KEY_RELEASED, this.getKeyReleasedEvent());
        scene.removeEventHandler(KeyEvent.KEY_PRESSED, this.getKeyPressedEvent());

        // Removing mouse button events
        scene.removeEventHandler(MouseEvent.MOUSE_RELEASED, this.getMouseReleasedEvent());
        scene.removeEventHandler(MouseEvent.MOUSE_PRESSED, this.getMousePressedEvent());

        // Removing mouse movement events
        scene.removeEventHandler(MouseEvent.MOUSE_MOVED, this.captureMouseCoordinates());
        scene.removeEventHandler(MouseEvent.MOUSE_DRAGGED, this.captureMouseCoordinates());
    }

    @Override
    public Pair<Set<Enum<?>>, Point2D> deliverInputs() {
        return new Pair<>(inputs, currentMouseCoords);
    }

    /**
     * Handler for key releases.
     */
    private EventHandler<KeyEvent> getKeyReleasedEvent() {
        return e -> this.forgetInput(e.getCode());
    }

    /**
     * Handler for key presses.
     */
    private EventHandler<KeyEvent> getKeyPressedEvent() {
        return e -> this.captureInput(e.getCode());
    }

    /**
     * Handler for mouse buttons releases.
     */
    private EventHandler<MouseEvent> getMouseReleasedEvent() {
        return e -> this.forgetInput(e.getButton());
    }

    /**
     * Handler for mouse buttons presses.
     */
    private EventHandler<MouseEvent> getMousePressedEvent() {
        return e -> this.captureInput(e.getButton());
    }

    /**
     * Handler common to setOnMouseMoved and setOnMouseDragged properties.
     */
    private EventHandler<MouseEvent> captureMouseCoordinates() {
        return e -> currentMouseCoords = new Point2D(e.getSceneX(), e.getSceneY());
    }

    private <T extends Enum<T>> void captureInput(final T code) {
        inputs.add(code);
    }

    private <T extends Enum<T>> void forgetInput(final T code) {
        if (inputs.contains(code)) {
            inputs.remove(code);
        }
    }
}
