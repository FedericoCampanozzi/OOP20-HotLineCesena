package hotlinecesena.controller.entities;

import hotlinecesena.controller.Updatable;
import hotlinecesena.view.entities.Sprite;

/**
 * Models a controller that will handle updates for an {@link Entity}
 * and its {@link Sprite}.
 */
public interface EntityController extends Updatable {

    /**
     * Returns the {@link Sprite} handled by this controller.
     * @return the {@link Sprite} handled by this controller.
     */
    Sprite getSprite();
}
