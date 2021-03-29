package hotlinecesena.controller.input;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import hotlinecesena.view.entities.Sprite;
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

    private static final float DEADZONE = 65.0f;
    private final Set<KeyCode> keyboardInputs = new HashSet<>();
    private final Set<MouseButton> mouseInputs = new HashSet<>();
    private Point2D mouseMovement = Point2D.ZERO;
    private final Scene scene;
    private final Sprite playerView;

    public InputListenerFX(final Scene scene, final Sprite view) {
        this.scene = scene;
        this.playerView = view;
    }

    @Override
    public Triple<Set<KeyCode>, Set<MouseButton>, Point2D> deliverInputs() {
        this.listenForKeyInputs();
        this.listenForMouseButtons();
        this.listenForMouseMovement(playerView.getSpritePosition());
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

    private void listenForMouseMovement(final Point2D spritePosition) {
        //Mouse moved, no buttons pressed
        scene.setOnMouseMoved(e -> {
            this.mouseMovement = this.retrieveMouseCoordinates(e, spritePosition);
        });
        //Mouse moved and buttons pressed
        scene.setOnMouseDragged(e -> {
            this.mouseMovement = this.retrieveMouseCoordinates(e, spritePosition);
        });
    }

    private Point2D retrieveMouseCoordinates(MouseEvent e, Point2D spritePosition) {
        // Ignore mouse movements if too close to the player
        if (spritePosition.distance(e.getSceneX(), e.getSceneY()) > DEADZONE) {
            return new Point2D(e.getSceneX() - spritePosition.getX(),
                               e.getSceneY() - spritePosition.getY());
        }
        return this.mouseMovement;
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
