package hotlinecesena.model.entities.items;

import java.util.Optional;
import hotlinecesena.model.entities.actors.Actor;

public interface Usable {

    void use(Optional<Actor> actor);
}
