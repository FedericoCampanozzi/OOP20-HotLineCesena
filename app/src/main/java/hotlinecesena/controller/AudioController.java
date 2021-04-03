package hotlinecesena.controller;

import java.io.IOException;
import java.util.Optional;

import hotlinecesena.model.DALImpl;
import hotlinecesena.model.entities.Entity;
import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.events.Subscriber;
import hotlinecesena.view.loader.AudioType;
import hotlinecesena.view.loader.ProxyAudio;
import hotlinecesena.view.loader.SoundLoader;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;

public class AudioController implements Subscriber{
    
    private final static int PERCENTAGE = 100;
    private final static int ENEMY_SOUNDS = 10;
    
    private final SoundLoader loader;
    private AudioClip clip;
    private MediaPlayer audio;
    private Optional<Entity> caller;
    private double volume;
        
    public AudioController(Optional<Entity> caller) {
        this.loader = new ProxyAudio();
        this.caller = caller;

        try {
            this.volume = DALImpl.getInstance().getIntegerSetting("volume");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.volumeSettings();
    }
    
    private void updateVolume(double value) {
        if(this.audio != null) {
            this.audio.setVolume(value);
        }
    }
    
    private void volumeSettings() {
        this.volume /= PERCENTAGE;
        this.volume = this.caller.isPresent() && this.caller.get() instanceof Enemy ?
                this.volume /  ENEMY_SOUNDS : this.volume;
    }
    
    public void setVolume(double value) {
        this.volume = value;
        this.volumeSettings();
        
        this.updateVolume(this.volume);
    }
    
    public void playAudioClip(AudioType type) {
        this.clip = this.loader.getAudioClip(type);
        this.clip.play(this.volume);
    }
    
    public void playMusic() {
        this.audio = this.loader.getMediaPlayer(AudioType.BACKGROUND);
        this.audio.setVolume(this.volume / PERCENTAGE);
        this.audio.play();
    }
}
