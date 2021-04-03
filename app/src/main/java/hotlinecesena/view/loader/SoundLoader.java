package hotlinecesena.view.loader;

import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;

public interface SoundLoader {
    
    public AudioClip getAudioClip(AudioType type);
    
    public MediaPlayer getMediaPlayer(AudioType type);
    
}
