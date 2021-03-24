package hotlinecesena.controller.input;

import java.util.Set;

import hotlinecesena.model.entities.actors.player.CommandType;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.util.Pair;

public interface InputManager {

    Pair<Set<CommandType>, Point2D> deliverCommandsFrom(Scene scene);
}
