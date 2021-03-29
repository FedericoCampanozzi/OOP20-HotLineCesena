package hotlinecesena.controller.entities.player;

import java.util.Set;
import java.util.function.Consumer;

import hotlinecesena.controller.input.InputInterpreter;
import hotlinecesena.model.entities.actors.player.Command;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.view.entities.Camera;
import hotlinecesena.view.entities.SpriteView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public final class PlayerControllerFX implements PlayerController {

    private final Player player;
    private final InputInterpreter<KeyCode, MouseButton> input;
    private final SpriteView view;
    private final Camera camera;

    public PlayerControllerFX(final Player player, final SpriteView view,
            final InputInterpreter<KeyCode, MouseButton> input, final Camera camera) {
        this.player = player;
        this.view = view;
        this.input = input;
        this.camera = camera;
    }

    @Override
    public Consumer<Double> getUpdateMethod() {
        return deltaTime -> {
            player.update(deltaTime);
            Set<Command> commands = input.interpret(deltaTime);
            if (!commands.isEmpty()) {
                commands.forEach(c -> c.execute(this.player));
            }
            view.update(player.getPosition(), player.getAngle());
            camera.update(player.getPosition(), deltaTime);
        };
    }

    @Override
    public Camera getCamera() {
        return this.camera;
    }
}
