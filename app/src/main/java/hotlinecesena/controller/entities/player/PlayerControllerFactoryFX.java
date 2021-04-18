package hotlinecesena.controller.entities.player;

import static hotlinecesena.controller.entities.player.PlayerAction.ATTACK;
import static hotlinecesena.controller.entities.player.PlayerAction.MOVE_EAST;
import static hotlinecesena.controller.entities.player.PlayerAction.MOVE_NORTH;
import static hotlinecesena.controller.entities.player.PlayerAction.MOVE_SOUTH;
import static hotlinecesena.controller.entities.player.PlayerAction.MOVE_WEST;
import static hotlinecesena.controller.entities.player.PlayerAction.RELOAD;
import static hotlinecesena.controller.entities.player.PlayerAction.USE;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Nonnull;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.view.entities.Sprite;
import hotlinecesena.view.input.InputListener;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

/**
 *
 * Factory implementation to be used with JavaFX.
 *
 */
public final class PlayerControllerFactoryFX implements PlayerControllerFactory {

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
     * Instantiates a new factory.
     */
    public PlayerControllerFactoryFX() {
    }

    /**
     * @throws NullPointerException if the given sprite is null.
     */
    @Override
    public PlayerController createPlayerController(@Nonnull final Sprite sprite, @Nonnull final InputListener listener) {
        Objects.requireNonNull(sprite);
        final Player playerModel = JSONDataAccessLayer.getInstance().getPlayer().getPly();
        final InputInterpreter interpreter = new InputInterpreterImpl(bindings);
        return new PlayerControllerFX(playerModel, sprite, interpreter, listener);
    }
}
