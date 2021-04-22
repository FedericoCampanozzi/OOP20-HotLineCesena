package hotlinecesena.controller;

import java.util.Collection;

import hotlinecesena.view.loader.AudioType;

/**
 * Models an object that controls the
 * audio settings for {@code AudioClip}s
 * and {@code MediaPlayer}, making it
 * easier for other to reproduce this
 * types of files.
 */
public interface AudioController {

    /**
     * Updates the volume and checks if music or sounds
     * have been disabled for this instance
     * of the {@code AudioController} and
     * updates the volume if a {@code MediaPlayer}
     * track is already playing.
     */
    void updateSettings();

    /**
     * Plays the {@code AudioClip} of the audio file specified
     * by its relative path.
     * @param type the path of the file that wants to
     * @param caller the {@code Actor} invoking this method
     * @see Actor
     */
    void playAudioClip(AudioType type, Collection<Class<?>> caller);

    /**
     * Plays the music playing in the background.
     */
    void playMusic();

    /**
     * Pause the music playing in the background.
     */
    void stopMusic();

}
