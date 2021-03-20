package hotlinecesena.model.entities.items;

import hotlinecesena.model.entities.actors.Actor;

public interface UsableItem extends Item {

    void use(Actor actor);
}
