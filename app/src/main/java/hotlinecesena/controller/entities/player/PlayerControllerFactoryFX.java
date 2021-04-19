package hotlinecesena.controller.entities.player;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Nonnull;

import hotlinecesena.controller.entities.EntityController;
import hotlinecesena.controller.entities.player.command.Command;
import hotlinecesena.model.entities.actors.Direction;
import hotlinecesena.model.entities.actors.DirectionList;
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
    private final Map<Enum<?>, String> bindings = Map.of(
            KeyCode.W,             "move_north",
            KeyCode.S,             "move_south",
            KeyCode.D,             "move_east",
            KeyCode.A,             "move_west",
            KeyCode.R,             "reload",
            KeyCode.E,             "use",
            MouseButton.PRIMARY,   "attack"
            );
    private final Map<String, Direction> movementActions = Map.of(
            "move_north",   DirectionList.NORTH,
            "move_south",   DirectionList.SOUTH,
            "move_east",    DirectionList.EAST,
            "move_west",    DirectionList.WEST
            );
    private final Map<String, Command> otherActions = Map.of(
            "attack",       Player::attack,
            "reload",       Player::reload,
            "use",          Player::use
            );

    /**
     * Instantiates a new factory.
     */
    public PlayerControllerFactoryFX() {
    }

    /**
     * @throws NullPointerException if the given player, sprite or listener are null.
     */
    @Override
    public EntityController create(@Nonnull final Player player, @Nonnull final Sprite sprite,
            @Nonnull final InputListener listener) {
        return new PlayerController(
                Objects.requireNonNull(player),
                Objects.requireNonNull(sprite),
                new InputInterpreterImpl(bindings, movementActions, otherActions),
                Objects.requireNonNull(listener)
                );
    }
}
