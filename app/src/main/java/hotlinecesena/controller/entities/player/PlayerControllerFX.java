package hotlinecesena.controller.entities.player;

import java.util.Set;
import java.util.function.Consumer;

import com.google.common.eventbus.Subscribe;

import hotlinecesena.controller.input.InputInterpreter;
import hotlinecesena.model.entities.actors.player.Command;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.events.MovementEvent;
import hotlinecesena.model.events.RotationEvent;
import hotlinecesena.model.events.Subscriber;
import hotlinecesena.view.camera.CameraController;
import hotlinecesena.view.entities.Sprite;
import hotlinecesena.view.input.InputListener;

public final class PlayerControllerFX implements PlayerController, Subscriber {

    private final Player player;
    //TODO Listener to be held by the GameController/WorldView
    private final InputListener listener;
    private final InputInterpreter interpreter;
    private final Sprite sprite;
    //TODO Camera to be held by the GameController/WorldView
    private final CameraController camera;

    public PlayerControllerFX(final Player player, final Sprite sprite, final InputInterpreter interpreter,
            final CameraController camera, final InputListener listener) {
        this.player = player;
        this.sprite = sprite;
        this.listener = listener;
        this.interpreter = interpreter;
        this.camera = camera;

        this.setup();
    }

    private void setup() {
        player.register(this);
        sprite.updatePosition(player.getPosition());
        sprite.updateRotation(player.getAngle());
    }

    @Override
    public Consumer<Double> getUpdateMethod() {
        return deltaTime -> {
            player.update(deltaTime);
            final Set<Command> commands = interpreter.interpret(listener.deliverInputs(),
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
        return camera;
    }

    @Subscribe
    private void handleMovementEvent(final MovementEvent e) {
        sprite.updatePosition(e.getPosition());
    }

    @Subscribe
    private void handleRotationEvent(final RotationEvent e) {
        sprite.updateRotation(e.getNewAngle());
    }
}
