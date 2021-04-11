package hotlinecesena.controller;

import hotlinecesena.model.entities.Entity;
import hotlinecesena.view.loader.AudioType;

/**
 * Models a controller that will control
 * audio files by playing the asked file
 * and by adding the correct volume setting.
 */
public interface AudioController {

    /**
     * Plays the {@code AudioClip} of the audio file specified
     * by its relative path.
     * @param type the path of the file that wants to
     * @param caller the entity invoking this method
     * be reproduced
     * @see Entity
     */
    void playAudioClip(AudioType type, Entity caller);

    /**
     * Plays the {@code MediaPlayer} of the audio file specified
     * by its relative path.
     */
    void playMusic();

}
