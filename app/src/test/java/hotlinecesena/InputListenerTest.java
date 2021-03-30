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

import hotlinecesena.controller.input.InputListener;
import hotlinecesena.controller.input.InputListenerFX;
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
    void keyboardPress() {
        robot.moveTo(testScene).press(KeyCode.W);
        assertThat(listener.deliverInputs().getLeft(), contains(KeyCode.W));
    }
    
    @Test
    void keyboardRelease() {
        robot.moveTo(testScene).release(KeyCode.W);
        assertThat(listener.deliverInputs().getLeft(), empty());
    }
    
    @Test
    void mousePressAndRelease() {
        robot.moveTo(testScene).press(MouseButton.PRIMARY);
        assertThat(listener.deliverInputs().getMiddle(), contains(MouseButton.PRIMARY));
        robot.moveTo(testScene).release(MouseButton.PRIMARY);
        assertThat(listener.deliverInputs().getMiddle(), empty());
    }
    
    @Test
    void multiplePressedAndReleased() {
        robot.press(KeyCode.W, KeyCode.D, KeyCode.E);
        assertThat(listener.deliverInputs().getLeft(),
                containsInAnyOrder(KeyCode.W, KeyCode.D, KeyCode.E));
        robot.moveTo(testScene).press(MouseButton.PRIMARY, MouseButton.SECONDARY);
        assertThat(listener.deliverInputs().getMiddle(),
                containsInAnyOrder(MouseButton.PRIMARY, MouseButton.SECONDARY));
    }
}
