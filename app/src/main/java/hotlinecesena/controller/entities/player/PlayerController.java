package hotlinecesena.controller.entities.player;

import java.util.Objects;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import com.google.common.eventbus.Subscribe;

import hotlinecesena.controller.entities.EntityController;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.entities.items.WeaponType;
import hotlinecesena.model.events.DeathEvent;
import hotlinecesena.model.events.MovementEvent;
import hotlinecesena.model.events.RotationEvent;
import hotlinecesena.model.events.Subscriber;
import hotlinecesena.model.events.WeaponPickUpEvent;
import hotlinecesena.view.entities.Sprite;
import hotlinecesena.view.input.InputListener;
import hotlinecesena.view.loader.ImageType;

/**
 *
 * {@link EntityController} implementation for {@link Player} management.
 *
 */
public final class PlayerController implements EntityController, Subscriber {

    private final Player player;
    private final InputInterpreter interpreter;
    private final Sprite sprite;
    private final InputListener listener;

    public PlayerController(@Nonnull final Player player, @Nonnull final Sprite sprite,
            @Nonnull final InputInterpreter interpreter, @Nonnull final InputListener listener) {
        this.player = Objects.requireNonNull(player);
        this.sprite = Objects.requireNonNull(sprite);
        this.interpreter = Objects.requireNonNull(interpreter);
        this.listener = Objects.requireNonNull(listener);
        this.setup();
    }

    /**
     * Subscribe this controller to the Player and perform
     * initial Sprite updates.
     */
    private void setup() {
        player.register(this);
        sprite.updatePosition(player.getPosition());
        sprite.updateRotation(player.getAngle());
        player.getInventory().getWeapon().ifPresentOrElse(
                weapon -> sprite.updateImage(this.getImageByWeapon(weapon.getWeaponType())),
                () -> sprite.updateImage(ImageType.PLAYER));
    }

    private @Nonnull ImageType getImageByWeapon(final WeaponType weapon) {
        ImageType outImage = ImageType.PLAYER;
        switch (weapon) {
        case PISTOL:
            outImage = ImageType.PLAYER_PISTOL;
            break;
        case SHOTGUN:
            outImage = ImageType.PLAYER_SHOTGUN;
            break;
        case RIFLE:
            outImage = ImageType.PLAYER_RIFLE;
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
            interpreter.interpret(
                    listener.deliverInputs(),
                    sprite.getPositionRelativeToScene(),
                    deltaTime)
            .forEach(c -> c.execute(player));
        };
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    /*
     * Updates the sprite's position when the player moves.
     */
    @Subscribe
    private void handleMovementEvent(final MovementEvent e) {
        sprite.updatePosition(e.getPosition());
    }

    /*
     * Updates the sprite's angle when the player rotates.
     */
    @Subscribe
    private void handleRotationEvent(final RotationEvent e) {
        sprite.updateRotation(e.getNewAngle());
    }

    /*
     * Updates the sprite's image when the player picks up
     * a new weapon.
     */
    @Subscribe
    private void handleWeaponPickUpEvent(final WeaponPickUpEvent e) {
        sprite.updateImage(this.getImageByWeapon(e.getItemType()));
    }

    /*
     * Updates the sprite's image on death.
     */
    @Subscribe
    private void handleDeathEvent(final DeathEvent e) {
        sprite.updateImage(ImageType.TOMBSTONE);
        sprite.updateRotation(0.0);
    }
}
