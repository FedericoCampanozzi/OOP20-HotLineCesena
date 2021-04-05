package hotlinecesena.view.loader;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * ProxyAudio is the surrogate of {@link SoundLoader}, the proxy is able
 * to control and secure access to the real audio loader (very similarly to {@link ProxyImage}),
 * and is able to add extra functionalities without changing the way an
 * audio file is loaded
 */
public class ProxyAudio implements SoundLoader{

    private final ProxyAudioLoader audioLoader;
    private final Map<String, AudioClip> loadedAudioClips;

    /**
     * Class constructor
     */
    public ProxyAudio() {
        this.audioLoader = new ProxyAudioLoader();
        this.loadedAudioClips = new HashMap<>();
    }

    /**
     * Returns an {@code AudioClip} object that can then be reproduced.
     * Once a clip is loaded it's put in a {@code Map} to prevent the
     * same audio clip to be loaded once more if it's already present
     * this helps prevent waste of resources
     * @see Map
     */
    @Override
    public AudioClip getAudioClip(AudioType type) {
        if(this.loadedAudioClips.containsKey(type.toString())) {
            return this.loadedAudioClips.get(type.toString());
        } else {
            this.loadedAudioClips.put(type.toString(), audioLoader.getAudioClip(type));
            return this.loadedAudioClips.get(type.toString());
        }
    }

    @Override
    public MediaPlayer getMediaPlayer(AudioType type) {
        return audioLoader.getMediaPlayer(type);
    }

    private static class ProxyAudioLoader implements SoundLoader {
        private final static String PATH = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "FX";

        @Override
        public AudioClip getAudioClip(AudioType type) {
            return new AudioClip(Paths.get(PATH + File.separator + type.toString()).toUri().toString());
        }

        @Override
        public MediaPlayer getMediaPlayer(AudioType type) {
            final Media media = new Media(Paths.get(PATH + File.separator + type.toString()).toUri().toString());
            return new MediaPlayer(media);
        }  
    }
}
