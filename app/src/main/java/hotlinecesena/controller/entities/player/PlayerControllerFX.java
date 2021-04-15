package hotlinecesena.controller.entities.player;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import com.google.common.eventbus.Subscribe;

import hotlinecesena.controller.input.InputInterpreter;
import hotlinecesena.model.entities.actors.player.Command;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.entities.items.WeaponType;
import hotlinecesena.model.events.DeathEvent;
import hotlinecesena.model.events.MovementEvent;
import hotlinecesena.model.events.RotationEvent;
import hotlinecesena.model.events.Subscriber;
import hotlinecesena.model.events.WeaponPickUpEvent;
import hotlinecesena.view.entities.Sprite;
import hotlinecesena.view.input.InputListener;
import hotlinecesena.view.loader.ImageLoader;
import hotlinecesena.view.loader.ImageType;
import hotlinecesena.view.loader.ProxyImage;
import hotlinecesena.view.loader.SceneType;
import javafx.scene.image.Image;

/**
 *
 * {@link PlayerController} implementation.
 *
 */
public final class PlayerControllerFX implements PlayerController, Subscriber {

    private final Player player;
    private final InputInterpreter interpreter;
    private final Sprite sprite;
    private final InputListener listener;
    private final ImageLoader loader = new ProxyImage();

    public PlayerControllerFX(@Nonnull final Player player, @Nonnull final Sprite sprite,
            @Nonnull final InputInterpreter interpreter, @Nonnull final InputListener listener) {
        this.player = Objects.requireNonNull(player);
        this.sprite = Objects.requireNonNull(sprite);
        this.interpreter = Objects.requireNonNull(interpreter);
        this.listener = Objects.requireNonNull(listener);
        this.setup();
    }

    private void setup() {
        player.register(this);
        sprite.updatePosition(player.getPosition());
        sprite.updateRotation(player.getAngle());
        player.getInventory().getWeapon().ifPresentOrElse(
                weapon -> sprite.updateImage(this.getImageByWeapon(weapon.getWeaponType())),
                () -> sprite.updateImage(loader.getImage(SceneType.GAME, ImageType.PLAYER)));
    }

    private @Nonnull Image getImageByWeapon(final WeaponType weapon) {
        Image outImage = loader.getImage(SceneType.GAME, ImageType.PLAYER);
        switch (weapon) {
        case PISTOL:
            outImage = loader.getImage(SceneType.GAME, ImageType.PLAYER_PISTOL);
            break;
        case SHOTGUN:
            outImage = loader.getImage(SceneType.GAME, ImageType.PLAYER_SHOTGUN);
            break;
        case RIFLE:
            outImage = loader.getImage(SceneType.GAME, ImageType.PLAYER_RIFLE);
            break;
        default:
            break;
        }
        return outImage;
    }

    @Override
    public Consumer<Double> getUpdateMethod() {
        return deltaTime -> {
            player.update(deltaTime);
            final Collection<Command> commands = interpreter.interpret(
                    listener.deliverInputs(),
                    sprite.getPositionRelativeToScene(),
                    deltaTime
                    );
            if (!commands.isEmpty()) {
                commands.forEach(c -> c.execute(player));
            }
        };
    }

    /*
     * Update the sprite's position when the player moves.
     */
    @Subscribe
    private void handleMovementEvent(final MovementEvent e) {
        sprite.updatePosition(e.getPosition());
    }

    /*
     * Update the sprite's angle when the player rotates.
     */
    @Subscribe
    private void handleRotationEvent(final RotationEvent e) {
        sprite.updateRotation(e.getNewAngle());
    }

    /*
     * Update the sprite's image when the player picks up
     * a new weapon.
     */
    @Subscribe
    private void handleWeaponPickUpEvent(final WeaponPickUpEvent e) {
        sprite.updateImage(this.getImageByWeapon(e.getItemType()));
    }

    /*
     * Update the sprite's image on death.
     */
    @Subscribe
    private void handleDeathEvent(final DeathEvent e) {
        sprite.updateImage(loader.getImage(SceneType.GAME, ImageType.TOMBSTONE));
        sprite.updateRotation(0);
    }
}
