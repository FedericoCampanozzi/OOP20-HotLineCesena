package hotlinecesena;

import static hotlinecesena.model.entities.actors.player.PlayerAction.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import hotlinecesena.controller.input.InputInterpreter;
import hotlinecesena.controller.input.InputInterpreterImpl;
import hotlinecesena.model.entities.actors.player.Command;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.entities.actors.player.PlayerAction;
import hotlinecesena.model.entities.actors.player.PlayerImpl;
import hotlinecesena.model.inventory.NaiveInventoryImpl;
import hotlinecesena.view.input.InputListener;
import hotlinecesena.view.input.InputListenerFX;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
@TestInstance(Lifecycle.PER_METHOD)
class InterpreterPlayerTest {

    static Player player;
    static FxRobot robot;
    static Scene testScene;
    static InputListener<KeyCode, MouseButton> listener;
    static InputInterpreter<KeyCode, MouseButton> interpreter;
    static final double SPEED = 1;
    static final double MAX_HP = 100;
    static final double DELTA_TIME = 1;
    private final Map<KeyCode, PlayerAction> keyBindings = new EnumMap<>(Map.of(
            KeyCode.W,             MOVE_NORTH,
            KeyCode.S,             MOVE_SOUTH,
            KeyCode.D,             MOVE_EAST,
            KeyCode.A,             MOVE_WEST,
            KeyCode.R,             RELOAD
            ));
    private final Map<MouseButton, PlayerAction> mouseBindings = new EnumMap<>(Map.of(
            MouseButton.PRIMARY,   ATTACK
            ));

    @Start
    public void start(Stage stage) {
        player = new PlayerImpl(Point2D.ZERO, 270, SPEED, MAX_HP, new NaiveInventoryImpl(), Map.of());
        robot = new FxRobot();
        testScene = new Scene(new Pane());
        testScene.setFill(Color.BLACK);
        listener = new InputListenerFX(testScene);
        interpreter = new InputInterpreterImpl<>(keyBindings, mouseBindings);
        stage.setScene(testScene);
        stage.requestFocus();
        stage.show();
    }

    @AfterEach
    void reset() throws Exception {
        robot.release(KeyCode.W, KeyCode.S, KeyCode.D, KeyCode.A, KeyCode.R, KeyCode.E, KeyCode.J);
        robot.release(MouseButton.PRIMARY, MouseButton.SECONDARY);
        robot.moveTo(testScene);
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void deliverNothingWhenReceivingNoInputs() {
        WaitForAsyncUtils.waitForFxEvents();
        Set<Command> commands = interpreter.interpret(listener.deliverInputs(), Point2D.ZERO, DELTA_TIME);
        assertThat(commands, empty());
    }

    @Test
    void deliverNothingWhenReceivingUnboundInputs() {
        robot.press(KeyCode.J);
        WaitForAsyncUtils.waitForFxEvents();
        Set<Command> commands = interpreter.interpret(listener.deliverInputs(), Point2D.ZERO, DELTA_TIME);
        assertThat(commands, empty());
    }

    @Test
    void deliverAttack() {
        robot.moveTo(testScene).press(MouseButton.PRIMARY); //It won't register without moveTo
        WaitForAsyncUtils.waitForFxEvents();
        Set<Command> commands = interpreter.interpret(listener.deliverInputs(), Point2D.ZERO, DELTA_TIME);
        assertThat(PlayerAction.ATTACK.getCommand().get(), is(in(commands)));
    }

    @Test
    void deliverMoveNorthThenMovePlayer() {
        final Point2D dest = new Point2D(0, -1);
        robot.press(KeyCode.W);
        WaitForAsyncUtils.waitForFxEvents();
        Set<Command> commands = interpreter.interpret(listener.deliverInputs(), Point2D.ZERO, DELTA_TIME);
        assertThat(commands, not(empty()));
        commands.forEach(c -> c.execute(player));
        assertThat(player.getPosition(), equalTo(dest));
    }

    @Test
    void deliverNormalizedDiagonalMovement() {
        final Point2D dest = new Point2D(1, -1).normalize();
        robot.press(KeyCode.W, KeyCode.D);
        WaitForAsyncUtils.waitForFxEvents();
        Set<Command> commands = interpreter.interpret(listener.deliverInputs(), Point2D.ZERO, DELTA_TIME);
        assertThat(commands, not(empty()));
        commands.forEach(c -> c.execute(player));
        assertThat(player.getPosition(), equalTo(dest));
    }

    //TODO Can't make this work
//    @Test
//    void deliverRotation() {
//        final Point2D mouseCoords = new Point2D(testScene.getWidth()/2, testScene.getHeight()/2);
//        final double angle = MathUtils.mouseToDegrees(mouseCoords);
//        robot.moveBy(testScene.getWidth()/2, testScene.getHeight()/2);
//        WaitForAsyncUtils.waitForFxEvents();
//        Set<Command> commands = interpreter.interpret(listener.deliverInputs(), Point2D.ZERO, DELTA_TIME);
//        assertThat(commands, not(empty()));
//        commands.forEach(c -> c.execute(player));
//        assertThat(player.getAngle(), equalTo(angle));
//    }
}
