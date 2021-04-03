package hotlinecesena.view.loader;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class ProxyAudio implements SoundLoader{

    private final ProxyAudioLoader audioLoader;
    private final Map<String, AudioClip> loadedAudioClips;
    
    public ProxyAudio() {
        this.audioLoader = new ProxyAudioLoader();
        this.loadedAudioClips = new HashMap<>();
    }
    
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
