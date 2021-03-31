package hotlinecesena.controller.input;

import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

/**
 * 
 * Specific implementation created to listen for inputs in JavaFX.
 *
 */
public final class InputListenerFX implements InputListener<KeyCode, MouseButton> {

    private final Set<KeyCode> keyboardInputs = EnumSet.noneOf(KeyCode.class);
    private final Set<MouseButton> mouseInputs = EnumSet.noneOf(MouseButton.class);
    private Point2D currentMouseCoords = Point2D.ZERO;

    public InputListenerFX(final Scene scene) {
        this.setKeyEventHandlers(scene);
        this.setMouseButtonHandlers(scene);
        this.setMouseMovementHandlers(scene);
    }

    @Override
    public Triple<Set<KeyCode>, Set<MouseButton>, Point2D> deliverInputs() {
        return new ImmutableTriple<>(this.keyboardInputs, this.mouseInputs, this.currentMouseCoords);
    }

//    private EventHandler<? super KeyEvent> handleOnKeyReleased() {
//        return e -> this.unregister(this.keyboardInputs, e.getCode());
//    }

    private void setKeyEventHandlers(final Scene scene) {
        scene.setOnKeyReleased(e -> this.unregister(this.keyboardInputs, e.getCode()));
        scene.setOnKeyPressed(e -> this.register(this.keyboardInputs, e.getCode()));
    }

    private void setMouseButtonHandlers(final Scene scene) {
        scene.setOnMouseReleased(e -> this.unregister(this.mouseInputs, e.getButton()));
        scene.setOnMousePressed(e -> this.register(this.mouseInputs, e.getButton()));
    }

    private void setMouseMovementHandlers(final Scene scene) {
        //Mouse moved, no buttons pressed
        scene.setOnMouseMoved(e -> this.currentMouseCoords = new Point2D(e.getSceneX(), e.getSceneY()));
        //Mouse moved and buttons pressed
        scene.setOnMouseDragged(e -> this.currentMouseCoords = new Point2D(e.getSceneX(), e.getSceneY()));
    }

    private <T extends Enum<T>> void register(Set<T> field, T code) {
        field.add(code);
    }

    private <T extends Enum<T>> void unregister(Set<T> field, T code) {
        if (field.contains(code)) {
            field.remove(code);
        }
    }
}
