package hotlinecesena;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.testfx.framework.junit5.Start;

import hotlinecesena.controller.entities.player.InputInterpreter;
import hotlinecesena.controller.entities.player.InputInterpreterImpl;
import hotlinecesena.controller.entities.player.command.Command;
import hotlinecesena.controller.entities.player.command.MoveCommand;
import hotlinecesena.controller.entities.player.command.RotateCommand;
import hotlinecesena.model.entities.actors.Direction;
import hotlinecesena.model.entities.actors.DirectionList;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.entities.actors.player.PlayerImpl;
import hotlinecesena.model.inventory.NaiveInventoryImpl;
import hotlinecesena.utilities.MathUtils;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.util.Pair;

@TestInstance(Lifecycle.PER_METHOD)
class InterpreterPlayerTest {

    private static final Point2D SPRITE_POS = Point2D.ZERO;
    private static final int ANGLE = 270;
    private static final int WIDTH = 1;
    private static final int HEIGHT = 1;
    private static final double SPEED = 1.0;
    private static final double MAX_HP = 100.0;

    private static final double SCENE_WIDTH = 800.0;
    private static final double SCENE_HEIGHT = 600.0;

    private static final double DELTA_TIME = 1.0;
    private Player player;
    private InputInterpreter interpreter;
    private final Map<Enum<?>, String> bindings = Map.of(
            KeyCode.W,             "move_north",
            KeyCode.S,             "move_south",
            KeyCode.D,             "move_east",
            KeyCode.A,             "move_west",
            KeyCode.R,             "reload",
            KeyCode.E,             "use",
            MouseButton.PRIMARY,   "attack"
            );
    private final Map<String, Direction> movementActions = Map.of(
            "move_north",   DirectionList.NORTH,
            "move_south",   DirectionList.SOUTH,
            "move_east",    DirectionList.EAST,
            "move_west",    DirectionList.WEST
            );
    private final Map<String, Command> otherActions = Map.of(
            "attack",       Player::attack,
            "reload",       Player::reload,
            "use",          Player::use
            );

    @Start
    private void setup() {
        player = new PlayerImpl(Point2D.ZERO, ANGLE, WIDTH, HEIGHT, SPEED, MAX_HP, new NaiveInventoryImpl(), Map.of(),
                List.of(), List.of(), Map.of(), Map.of());
        interpreter = new InputInterpreterImpl(bindings, movementActions, otherActions, Map.of());
    }

    @BeforeEach
    void reset() {
        this.setup();
    }

    @Test
    void deliverNothingWhenReceivingNoInputs() {
        final Pair<Set<Enum<?>>, Point2D> inputs = new Pair<>(Set.of(), Point2D.ZERO);
        final Collection<Command> commands = interpreter.interpret(inputs, SPRITE_POS, DELTA_TIME);
        assertThat(commands, is(empty()));
    }

    @Test
    void deliverNothingWhenReceivingUnboundInputs() {
        final Pair<Set<Enum<?>>, Point2D> inputs = new Pair<>(Set.of(KeyCode.J), Point2D.ZERO);
        final Collection<Command> commands = interpreter.interpret(inputs, SPRITE_POS, DELTA_TIME);
        assertThat(commands, is(empty()));
    }

    @Test
    void deliverMouseCommand() {
        final Pair<Set<Enum<?>>, Point2D> inputs = new Pair<>(Set.of(MouseButton.PRIMARY), Point2D.ZERO);
        final Collection<Command> commands = interpreter.interpret(inputs, SPRITE_POS, DELTA_TIME);
        assertThat(otherActions.get(bindings.get(MouseButton.PRIMARY)), is(in(commands)));
    }

    @Test
    void deliverKeyCommand() {
        final Pair<Set<Enum<?>>, Point2D> inputs = new Pair<>(Set.of(KeyCode.E), Point2D.ZERO);
        final Collection<Command> commands = interpreter.interpret(inputs, SPRITE_POS, DELTA_TIME);
        assertThat(otherActions.get(bindings.get(KeyCode.E)), is(in(commands)));
    }

    @Test
    void deliverMovement() {
        final Pair<Set<Enum<?>>, Point2D> inputs = new Pair<>(Set.of(KeyCode.W), Point2D.ZERO);
        final Collection<Command> commands = interpreter.interpret(inputs, SPRITE_POS, DELTA_TIME);
        assertThat(commands.stream().anyMatch(MoveCommand.class::isInstance), is(true));
    }

    @Test
    void deliverRotation() {
        final Point2D mouseCoords = new Point2D(SCENE_WIDTH / 2, SCENE_HEIGHT);
        final double angle = MathUtils.mouseToDegrees(mouseCoords);
        final Pair<Set<Enum<?>>, Point2D> inputs = new Pair<>(Set.of(), mouseCoords);
        final Collection<Command> commands = interpreter.interpret(inputs, SPRITE_POS, DELTA_TIME);
        assertThat(commands.stream().anyMatch(RotateCommand.class::isInstance), is(true));
        commands.forEach(c -> c.execute(player));
        assertThat(player.getAngle(), is(equalTo(angle)));
    }
}
