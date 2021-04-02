package hotlinecesena.controller.entities.player;

import java.util.Set;
import java.util.function.Consumer;

import com.google.common.eventbus.Subscribe;

import hotlinecesena.controller.input.InputInterpreter;
import hotlinecesena.model.entities.actors.player.Command;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.events.Subscriber;
import hotlinecesena.model.events.MovementEvent;
import hotlinecesena.model.events.RotationEvent;
import hotlinecesena.view.camera.CameraController;
import hotlinecesena.view.entities.Sprite;
import hotlinecesena.view.input.InputListener;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public final class PlayerControllerFX implements PlayerController, Subscriber {

    private final Player player;
    //TODO Listener to be held by the GameController/WorldView
    private final InputListener<KeyCode, MouseButton> listener;
    private final InputInterpreter<KeyCode, MouseButton> interpreter;
    private final Sprite sprite;
    //TODO Camera to be held by the GameController/WorldView
    private final CameraController camera;

    public PlayerControllerFX(final Player player, final Sprite sprite,
            final InputInterpreter<KeyCode, MouseButton> interpreter, final CameraController camera,
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
            Set<Command> commands = interpreter.interpret(listener.deliverInputs(),
                    sprite.getSpritePosition(), deltaTime);
            if (!commands.isEmpty()) {
                commands.forEach(c -> c.execute(player));
            }
            //TODO To be updated from the GameLoop
            camera.update(deltaTime);
        };
    }

    @Override
    public CameraController getCamera() {
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
