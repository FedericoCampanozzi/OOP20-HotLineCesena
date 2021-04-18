package hotlinecesena.view.loader;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * ProxyAudio is the surrogate of {@link SoundLoader}, the proxy is able
 * to control and secure access to the real audio loader (very similarly to {@link ProxyImage}),
 * and is able to add extra functionalities without changing the way an
 * audio file is loaded.
 */
public final class ProxyAudio implements SoundLoader {

    private static final String SEP = "/";

    private final ProxyAudioLoader audioLoader;
    private final Map<String, AudioClip> loadedAudioClips;

    /**
     * Class constructor.
     */
    public ProxyAudio() {
        audioLoader = new ProxyAudioLoader();
        loadedAudioClips = new HashMap<>();
    }

    /**
     * Returns an {@code AudioClip} object that can then be reproduced.
     * Once a clip is loaded it's put in a {@code Map} to prevent the
     * same audio clip to be loaded once more if it's already present
     * this helps prevent waste of resources
     * @see Map
     */
    @Override
    public AudioClip getAudioClip(final AudioType type) {
        if (loadedAudioClips.containsKey(type.toString())) {
            return loadedAudioClips.get(type.toString());
        } else {
            loadedAudioClips.put(type.toString(), audioLoader.getAudioClip(type));
            return loadedAudioClips.get(type.toString());
        }
    }

    @Override
    public MediaPlayer getMediaPlayer(final AudioType type) {
        return audioLoader.getMediaPlayer(type);
    }

    private static class ProxyAudioLoader implements SoundLoader {
        private static final String PATH = "FX";
        private final ClassLoader classLoader = this.getClass().getClassLoader();

        @Override
        public AudioClip getAudioClip(final AudioType type) {
            AudioClip audio = null;
            try {
                audio = new AudioClip(classLoader.getResource(PATH + SEP + type).toURI().toString());
            } catch (final URISyntaxException e) {
                e.printStackTrace();
            }
            return audio;
        }

        @Override
        public MediaPlayer getMediaPlayer(final AudioType type) {
            Media media = null;
            try {
                media = new Media(classLoader.getResource(PATH + SEP + type).toURI().toString());
            } catch (final URISyntaxException e) {
                e.printStackTrace();
            }
            return new MediaPlayer(media);
        }
    }
}
