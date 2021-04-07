package hotlinecesena.controller;

import java.util.Optional;

import com.google.common.eventbus.Subscribe;
import com.sun.jdi.event.Event;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.items.ItemsType;
import hotlinecesena.model.entities.items.WeaponType;
import hotlinecesena.model.events.AttackPerformedEvent;
import hotlinecesena.model.events.DeathEvent;
import hotlinecesena.model.events.MovementEvent;
import hotlinecesena.model.events.PickUpEvent;
import hotlinecesena.model.events.ReloadEvent;
import hotlinecesena.model.events.RotationEvent;
import hotlinecesena.model.events.Subscriber;
import hotlinecesena.view.loader.AudioType;
import javafx.scene.media.AudioClip;

/**
 * Models the controller that will reproduce an {@code AudioClip}
 * based on the event
 * @see AudioController
 * @see AudioClip
 * @see Event
 */
public class AudioEventController implements Subscriber{

    private AudioController audio;

    /**
     * Class constructor that will register each {@code Actor}
     * instantiated to listen to their {@code Event}
     */
    public AudioEventController() {
        JSONDataAccessLayer.getInstance().getEnemy().getEnemies().forEach(e -> {
            e.register(this);
        });
        JSONDataAccessLayer.getInstance().getPlayer().getPly().register(this);   
    }

    /**
     * Event triggered every time and entity moves and
     * will reproduce the related sound
     * @param e the {@code Entity} that has triggered this event.
     */
    @Subscribe
    private void onMoveEvent(final MovementEvent<Actor> e) {
        this.audio = new AudioController(Optional.of(e.getSource()));
        this.audio.playAudioClip(AudioType.WALK);
    }

    /**
     * Event triggered every time and entity rotate and
     * will reproduce the related sound
     * @param e the {@code Entity} that has triggered this event.
     */
    @Subscribe
    private void onRotationEvent(final RotationEvent<Actor> e) {
        this.audio = new AudioController(Optional.of(e.getSource()));  
        this.audio.playAudioClip(AudioType.WALK);
    }

    /**
     * Event triggered every time and entity dies and
     * will reproduce the related sound
     * @param e the {@code Entity} that has triggered this event.
     */
    @Subscribe
    private void onDeathEvent(final DeathEvent<Actor> e) {
        this.audio = new AudioController(Optional.of(e.getSource()));
        this.audio.playAudioClip(AudioType.DEATH);
        e.getSource().unregister(this);
    }

    /**
     * Event triggered every time and entity shoots and
     * will reproduce the related sound to the equipped
     * weapon
     * @param e the {@code Entity} that has triggered this event.
     */
    @Subscribe
    private void onAttackEvent(final AttackPerformedEvent<Actor> e) {
        this.audio = new AudioController(Optional.of(e.getSource()));

        switch(e.getItemType()) {
            case PISTOL:
                this.audio.playAudioClip(AudioType.SHOOT_PISTOL);
                break;
            case RIFLE:
                this.audio.playAudioClip(AudioType.SHOOT);
                break;
            case SHOTGUN:
                break;
            default:
                throw new IllegalArgumentException("No such weapon type");
        }
    }

    /**
     * Event triggered every time and entity reloads and
     * will reproduce the related sound to the equipped
     * weapon
     * @param e the {@code Entity} that has triggered this event.
     */
    @Subscribe
    private void onReloadEvent(final ReloadEvent<Actor> e) {
        this.audio = new AudioController(Optional.of(e.getSource()));

        switch(e.getItemType()) {
            case PISTOL:
                this.audio.playAudioClip(AudioType.RELOAD_PISTOL);
                break;
            case RIFLE:
                this.audio.playAudioClip(AudioType.RELOAD);
                break;
            case SHOTGUN:
                this.audio.playAudioClip(AudioType.RELOAD_SHOTGUN);
                break;
            default:
                throw new IllegalArgumentException("No such weapon type");
        }
    }

    /**
     * Event triggered every time the player picks up a
     * weapon and will reproduce the related sound
     * @param e the {@code Entity} that has triggered this event.
     */
    @Subscribe
    private void onWeaponPickUpEvent(final PickUpEvent<Actor, WeaponType> e) {
        this.audio = new AudioController(Optional.of(e.getSource()));
        this.audio.playAudioClip(AudioType.PICKUP_WEAPON);
    }

    /**
     * Event triggered every time the player picks up an
     * item and will reproduce the related sound to the
     * type of item picked up
     * @param e the {@code Entity} that has triggered this event.
     */
    @Subscribe
    private void onItemPickUpEvent(final PickUpEvent<Actor, ItemsType> e) {
        this.audio = new AudioController(Optional.of(e.getSource()));

        switch(e.getItemType()) {
            case AMMO_BAG:
                this.audio.playAudioClip(AudioType.PICKUP_ITEM);
                break;
            case MEDIKIT:
                this.audio.playAudioClip(AudioType.PICKUP_MEDKIT);
                break;
            default:
                throw new IllegalArgumentException("No such item");
        }
    }
}
