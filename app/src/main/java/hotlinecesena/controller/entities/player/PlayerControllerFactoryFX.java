package hotlinecesena.controller.entities.player;

import static hotlinecesena.model.entities.actors.player.PlayerAction.ATTACK;
import static hotlinecesena.model.entities.actors.player.PlayerAction.MOVE_EAST;
import static hotlinecesena.model.entities.actors.player.PlayerAction.MOVE_NORTH;
import static hotlinecesena.model.entities.actors.player.PlayerAction.MOVE_SOUTH;
import static hotlinecesena.model.entities.actors.player.PlayerAction.MOVE_WEST;
import static hotlinecesena.model.entities.actors.player.PlayerAction.RELOAD;

import java.util.EnumMap;
import java.util.Map;

import hotlinecesena.controller.input.InputInterpreterImpl;
import hotlinecesena.controller.input.InputListenerFX;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.entities.actors.player.PlayerAction;
import hotlinecesena.model.entities.actors.player.PlayerImpl;
import hotlinecesena.model.inventory.NaiveInventoryImpl;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public final class PlayerControllerFactoryFX implements PlayerControllerFactory {
    
    // TODO: All temporary, will replace with values given by DAL
    private static final double STARTING_ANGLE = 90;
    private static final double STARTING_SPEED = 10;
    private static final double MAX_HEALTH = 100;
    private static final Point2D STARTING_POS = Point2D.ZERO;
    private final Scene scene;
    // TODO: Read bindings from file?
    private final Map<KeyCode, PlayerAction> keyBindings = new EnumMap<>(Map.of(
            KeyCode.W,             MOVE_NORTH,
            KeyCode.S,             MOVE_SOUTH,
            KeyCode.D,             MOVE_EAST,
            KeyCode.A,             MOVE_WEST,
            KeyCode.R,             RELOAD
            ));
    private final Map<MouseButton, PlayerAction> mouseBindings = new EnumMap<>(Map.of(
            MouseButton.PRIMARY,   ATTACK
            ));

    public PlayerControllerFactoryFX(final Scene scene) {
        this.scene = scene;
    }

    @Override
    public PlayerController createPlayerController() {
        final Player playerModel = new PlayerImpl(
                STARTING_POS, STARTING_ANGLE, STARTING_SPEED, MAX_HEALTH,
                new NaiveInventoryImpl(),
                Map.of()
                );
        final InputInterpreterImpl<KeyCode, MouseButton> input = new InputInterpreterImpl<>(
                keyBindings, mouseBindings, new InputListenerFX(scene));

        return new PlayerControllerFX(playerModel, null, input);
    }
}
