package hotlinecesena.model.entities.actors.player;

import javafx.geometry.Point2D;

public interface PlayerFactory {

    Player createPlayer();

    Player createPlayer(Point2D position, double angle);
}
