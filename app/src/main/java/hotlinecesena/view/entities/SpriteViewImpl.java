package hotlinecesena.view.entities;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class SpriteViewImpl implements SpriteView {

    private static final float SCALE = 0.2f; //TODO Temporary, GameController will pass this to the constructor
    private final ImageView image;
    private final Rotate rotate;
    private final Translate trans;

    public SpriteViewImpl(final Image img, final Pane pane) {
        this.image = new ImageView(img);
        image.setScaleX(SCALE);
        image.setScaleY(SCALE);
        
        this.rotate = new Rotate();
        this.trans = new Translate();
        this.image.getTransforms().addAll(rotate, trans);
        pane.getChildren().add(image);
    }

    @Override
    public void update(final Point2D entityPos, final double entityAngle) {
        rotate.setAngle(entityAngle);
        rotate.setPivotX(entityPos.getX() + image.getImage().getWidth()/2);
        rotate.setPivotY(entityPos.getY() + image.getImage().getHeight()/2);
        trans.setX(entityPos.getX());
        trans.setY(entityPos.getY());
    }

    @Override
    public Point2D getSpritePosition() {
        final Point2D bounds = image.localToScene(0, 0);
        return new Point2D(bounds.getX(), bounds.getY());
    }
}
