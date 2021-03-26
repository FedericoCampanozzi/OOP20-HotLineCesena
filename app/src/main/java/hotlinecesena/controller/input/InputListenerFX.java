package hotlinecesena.controller.input;

import java.util.HashSet;
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
public class InputListenerFX implements InputListener<KeyCode, MouseButton> {

    private final Set<KeyCode> keyboardInputs = new HashSet<>();
    private final Set<MouseButton> mouseInputs = new HashSet<>();
    private Point2D mouseMovement = Point2D.ZERO;
    private final Scene scene;
    
    public InputListenerFX(final Scene scene) {
        this.scene = scene;
    }

    @Override
    public Triple<Set<KeyCode>, Set<MouseButton>, Point2D> deliverInputs() {
        this.listenForKeyInputs();
        this.listenForMouseButtons();
        this.listenForMouseMovement();
        return new ImmutableTriple<>(this.keyboardInputs, this.mouseInputs, this.mouseMovement);
    }

    private void listenForKeyInputs() {
        scene.setOnKeyReleased(e -> {
            this.unregister(this.keyboardInputs, e.getCode());
        });
        scene.setOnKeyPressed(e -> {
            this.register(this.keyboardInputs, e.getCode());
        });
    }

    private void listenForMouseButtons() {
        scene.setOnMouseReleased(e -> {
            this.unregister(this.mouseInputs, e.getButton());
        });
        scene.setOnMousePressed(e -> {
            this.register(this.mouseInputs, e.getButton());
        });
    }
    
    private void listenForMouseMovement() {
        scene.setOnMouseMoved(e -> {
            this.mouseMovement = new Point2D(e.getSceneX(), e.getSceneY());
        });
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
