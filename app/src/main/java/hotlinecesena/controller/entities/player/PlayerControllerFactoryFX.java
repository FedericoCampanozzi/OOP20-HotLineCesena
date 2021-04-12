package hotlinecesena.controller.entities.player;

import static hotlinecesena.model.entities.actors.player.PlayerAction.ATTACK;
import static hotlinecesena.model.entities.actors.player.PlayerAction.MOVE_EAST;
import static hotlinecesena.model.entities.actors.player.PlayerAction.MOVE_NORTH;
import static hotlinecesena.model.entities.actors.player.PlayerAction.MOVE_SOUTH;
import static hotlinecesena.model.entities.actors.player.PlayerAction.MOVE_WEST;
import static hotlinecesena.model.entities.actors.player.PlayerAction.RELOAD;
import static hotlinecesena.model.entities.actors.player.PlayerAction.USE;

import java.util.Map;
import java.util.Objects;

import hotlinecesena.controller.input.InputInterpreter;
import hotlinecesena.controller.input.InputInterpreterImpl;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.entities.actors.player.PlayerAction;
import hotlinecesena.view.camera.CameraView;
import hotlinecesena.view.camera.CameraViewImpl;
import hotlinecesena.view.entities.Sprite;
import hotlinecesena.view.input.InputListener;
import hotlinecesena.view.input.InputListenerFX;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

/**
 *
 * Factory implementation to be used with JavaFX.
 *
 */
public final class PlayerControllerFactoryFX implements PlayerControllerFactory {

    private final Scene scene;
    private final Pane pane;
    // TODO: Read bindings from file?
    private final Map<Enum<?>, PlayerAction> bindings = Map.of(
            KeyCode.W,             MOVE_NORTH,
            KeyCode.S,             MOVE_SOUTH,
            KeyCode.D,             MOVE_EAST,
            KeyCode.A,             MOVE_WEST,
            KeyCode.R,             RELOAD,
            KeyCode.E,             USE,
            MouseButton.PRIMARY,   ATTACK
            );

    /**
     * Instantiates a new factory with the given {@link Scene} and {@link Pane},
     * which will be temporarily used to set the {@link InputListener} and the
     * {@link CameraView}.
     * @param scene the scene to which the event handlers created by the {@code InputListener}
     * will be added.
     * @param pane the pane to which the {@code CameraView} will be attached.
     */
    public PlayerControllerFactoryFX(final Scene scene, final Pane pane) {
        this.scene = scene;
        this.pane = pane;
    }

    /**
     * @throws NullPointerException if the given sprite is null.
     */
    @Override
    public PlayerController createPlayerController(final Sprite sprite) {
        Objects.requireNonNull(sprite);
        final Player playerModel = JSONDataAccessLayer.getInstance().getPlayer().getPly();
        final InputListener listener = new InputListenerFX();
        listener.addEventHandlers(scene);
        final InputInterpreter interpreter = new InputInterpreterImpl(bindings);
        final CameraView camera = new CameraViewImpl();
        camera.setPane(pane);
        return new PlayerControllerFX(playerModel, sprite, interpreter, camera, listener);
    }
}
