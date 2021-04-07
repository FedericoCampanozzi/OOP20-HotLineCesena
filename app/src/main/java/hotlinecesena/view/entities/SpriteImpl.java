package hotlinecesena.view.entities;

import java.util.Objects;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 *
 * Sprite implementation for JavaFX.
 *
 */
public final class SpriteImpl implements Sprite {

    private static final double SCALE = 0.2; //TODO Temporary, GameController will pass this to the constructor
    private static final double SPEED_SCALE = 10; //SCALE * SPEED_SCALE = 2
    private final ImageView imageView;
    private final Rotate rotate;
    private final Translate trans;

    /**
     * Instantiates a new {@code SpriteImpl} with a given {@link Image}
     * and automatically adds it to the given {@link Pane}.
     * @param img the sprite image.
     * @param pane the Pane to which this sprite will be added.
     * @throws NullPointerException if the given image or pane are null.
     */
    public SpriteImpl(final Image img, final Pane pane) {
        Objects.requireNonNull(pane);
        imageView = new ImageView(Objects.requireNonNull(img));
        imageView.setScaleX(SCALE);
        imageView.setScaleY(SCALE);

        rotate = new Rotate();
        trans = new Translate();
        imageView.getTransforms().addAll(rotate, trans);
        pane.getChildren().add(imageView);
    }

    /**
     * @throws NullPointerException if the given position is null.
     */
    @Override
    public void updatePosition(final Point2D entityPos) {
        Objects.requireNonNull(entityPos);
        rotate.setPivotX(entityPos.getX() * SPEED_SCALE + imageView.getImage().getWidth() / 2);
        rotate.setPivotY(entityPos.getY() * SPEED_SCALE + imageView.getImage().getHeight() / 2);
        trans.setX(entityPos.getX() * SPEED_SCALE);
        trans.setY(entityPos.getY() * SPEED_SCALE);
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
        imageView.setImage(Objects.requireNonNull(image));
    }

    @Override
    public Point2D getSpritePosition() {
        return imageView.localToScene(0, 0);
    }
}
