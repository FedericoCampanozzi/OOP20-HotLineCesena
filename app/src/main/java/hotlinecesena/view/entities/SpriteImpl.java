package hotlinecesena.view.entities;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public final class SpriteImpl implements Sprite {

    private static final double SCALE = 0.2; //TODO Temporary, GameController will pass this to the constructor
    private static final double SPEED_SCALE = 10; //SCALE * SPEED_SCALE = 2
    private final ImageView imageView;
    private final Rotate rotate;
    private final Translate trans;

    public SpriteImpl(final Image img, final Pane pane) {
        imageView = new ImageView(img);
        imageView.setScaleX(SCALE);
        imageView.setScaleY(SCALE);

        rotate = new Rotate();
        trans = new Translate();
        imageView.getTransforms().addAll(rotate, trans);
        pane.getChildren().add(imageView);
    }

    @Override
    public void updatePosition(final Point2D entityPos) {
        rotate.setPivotX(entityPos.getX() * SPEED_SCALE + imageView.getImage().getWidth() / 2);
        rotate.setPivotY(entityPos.getY() * SPEED_SCALE + imageView.getImage().getHeight() / 2);
        trans.setX(entityPos.getX() * SPEED_SCALE);
        trans.setY(entityPos.getY() * SPEED_SCALE);
    }

    @Override
    public void updateRotation(final double entityAngle) {
        rotate.setAngle(entityAngle);
    }

    @Override
    public void updateImage(final Image image) {
        imageView.setImage(image);
    }

    @Override
    public Point2D getSpritePosition() {
        final Point2D bounds = imageView.localToScene(0, 0);
        return new Point2D(bounds.getX(), bounds.getY());
    }
}
