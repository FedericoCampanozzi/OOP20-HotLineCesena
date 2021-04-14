package hotlinecesena.controller;

import com.google.common.eventbus.Subscribe;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.events.AttackPerformedEvent;
import hotlinecesena.model.events.DeathEvent;
import hotlinecesena.model.events.ItemPickUpEvent;
import hotlinecesena.model.events.MovementEvent;
import hotlinecesena.model.events.ReloadEvent;
import hotlinecesena.model.events.Subscriber;
import hotlinecesena.model.events.WeaponPickUpEvent;
import hotlinecesena.view.loader.AudioType;

/**
 * Controller that will reproduce an {@code AudioClip}
 * based on the event.
 * @see AudioControllerImpl
 * @see AudioClip
 * @see Event
 */
public class AudioEventController implements Subscriber {

    private AudioControllerImpl audio;

    /**
     * Class constructor that will register each {@code Actor}
     * instantiated to listen to their {@code Event}.
     */
    public AudioEventController() {
        this.audio = new AudioControllerImpl();
        JSONDataAccessLayer.getInstance().getEnemy().getEnemies().forEach(e -> {
            e.register(this);
        });
        JSONDataAccessLayer.getInstance().getPlayer().getPly().register(this);
    }

    /**
     * Event triggered every time and entity moves and
     * will reproduce the related sound.
     * @param e the {@code Entity} that has triggered this event.
     */
    @Subscribe
    private void onMoveEvent(final MovementEvent<Actor> e) {
        this.audio.playAudioClip(AudioType.WALK, e.getSourceInterfaces());
    }

    /**
     * Event triggered every time and entity dies and
     * will reproduce the related sound.
     * @param e the {@code Entity} that has triggered this event.
     */
    @Subscribe
    private void onDeathEvent(final DeathEvent<Actor> e) {
        this.audio.playAudioClip(AudioType.DEATH, e.getSourceInterfaces());
    }

    /**
     * Event triggered every time and entity shoots and
     * will reproduce the related sound to the equipped
     * weapon.
     * @param e the {@code Entity} that has triggered this event.
     */
    @Subscribe
    private void onAttackEvent(final AttackPerformedEvent<Actor> e) {
        this.audio = new AudioControllerImpl();
        switch (e.getItemType()) {
            case PISTOL:
                this.audio.playAudioClip(AudioType.SHOOT_PISTOL, e.getSourceInterfaces());
                break;
            case RIFLE:
                this.audio.playAudioClip(AudioType.SHOOT, e.getSourceInterfaces());
                break;
            case SHOTGUN:
                this.audio.playAudioClip(AudioType.SHOOT_SHOTGUN, e.getSourceInterfaces());
                break;
            default:
                throw new IllegalArgumentException("No such weapon type");
        }
    }

    /**
     * Event triggered every time and entity reloads and
     * will reproduce the related sound to the equipped
     * weapon.
     * @param e the {@code Entity} that has triggered this event.
     */
    @Subscribe
    private void onReloadEvent(final ReloadEvent<Actor> e) {
        switch (e.getItemType()) {
            case PISTOL:
                this.audio.playAudioClip(AudioType.RELOAD_PISTOL, e.getSourceInterfaces());
                break;
            case RIFLE:
                this.audio.playAudioClip(AudioType.RELOAD, e.getSourceInterfaces());
                break;
            case SHOTGUN:
                this.audio.playAudioClip(AudioType.RELOAD_SHOTGUN, e.getSourceInterfaces());
                break;
            default:
                throw new IllegalArgumentException("No such weapon type");
        }
    }

    /**
     * Event triggered every time the player picks up a
     * weapon and will reproduce the related sound.
     * @param e the {@code Actor} that has triggered this event.
     */
    @Subscribe
    private void onWeaponPickUpEvent(final WeaponPickUpEvent<Actor> e) {
        this.audio.playAudioClip(AudioType.PICKUP_WEAPON, e.getSourceInterfaces());
    }

    /**
     * Event triggered every time the player picks up an
     * item and will reproduce the related sound to the
     * type of item picked up.
     * @param e the {@code Actor} that has triggered this event.
     */
    @Subscribe
    private void onItemPickUpEvent(final ItemPickUpEvent<Actor> e) {
        switch (e.getItemType()) {
            case AMMO_BAG:
                this.audio.playAudioClip(AudioType.PICKUP_ITEM, e.getSourceInterfaces());
                break;
            case MEDIKIT:
                this.audio.playAudioClip(AudioType.PICKUP_MEDKIT, e.getSourceInterfaces());
                break;
            default:
                throw new IllegalArgumentException("No such item");
        }
    }
}
