package hotlinecesena.controller.entities.player;

import hotlinecesena.controller.Updatable;
import hotlinecesena.view.entities.Camera;

public interface PlayerController extends Updatable {

    Camera getCamera();
}
