package hotlinecesena.controller.entities.player;

import java.util.function.Consumer;

import hotlinecesena.controller.Updatable;
import hotlinecesena.controller.input.InputManager;
import hotlinecesena.model.DAL;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.view.entities.PlayerView;
import javafx.scene.Scene;

public class PlayerController implements Updatable {

    private final Player player;
    private final InputManager input;
    private final PlayerView view;
    private final DAL world;
    private final Scene scene;
    
    public PlayerController(final Player player, final InputManager input, final Scene scene,
            final PlayerView view, final DAL world) {
        this.player = player;
        this.input = input;
        this.scene = scene;
        this.view = view;
        this.world = world;
    }

    @Override
    public Consumer<Double> getUpdateMethod() {
        return deltaTime -> {
            //TODO
            player.handleCurrentState(input.deliverCommandsFrom(scene), deltaTime);
            //this.view.updateView(deltaTime, this.playerModel);
        };
    }
}
