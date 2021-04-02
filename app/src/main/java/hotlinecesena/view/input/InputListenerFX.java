package hotlinecesena.view.input;

import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * 
 * Specific implementation created to listen for inputs in JavaFX.
 *
 */
public final class InputListenerFX implements InputListener<KeyCode, MouseButton> {

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
    public Triple<Set<KeyCode>, Set<MouseButton>, Point2D> deliverInputs() {
        return new ImmutableTriple<>(this.keyboardInputs, this.mouseInputs, this.currentMouseCoords);
    }

    /**
     * Handles key presses and key releases.
     * @param scene
     */
    private void setKeyEventHandlers(final Scene scene) {
        scene.setOnKeyReleased(e -> this.unregisterInput(keyboardInputs, e.getCode()));
        scene.setOnKeyPressed(e -> this.registerInput(keyboardInputs, e.getCode()));
    }

    /**
     * Handles mouse button presses and releases.
     * @param scene
     */
    private void setMouseButtonHandlers(final Scene scene) {
        scene.setOnMouseReleased(e -> this.unregisterInput(mouseInputs, e.getButton()));
        scene.setOnMousePressed(e -> this.registerInput(mouseInputs, e.getButton()));
    }

    /**
     * Handles mouse movement.
     * @param scene
     */
    private void setMouseMovementHandlers(final Scene scene) {
        //Mouse moved, no buttons pressed
        scene.setOnMouseMoved(this.registerMouseMovement());
        //Mouse moved while buttons pressed
        scene.setOnMouseDragged(this.registerMouseMovement());
    }

    private EventHandler<MouseEvent> registerMouseMovement() {
        return e -> this.currentMouseCoords = new Point2D(e.getSceneX(), e.getSceneY());
    }

    private <T extends Enum<T>> void registerInput(Set<T> field, T code) {
        field.add(code);
    }

    private <T extends Enum<T>> void unregisterInput(Set<T> field, T code) {
        if (field.contains(code)) {
            field.remove(code);
        }
    }
}
