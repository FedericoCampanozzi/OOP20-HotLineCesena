package hotlinecesena.view.entities;

import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nonnull;

import javafx.geometry.Point2D;
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

    private final ImageView imageView;
    private Point2D imageOffset;
    private Rotate rotate;
    private Translate trans;

    /**
     * Instantiates a new {@code SpriteImpl} with a given {@link ImageView}.
     * @param view the {@code ImageView} to be used.
     * @throws NullPointerException if the given ImageView is null.
     */
    public SpriteImpl(@Nonnull final ImageView view) {
        imageView = Objects.requireNonNull(view);
        imageOffset = new Point2D(imageView.getFitWidth() / 2, imageView.getFitHeight() / 2);
        this.findPreexistingTranslate(view).ifPresentOrElse(t -> trans = t, () -> trans = new Translate());
        this.findPreexistingRotate(view).ifPresentOrElse(r -> rotate = r, () -> rotate = new Rotate());
        imageView.getTransforms().addAll(rotate, trans);
    }

    /**
     * If the given ImageView already possesses a {@link Translate}, use that
     * instead of creating a new one.
     */
    private Optional<Translate> findPreexistingTranslate(final ImageView view) {
        return view.getTransforms()
                .stream()
                .filter(Translate.class::isInstance)
                .map(Translate.class::cast)
                .findFirst();
    }

    /**
     * If the given ImageView already possesses a {@link Rotate}, use that
     * instead of creating a new one.
     */
    private Optional<Rotate> findPreexistingRotate(final ImageView view) {
        return view.getTransforms()
                .stream()
                .filter(Rotate.class::isInstance)
                .map(Rotate.class::cast)
                .findFirst();
    }

    /**
     * @throws NullPointerException if the given position is null.
     */
    @Override
    public void updatePosition(@Nonnull final Point2D entityPos) {
        Objects.requireNonNull(entityPos);
        rotate.setPivotX(entityPos.getX() / 2 + imageOffset.getX());
        rotate.setPivotY(entityPos.getY() / 2 + imageOffset.getY());
        trans.setX(entityPos.getX() / 2);
        trans.setY(entityPos.getY() / 2);
    }

    @Override
    public void updateRotation(final double entityAngle) {
        rotate.setAngle(entityAngle);
    }

    @Override
    public void updateImage(final Image image) {
        imageView.setImage(image);
        imageOffset = new Point2D(imageView.getFitWidth() / 2, imageView.getFitHeight() / 2);
    }

    @Override
    public Point2D getPositionRelativeToParent() {
        return imageView.localToParent(imageOffset);
    }

    @Override
    public Point2D getPositionRelativeToScene() {
        return imageView.localToScene(imageOffset);
    }

    @Override
    public Point2D getTranslatePosition() {
        return new Point2D(trans.getX(), trans.getY());
    }
}
