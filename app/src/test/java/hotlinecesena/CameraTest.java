package hotlinecesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.utilities.MathUtils;
import hotlinecesena.view.camera.CameraView;
import hotlinecesena.view.camera.CameraViewImpl;
import hotlinecesena.view.entities.Sprite;
import hotlinecesena.view.entities.SpriteImpl;
import hotlinecesena.view.input.InputListenerFX;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
@TestInstance(Lifecycle.PER_METHOD)
public final class CameraTest {

    private static final double DOUBLE_EPSILON = 0.01;
    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;
    private static final double DELTA_TIME = 5.0;
    private static final double HUD_SIZE = JSONDataAccessLayer.getInstance().getSettings().getBotHudHeight()
            + JSONDataAccessLayer.getInstance().getSettings().getTopHudHeight();
    private static final double ACCEL = 30.0;
    private static final double SHARPNESS = 0.2;
    private Pane testPane;
    private Scene testScene;
    private CameraView camera;
    private Sprite sprite;

    @Start
    public void start(final Stage stage) {
        testPane = new Pane();
        final ImageView imageView = new ImageView();
        testPane.getChildren().add(imageView);
        sprite = new SpriteImpl(imageView);
        camera = new CameraViewImpl(sprite, new InputListenerFX());
        testScene = new Scene(testPane, WIDTH, HEIGHT);
        stage.setScene(testScene);
    }

    @Test
    void throwIfPaneIsNotSet() {
        assertThrows(IllegalStateException.class, () -> camera.getUpdateMethod().accept(DELTA_TIME));
    }

    @Test
    void followSpriteMovements() {
        camera.setPane(testPane);
        sprite.updatePosition(new Point2D(1, 1));
        camera.getUpdateMethod().accept(DELTA_TIME);

        // Compute predicted camera position
        final double blend = MathUtils.blend(SHARPNESS, ACCEL, DELTA_TIME);
        final Point2D targetPos = sprite.getPositionRelativeToParent()
                .subtract(testPane.getScene().getWidth() / 2,
                        (testPane.getScene().getHeight() - HUD_SIZE) / 2);
        final Point2D newPos = MathUtils.lerp(camera.getPosition(), targetPos, blend);

        assertEquals(newPos.getX(), camera.getPosition().getX(), DOUBLE_EPSILON);
        assertEquals(newPos.getY(), camera.getPosition().getY(), DOUBLE_EPSILON);
    }
}
