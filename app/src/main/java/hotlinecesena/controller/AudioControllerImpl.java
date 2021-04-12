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
 * Models an audio controller that allow an easy access 
 * to the {@link ProxyAudio} by exposing few ways
 * to directly play the needed sound with the 
 * correct volume settings.
 */
public final class AudioControllerImpl implements AudioController {

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
    public AudioControllerImpl() {
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
            this.audio.setVolume(((this.volume / PERCENT) + (PERCENT + 1 - this.volume) / POINT_O_PERCENT) - BACKGROUND_VOLUME / PERCENT);
        }
    }

    /**
     * Starts or stops the music based on option
     * menu
     */
    private void startAndStop() {
        if (this.audio == null && playMusic) {
            this.audio = this.loader.getMediaPlayer(AudioType.BACKGROUND);
            this.audio.play();
        } else if (this.audio != null && !playMusic) {
            this.audio.stop();
            this.audio = null;
        }
    }

    /**
     * Calculates the volume based on the
     * {@code Entity}.
     * @param caller the entity invoking this method
     * @return the value for the volume
     * @see Entity
     */
    private double volumeSettings(final Entity caller) {
        return caller instanceof Enemy 
                ? this.volume - BACKGROUND_VOLUME / PERCENT : this.volume;
    }

    @Override
    public void updateSettings() {
        this.volume = JSONDataAccessLayer.getInstance().getSettings().getVolume();
        this.playEffects = JSONDataAccessLayer.getInstance().getSettings().isEffectActive();
        this.playMusic = JSONDataAccessLayer.getInstance().getSettings().isMusicActive();

        this.startAndStop();
        
        if (this.playMusic) {
            this.updateMusicVolume(this.volume);
        }
    }

    @Override
    public void playAudioClip(final AudioType type, final Entity caller) {
        if (this.playEffects) {
            this.clip = this.loader.getAudioClip(type);
            if (!this.clip.isPlaying()) {
                this.clip.play(this.volumeSettings(caller));
            }
        }
    }

    @Override
    public void playMusic() {
        if (this.playMusic) {
            this.audio = this.loader.getMediaPlayer(AudioType.BACKGROUND);
            this.audio.setVolume(this.volume  / BACKGROUND_VOLUME);
            this.audio.setAutoPlay(true);
        }
    }

    @Override
    public void stopMusic() {
        if (this.audio != null) {
            this.audio.stop();
        }
    }
}
