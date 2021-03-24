package hotlinecesena.model.entities.actors.player;

import java.util.Set;

import hotlinecesena.model.entities.actors.Actor;
import javafx.geometry.Point2D;
import javafx.util.Pair;

public interface Player extends Actor {

    void pickUp();
    
    void use();

    void dropUsable();
    
    void dropEquipped();

    double getNoiseRadius();
    
    void handleCurrentState(Pair<Set<CommandType>, Point2D> commandsToHandle, double timeElapsed);
}
