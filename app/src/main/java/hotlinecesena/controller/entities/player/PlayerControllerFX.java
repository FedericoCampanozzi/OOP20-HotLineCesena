package hotlinecesena.controller.entities.player;

import java.util.function.Consumer;

import hotlinecesena.controller.input.InputInterpreter;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.view.entities.PlayerView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public final class PlayerControllerFX implements PlayerController {

    private final Player player;
    private final InputInterpreter<KeyCode, MouseButton> input;
    private final PlayerView view;

    public PlayerControllerFX(final Player player, final PlayerView view,
            final InputInterpreter<KeyCode, MouseButton> input) {
        this.player = player;
        this.view = view;
        this.input = input;
    }

    @Override
    public Consumer<Double> getUpdateMethod() {
        return deltaTime -> {
            player.update(deltaTime);
            input.interpret(deltaTime).forEach(c -> c.execute(this.player));
            //view.update(this.playerModel);
        };
    }
}
