package hotlinecesena.controller.entities.player;

import static hotlinecesena.model.entities.actors.player.PlayerAction.*;

import java.util.EnumMap;
import java.util.Map;

import hotlinecesena.controller.input.InputInterpreter;
import hotlinecesena.controller.input.InputInterpreterImpl;
import hotlinecesena.controller.input.InputListener;
import hotlinecesena.controller.input.InputListenerFX;
import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.entities.actors.player.PlayerAction;
import hotlinecesena.model.entities.actors.player.PlayerImpl;
import hotlinecesena.model.inventory.NaiveInventoryImpl;
import hotlinecesena.view.Camera;
import hotlinecesena.view.CameraImpl;
import hotlinecesena.view.entities.Sprite;
import hotlinecesena.view.entities.SpriteImpl;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

public final class PlayerControllerFactoryFX implements PlayerControllerFactory {

    // TODO: All temporary, will replace with values given by DAL
    private static final String SPRITE_NAME = "index.png";
    private static final double MAX_HEALTH = 100;
    private static final double SPEED = 500;
    private static final double STARTING_ANGLE = 270;
    private static final Point2D STARTING_POS = Point2D.ZERO;
    private final Scene scene;
    private final Pane pane;
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

    public PlayerControllerFactoryFX(final Scene scene, final Pane pane) {
        this.scene = scene;
        this.pane = pane;
    }

    @Override
    public PlayerController createPlayerController() {
        final Player playerModel = new PlayerImpl(
                STARTING_POS,
                STARTING_ANGLE,
                SPEED,
                MAX_HEALTH,
                new NaiveInventoryImpl(),
                Map.of(ActorStatus.NORMAL, 0.0,
                       ActorStatus.ATTACKING, 10.0,
                       ActorStatus.RELOADING, 3.0,
                       ActorStatus.DEAD, 0.0
                       )
                );
        final Sprite view = new SpriteImpl(new Image(SPRITE_NAME), pane);
        final InputListener<KeyCode, MouseButton> listener = new InputListenerFX(scene);
        final InputInterpreter<KeyCode, MouseButton> interpreter = new InputInterpreterImpl<>(
                keyBindings, mouseBindings);
        final Camera camera = new CameraImpl(scene);
        return new PlayerControllerFX(playerModel, view, interpreter, camera, listener);
    }
}
