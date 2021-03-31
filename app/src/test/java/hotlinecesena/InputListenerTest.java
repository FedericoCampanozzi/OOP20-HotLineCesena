package hotlinecesena;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import hotlinecesena.controller.input.InputListener;
import hotlinecesena.controller.input.InputListenerFX;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

@ExtendWith(ApplicationExtension.class)
class InputListenerTest {

    static FxRobot robot;
    static Scene testScene;
    static InputListener<KeyCode, MouseButton> listener;

    @Start
    public void start(Stage stage) {
        robot = new FxRobot();
        testScene = new Scene(new Pane());
        testScene.setFill(Color.BLACK);
        listener = new InputListenerFX(testScene);
        stage.setScene(testScene);
        stage.requestFocus();
        stage.show();
    }

    @Test
    void keyboardPressAndRelease() {
        robot.press(KeyCode.W);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(listener.deliverInputs().getLeft(), contains(KeyCode.W));
        robot.release(KeyCode.W);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(listener.deliverInputs().getLeft(), empty());
    }

    @Test
    void mousePressAndRelease() {
        robot.moveTo(testScene).press(MouseButton.PRIMARY);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(listener.deliverInputs().getMiddle(), contains(MouseButton.PRIMARY));
        robot.moveTo(testScene).release(MouseButton.PRIMARY);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(listener.deliverInputs().getMiddle(), empty());
    }

    @Test
    void multiplePressAndRelease() {
        robot.press(KeyCode.W, KeyCode.D, KeyCode.E);
        robot.moveTo(testScene).press(MouseButton.PRIMARY, MouseButton.SECONDARY);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(listener.deliverInputs().getLeft(),
                containsInAnyOrder(KeyCode.W, KeyCode.D, KeyCode.E));
        assertThat(listener.deliverInputs().getMiddle(),
                containsInAnyOrder(MouseButton.PRIMARY, MouseButton.SECONDARY));

        robot.release(KeyCode.W, KeyCode.E);
        robot.release(MouseButton.PRIMARY);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(listener.deliverInputs().getLeft(),
                contains(KeyCode.D));
        assertThat(listener.deliverInputs().getMiddle(),
                contains(MouseButton.SECONDARY));
    }

    @Test
    void mouseMovement() {
        final Point2D center = new Point2D(testScene.getWidth()/2, testScene.getHeight()/2);
        robot.moveTo(testScene); // Moves to the center of the scene
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(listener.deliverInputs().getRight(), equalTo(center));
    }
}