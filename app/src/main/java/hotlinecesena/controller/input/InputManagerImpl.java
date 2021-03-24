package hotlinecesena.controller.input;

import static hotlinecesena.model.entities.actors.player.CommandType.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import hotlinecesena.model.entities.actors.player.CommandType;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.util.Pair;

public class InputManagerImpl implements InputManager {

    private final Set<CommandType> commandsToDeliver = new HashSet<>();
    private Point2D mouseMovement = Point2D.ZERO;
    // TODO: Read bindings from file
    private final Map<KeyCode, CommandType> keyBindings = Map.of(
            KeyCode.W,             MOVE_NORTH,
            KeyCode.S,             MOVE_SOUTH,
            KeyCode.D,             MOVE_EAST,
            KeyCode.A,             MOVE_WEST,
            KeyCode.R,             RELOAD,
            KeyCode.E,             PICK_UP
            );
    private final Map<MouseButton, CommandType> mouseBindings = Map.of(
            MouseButton.PRIMARY,   ATTACK,
            MouseButton.SECONDARY, USE
            );

    @Override
    public Pair<Set<CommandType>, Point2D> deliverCommandsFrom(final Scene scene) {
        this.processInput(scene);
        return new Pair<>(commandsToDeliver, mouseMovement);
    }

    private void processInput(final Scene scene) {
        scene.setOnKeyReleased(e -> {
            unregister(keyBindings.get(e.getCode()));
        });
        scene.setOnMouseReleased(e -> {
            unregister(mouseBindings.get(e.getButton()));
        });
        scene.setOnKeyPressed(e -> {
            register(keyBindings.get(e.getCode()));
        });
        scene.setOnMousePressed(e -> {
            register(mouseBindings.get(e.getButton()));
        });
        scene.setOnMouseMoved(e -> {
            mouseMovement = new Point2D(e.getSceneX(), e.getSceneY());
        });
    }

    private void register(final CommandType type) {
        this.commandsToDeliver.add(type);
    }

    private void unregister(final CommandType type) {
        if (this.commandsToDeliver.contains(type)) {
            this.commandsToDeliver.remove(type);
        }
    }
}
