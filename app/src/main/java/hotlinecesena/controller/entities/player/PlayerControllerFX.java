package hotlinecesena.controller.entities.player;

import java.util.Set;
import java.util.function.Consumer;

import hotlinecesena.controller.input.InputInterpreter;
import hotlinecesena.model.entities.actors.player.Command;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.view.entities.Camera;
import hotlinecesena.view.entities.Sprite;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public final class PlayerControllerFX implements PlayerController {

    private final Player player;
    private final InputInterpreter<KeyCode, MouseButton> input;
    private final Sprite sprite;
    private final Camera camera;

    public PlayerControllerFX(final Player player, final Sprite sprite,
            final InputInterpreter<KeyCode, MouseButton> input, final Camera camera) {
        this.player = player;
        this.sprite = sprite;
        this.input = input;
        this.camera = camera;

//        setup();
    }

//    private void setup() {
//        this.player.register(this);
//        this.sprite.updatePosition(this.player.getPosition());
//        this.sprite.updateRotation(this.player.getAngle());
//    }

    @Override
    public Consumer<Double> getUpdateMethod() {
        return deltaTime -> {
            player.update(deltaTime);
            Set<Command> commands = input.interpret(deltaTime);
            if (!commands.isEmpty()) {
                commands.forEach(c -> c.execute(this.player));
            }
            sprite.updatePosition(player.getPosition());
            sprite.updateRotation(player.getAngle());
            camera.update(player.getPosition(), deltaTime);
        };
    }

    @Override
    public Camera getCamera() {
        return this.camera;
    }

//    @Subscribe
//    private void handleMovementEvent(MovementEvent e) {
//        sprite.updatePosition(e.getPosition());
//    }
//
//    @Subscribe
//    private void handleRotationEvent(RotationEvent e) {
//        sprite.updateRotation(e.getNewAngle());
//    }

//    @Subscribe
//    private void handleDeathEvent(DeathEvent e) {
//        sprite.updateImage(new Image("dead.png"));
//    }
}
