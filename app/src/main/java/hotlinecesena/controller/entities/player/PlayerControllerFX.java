package hotlinecesena.controller.entities.player;

import java.util.Set;
import java.util.function.Consumer;

import com.google.common.eventbus.Subscribe;

import hotlinecesena.controller.input.InputInterpreter;
import hotlinecesena.controller.input.InputListener;
import hotlinecesena.model.entities.actors.player.Command;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.events.Subscriber;
import hotlinecesena.model.events.MovementEvent;
import hotlinecesena.model.events.RotationEvent;
import hotlinecesena.view.Camera;
import hotlinecesena.view.entities.Sprite;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public final class PlayerControllerFX implements PlayerController, Subscriber {

    private final Player player;
    //TODO Listener to be held by the GameController
    private final InputListener<KeyCode, MouseButton> listener;
    private final InputInterpreter<KeyCode, MouseButton> interpreter;
    private final Sprite sprite;
    //TODO Camera to be held by the GameController
    private final Camera camera;

    public PlayerControllerFX(final Player player, final Sprite sprite,
            final InputInterpreter<KeyCode, MouseButton> interpreter, final Camera camera,
            final InputListener<KeyCode, MouseButton> listener) {
        this.player = player;
        this.sprite = sprite;
        this.listener = listener;
        this.interpreter = interpreter;
        this.camera = camera;

        setup();
    }

    private void setup() {
        this.player.register(this);
        this.sprite.updatePosition(this.player.getPosition());
        this.sprite.updateRotation(this.player.getAngle());
    }

    @Override
    public Consumer<Double> getUpdateMethod() {
        return deltaTime -> {
            player.update(deltaTime);
            var inputs = listener.deliverInputs();
            Set<Command> commands = interpreter.interpret(inputs, sprite.getSpritePosition(), deltaTime);
            if (!commands.isEmpty()) {
                commands.forEach(c -> c.execute(this.player));
            }
            camera.update(player.getPosition(), deltaTime);
        };
    }

    @Override
    public Camera getCamera() {
        return this.camera;
    }

    @Subscribe
    private void handleMovementEvent(MovementEvent e) {
        sprite.updatePosition(e.getPosition());
    }

    @Subscribe
    private void handleRotationEvent(RotationEvent e) {
        sprite.updateRotation(e.getNewAngle());
    }
}
