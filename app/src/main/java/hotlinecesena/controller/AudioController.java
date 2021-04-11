package hotlinecesena.controller;

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
 * correct volume settings.
 */
public class AudioController {

    private static final int POINT_O_PERCENT = 1000;
    private static final int PERCENT = 100;
    private static final int BACKGROUND_VOLUME = 10;

    private final SoundLoader loader;
    private AudioClip clip;
    private MediaPlayer audio;
    private double volume;
    private boolean playEffects;
    private boolean playMusic;

    /**
     * Class constructor.
     */
    public AudioController() {
        this.loader = new ProxyAudio();
        this.volume = JSONDataAccessLayer.getInstance().getSettings().getVolume();
        this.playEffects = JSONDataAccessLayer.getInstance().getSettings().isEffectActive();
        this.playMusic = JSONDataAccessLayer.getInstance().getSettings().isMusicActive();

        this.volume = (this.volume / PERCENT) + (PERCENT + 1 - this.volume) / POINT_O_PERCENT;
    }

    /**
     * Updates the volume of the audio that is
     * already playing in the background.
     * @param value the new volume setting
     */
    private void updateMusicVolume(final double value) {
        if (this.audio != null) {
            this.audio.setVolume(value);
        }
    }

    /**
     * Calculates the volume based on the
     * {@code Entity}.
     * @return the value for the volume
     * @see Entity
     */
    private double volumeSettings(final Entity caller) {
        return caller instanceof Enemy 
                ? this.volume - BACKGROUND_VOLUME / PERCENT : this.volume;
    }

    /**
     * Updates the volume and checks if music or sounds
     * have been disabled for this instance
     * of the {@code AudioController} and
     * updates the volume if a {@code MediaPlayer}
     * track is already playing.
     */
    public void updateSettings() {
        this.volume = JSONDataAccessLayer.getInstance().getSettings().getVolume();
        this.playEffects = JSONDataAccessLayer.getInstance().getSettings().isEffectActive();
        this.playMusic = JSONDataAccessLayer.getInstance().getSettings().isMusicActive();

        this.updateMusicVolume(this.volume);
    }

    /**
     * Plays the {@code AudioClip} of the audio file specified
     * by its relative path.
     * @param type the path of the file that wants to
     * @param caller the entity invoking this method
     * be reproduced
     * @see Entity
     */
    public void playAudioClip(final AudioType type, final Entity caller) {
        if (this.playEffects) {
            this.clip = this.loader.getAudioClip(type);
            if (!this.clip.isPlaying()) {
                this.clip.play(this.volumeSettings(caller));
            }
        }
    }

    /**
     * Plays the {@code MediaPlayer} of the audio file specified
     * by its relative path.
     */
    public void playMusic() {
        if (this.playMusic) {
            this.audio = this.loader.getMediaPlayer(AudioType.BACKGROUND);
            this.audio.setVolume(this.volume  / BACKGROUND_VOLUME);
            this.audio.setAutoPlay(true);
        }
    }
}
