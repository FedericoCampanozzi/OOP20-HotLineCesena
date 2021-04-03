package hotlinecesena;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import hotlinecesena.view.input.InputListener;
import hotlinecesena.view.input.InputListenerFX;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

@ExtendWith(ApplicationExtension.class)
@TestInstance(Lifecycle.PER_METHOD)
class InputListenerTest {

    private FxRobot robot;
    private Scene testScene;
    private InputListener listener;

    @Start
    public void start(Stage stage) {
        robot = new FxRobot();
        testScene = new Scene(new Pane(), 800, 600);
        testScene.setFill(Color.BLACK);
        listener = new InputListenerFX(testScene);
        stage.setScene(testScene);
        stage.requestFocus();
        stage.show();
    }

    @AfterEach
    void reset() throws Exception {
        robot.release(KeyCode.W, KeyCode.D, KeyCode.E);
        robot.release(MouseButton.PRIMARY, MouseButton.SECONDARY);
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void keyboardPressAndRelease() {
        robot.press(KeyCode.W);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(listener.deliverInputs().getKey(), contains(KeyCode.W));
        robot.release(KeyCode.W);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(listener.deliverInputs().getKey(), empty());
    }

    @Test
    void mousePressAndRelease() {
        robot.moveTo(testScene).press(MouseButton.PRIMARY);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(listener.deliverInputs().getKey(), contains(MouseButton.PRIMARY));
        robot.moveTo(testScene).release(MouseButton.PRIMARY);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(listener.deliverInputs().getKey(), empty());
    }

    @Test
    void multiplePressAndRelease() {
        robot.press(KeyCode.W, KeyCode.D, KeyCode.E);
        robot.moveTo(testScene).press(MouseButton.PRIMARY, MouseButton.SECONDARY);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(listener.deliverInputs().getKey(),
                containsInAnyOrder(KeyCode.W, KeyCode.D, KeyCode.E, MouseButton.PRIMARY, MouseButton.SECONDARY));

        robot.release(KeyCode.W, KeyCode.E);
        robot.release(MouseButton.PRIMARY, MouseButton.SECONDARY);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(listener.deliverInputs().getKey(), hasSize(1));
        assertThat(listener.deliverInputs().getKey(), hasItem(KeyCode.D));
    }

    @Test
    void mouseMovement() {
        final Point2D center = new Point2D(testScene.getWidth()/2, testScene.getHeight()/2);
        robot.moveTo(Point2D.ZERO).moveTo(testScene);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(listener.deliverInputs().getValue(), equalTo(center));
    }
}
