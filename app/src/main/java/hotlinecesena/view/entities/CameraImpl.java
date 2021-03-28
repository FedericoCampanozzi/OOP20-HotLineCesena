package hotlinecesena.view.entities;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;

/**
 * Simple camera implementation.
 *
 */
public class CameraImpl implements Camera {

    private static final float LERP = 0.1f;
    private final Translate paneTranslate = new Translate();
    private final Scene scene;

    public CameraImpl(final Scene scene) {
        this.scene = scene;
    }

    @Override
    public void attachToPane(final Pane pane) {
        pane.getTransforms().add(this.paneTranslate);
//      clipTrans = new Translate();
//      clip.getTransforms().add(clipTrans);
//      clip.widthProperty().bind(scene.widthProperty());
//      clip.heightProperty().bind(scene.heightProperty());
//      pane.setClip(clip);
    }

    @Override
    public void update(final Point2D playerPos) {
//        clipTrans.setX(playerPos.getX() - scene.getWidth() / 2);
//        clipTrans.setY(playerPos.getY() - scene.getHeight() / 2);
        this.paneTranslate.setX((playerPos.getX() - this.scene.getWidth()) * (-LERP));
        this.paneTranslate.setY((playerPos.getY() - this.scene.getHeight()) * (-LERP));
    }
}
