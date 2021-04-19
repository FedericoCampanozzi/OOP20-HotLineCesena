package hotlinecesena.controller.entities.player.command;

import hotlinecesena.model.entities.actors.player.Player;

/**
 * Encapsulates Player::setAngle.
 */
public final class RotateCommand implements Command {

    private final double angle;

    /**
     * Instantiates a new {@code RotateCommand}.
     * @param angle the angle the player will face.
     */
    public RotateCommand(final double angle) {
        this.angle = angle;
    }

    @Override
    public void execute(final Player player) {
        player.setAngle(angle);
    }
}
