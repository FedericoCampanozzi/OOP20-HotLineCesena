package hotlinecesena.view.entities;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 *
 * Sprite implementation for JavaFX.
 *
 */
public final class SpriteImpl implements Sprite {

    private final ImageView imageGroup;
    private final Rotate rotate;
    private final Translate trans;

    /**
     * Instantiates a new {@code SpriteImpl} with a given {@link Group}
     * which must contain two {@link ImageView}s.
     * @param group the blend group.
     * @throws NullPointerException if the given group is null.
     * @throws IllegalArgumentException if the given group does not contain
     * exactly two {@code ImageView}s.
     */
    public SpriteImpl(final ImageView view) {
        //        if (group.getChildren().size() != 2) {
        //            throw new IllegalArgumentException();
        //        }
        //final ImageView bottomImage = (ImageView) imageGroup.getChildren().get(0);
        //bottomImage.setImage(null); //Necessary to avoid the "floating raft" effect
        imageGroup = view;
        rotate = new Rotate();
        trans = new Translate();
        imageGroup.getTransforms().addAll(rotate, trans);
    }

    /**
     * @throws NullPointerException if the given position is null.
     */
    @Override
    public void updatePosition(final Point2D entityPos) {
        rotate.setPivotX(entityPos.getX() / 100 + imageGroup.getFitWidth() / 2);
        rotate.setPivotY(entityPos.getY() / 100 + imageGroup.getFitHeight() / 2);
        trans.setX(entityPos.getX() / 100);
        trans.setY(entityPos.getY() / 100);
    }

    @Override
    public void updateRotation(final double entityAngle) {
        rotate.setAngle(entityAngle);
    }

    /**
     * @throws NullPointerException if the given image is null.
     */
    @Override
    public void updateImage(final Image image) {
    }

    @Override
    public Point2D getPositionRelativeToParent() {
        return imageGroup.localToParent(Point2D.ZERO);
    }

    @Override
    public Point2D getPositionRelativeToScene() {
        return imageGroup.localToScene(Point2D.ZERO);
    }
}