package hotlinecesena.controller;

import java.util.Optional;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.Entity;
import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.view.loader.AudioType;
import hotlinecesena.view.loader.ProxyAudio;
import hotlinecesena.view.loader.SoundLoader;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;

/**
 * Controller audio that allow an easy access 
 * to the {@link ProxyAudio} by exposing few ways
 * to directly play the needed sound with the 
 * correct volume settings
 */
public class AudioController {

    private final static int POINT_O_PERCENT = 1000;
    private final static int PERCENT = 100;
    private final static int BACKGROUND_VOLUME = 10;

    private final SoundLoader loader;
    private final Optional<Entity> caller;
    private AudioClip clip;
    private MediaPlayer audio;
    private double volume;

    /**
     * Class constructor
     * @param caller the entity that create a new instance
     * @see Entity
     */
    public AudioController(final Optional<Entity> caller) {
        this.loader = new ProxyAudio();
        this.caller = caller;
        this.volume = JSONDataAccessLayer.getInstance().getSettings().getVolume();

        this.volumeSettings();
    }

    /**
     * Updates the volume of the audio that is
     * already playing in the background
     * @param value the new volume setting
     */
    private void updateVolume(final double value) {
        if(this.audio != null) {
            this.audio.setVolume(value);
        }
    }

    /**
     * Calculates the volume based on the
     * {@code Entity} that created this instance
     * @see Entity
     */
    private void volumeSettings() {
        this.volume = (this.volume / PERCENT) + (PERCENT + 1 - this.volume) / POINT_O_PERCENT;
        this.volume = this.caller.isPresent() && this.caller.get() instanceof Enemy ?
                this.volume - BACKGROUND_VOLUME / PERCENT : this.volume;
    }

    /**
     * Sets the new volume for this instance
     * of the {@code AudioController} and
     * updates the volume if a {@code MediaPlayer}
     * track is already playing
     * @param value the new volume setting
     */
    public void setVolume(final double value) {
        this.volume = value;
        this.volumeSettings();

        this.updateVolume(this.volume);
    }

    /**
     * Plays the {@code AudioClip} of the audio file specified
     * by its relative path
     * @param type the path of the file that wants to
     * be reproduced
     */
    public void playAudioClip(final AudioType type) {
        this.clip = this.loader.getAudioClip(type);
        this.clip.play(this.volume);
    }

    /**
     * Plays the {@code MediaPlayer} of the audio file specified
     * by its relative path
     */
    public void playMusic() {
        this.audio = this.loader.getMediaPlayer(AudioType.BACKGROUND);
        System.out.println(this.volume);
        this.audio.setVolume(this.volume);
        this.audio.setAutoPlay(true);
    }
}
