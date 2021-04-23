package hotlinecesena.view.loader;

import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;

/**
 * SoundLoader is the actual class that perform {@code AudioClip} and
 * {@code MediaPlayer} audio file loading.
 * Once loaded the requested audio file can be reproduced when needed.
 */
public interface SoundLoader {

    /**
     * Returns an {@code AudioClip} object that can then be reproduced.
     * The audio is loaded by adding an absolute path to the two
     * arguments
     * @param type the relative path to the requested audio file
     * @return an {@code AudioClip} object
     * @see AudioClip
     * @see AudioType
     */
    AudioClip getAudioClip(AudioType type);

    /**
     * Returns an {@code MediaPlayer} object that can then be reproduced.
     * The audio is loaded by adding an absolute path to the two
     * arguments
     * @param type the relative path to the requested audio file
     * @return an {@code MediaPlayer} object
     * @see MediaPlayer
     * @see AudioType
     */
    MediaPlayer getMediaPlayer(AudioType type);

}
