package hotlinecesena.view.input;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Nonnull;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
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

    @Override
    public Pair<Set<Enum<?>>, Point2D> deliverInputs() {
        return new Pair<>(Set.copyOf(inputs), new Point2D(currentMouseCoords.getX(), currentMouseCoords.getY()));
    }

    /**
     * @throws NullPointerException if the given scene is null.
     */
    @Override
    public void addEventHandlers(@Nonnull final Scene scene) {
        Objects.requireNonNull(scene);

        // Set keyboard handlers
        scene.setOnKeyReleased(this.getOnKeyReleasedHandler());
        scene.setOnKeyPressed(this.getOnKeyPressedHandler());

        // Set mouse button handlers
        scene.setOnMouseReleased(this.getOnMouseReleasedHandler());
        scene.setOnMousePressed(this.getOnMousePressedHandler());

        // Set mouse movement handlers
        scene.setOnMouseMoved(this.captureMouseCoordinates());
        scene.setOnMouseDragged(this.captureMouseCoordinates());

        // Add focus change listener to the stage
        ((Stage) scene.getWindow()).focusedProperty().addListener(this.forgetAllInputs());
    }

    /**
     * @throws NullPointerException if the given scene is null.
     */
    @Override
    public void removeEventHandlersFrom(@Nonnull final Scene scene) {
        Objects.requireNonNull(scene);

        // Remove keyboard handlers
        scene.removeEventHandler(KeyEvent.KEY_RELEASED, this.getOnKeyReleasedHandler());
        scene.removeEventHandler(KeyEvent.KEY_PRESSED, this.getOnKeyPressedHandler());

        // Remove mouse button handlers
        scene.removeEventHandler(MouseEvent.MOUSE_RELEASED, this.getOnMouseReleasedHandler());
        scene.removeEventHandler(MouseEvent.MOUSE_PRESSED, this.getOnMousePressedHandler());

        // Remove mouse movement handlers
        scene.removeEventHandler(MouseEvent.MOUSE_MOVED, this.captureMouseCoordinates());
        scene.removeEventHandler(MouseEvent.MOUSE_DRAGGED, this.captureMouseCoordinates());

        // Remove focus change listener from the stage
        ((Stage) scene.getWindow()).focusedProperty().removeListener(this.forgetAllInputs());
    }

    /*
     * Handler for key releases.
     */
    private EventHandler<KeyEvent> getOnKeyReleasedHandler() {
        return e -> this.forgetInput(e.getCode());
    }

    /*
     * Handler for key presses.
     */
    private EventHandler<KeyEvent> getOnKeyPressedHandler() {
        return e -> this.captureInput(e.getCode());
    }

    /*
     * Handler for mouse buttons releases.
     */
    private EventHandler<MouseEvent> getOnMouseReleasedHandler() {
        return e -> this.forgetInput(e.getButton());
    }

    /*
     * Handler for mouse buttons presses.
     */
    private EventHandler<MouseEvent> getOnMousePressedHandler() {
        return e -> this.captureInput(e.getButton());
    }

    /*
     * Handler common to setOnMouseMoved and setOnMouseDragged properties.
     */
    private EventHandler<MouseEvent> captureMouseCoordinates() {
        return e -> currentMouseCoords = new Point2D(e.getSceneX(), e.getSceneY());
    }

    /*
     * Makes this InputListener forget all registered inputs if
     * the stage loses focus.
     */
    private ChangeListener<? super Boolean> forgetAllInputs() {
        return (obs, oldV, newV) -> inputs.clear();
    }

    private <T extends Enum<T>> void captureInput(final T code) {
        inputs.add(code);
    }

    private <T extends Enum<T>> void forgetInput(final T code) {
        inputs.remove(code);
    }
}
