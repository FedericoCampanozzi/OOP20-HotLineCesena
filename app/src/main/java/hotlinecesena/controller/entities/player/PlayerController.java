package hotlinecesena.controller.entities.player;

import hotlinecesena.controller.Updatable;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.view.camera.CameraView;
import hotlinecesena.view.entities.Sprite;

/**
 *
 * Represents a controller that will handle updates for
 * the {@link Player} and its {@link Sprite}.
 *
 */
public interface PlayerController extends Updatable {

    // TODO To be removed when the GameController will be made to hold the camera
    CameraView getCamera();
}
