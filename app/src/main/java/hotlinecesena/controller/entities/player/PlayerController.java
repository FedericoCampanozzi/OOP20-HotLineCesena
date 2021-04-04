package hotlinecesena.controller.entities.player;

import hotlinecesena.controller.Updatable;
import hotlinecesena.view.camera.CameraView;

public interface PlayerController extends Updatable {

    // TODO To be removed when the GameController will be made to hold the camera
    CameraView getCamera();
}
